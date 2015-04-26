/**
 * 
 */
package org.springframework.social.mockbin.impl.resources;

import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.social.mockbin.Mockbin.MOCKBIN_PROVIDER_ID;

import java.net.URI;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.social.ApiException;
import org.springframework.social.impl.resources.AbstractBaseApiResourceTemplate;
import org.springframework.social.mockbin.operations.resources.BinOperations;
import org.springframework.social.settings.ClientSettings;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.BaseApiUriComponentsBuilder;

import com.sportslabs.amp.har.dto.alf.har.AlfHar;
import com.sportslabs.amp.har.dto.alf.har.entries.AlfHarResponse;

/**
 * @author robin
 * @see BinOperations
 */
public class BinTemplate extends AbstractBaseApiResourceTemplate<BaseApiUriComponentsBuilder<?>> implements BinOperations {
	
	public static final String RESOURCES_PATH = "/bin";
	public static final String RESOURCE_ID_PATH = "/{binId}";

	/**
	 * @param settings
	 * @param restTemplate
	 */
	public BinTemplate(ClientSettings settings, RestTemplate restTemplate) {
		super(settings, restTemplate);
	}

	/* (non-Javadoc)
	 * @see org.springframework.social.impl.resources.AbstractBaseApiResourceTemplate#getResourcePath()
	 */
	@Override
	protected String getResourcePath() {
		return RESOURCES_PATH;
	}

	/* (non-Javadoc)
	 * @see org.springframework.social.impl.resources.AbstractBaseApiResourceTemplate#getResourceIdPath()
	 */
	@Override
	protected String getResourceIdPath() {
		return RESOURCE_ID_PATH;
	}

	/* (non-Javadoc)
	 * @see org.springframework.social.impl.resources.AbstractBaseApiResourceTemplate#getResourceClass()
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected Class getResourceClass() {
		return AlfHarResponse.class;
	}

	/* (non-Javadoc)
	 * @see org.springframework.social.impl.resources.AbstractBaseApiResourceTemplate#getPageClass()
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected Class getPageClass() {
		return null;
	}

	@Override
	public String create(AlfHarResponse response) {
		URI uri = qb().path("/create").build().toUri();
		
		@SuppressWarnings("unchecked")
		ResponseEntity<String> responseEntity = 
				(ResponseEntity<String>) createRequest(POST, uri, response, NO_ADDITIONAL_HEADERS, String.class);
		
		if (responseEntity != null && 
				responseEntity.getStatusCode() == CREATED && 
				responseEntity.getHeaders().containsKey(LOCATION) &&
				responseEntity.getBody() != null && !responseEntity.getBody().isEmpty()) {
			return responseEntity.getBody();
		}
		
		throw new ApiException(MOCKBIN_PROVIDER_ID, 
				"Failed to create new Bin. " + 
				"Response code: " + responseEntity.getStatusCode() + ", " + 
				"response body: " + responseEntity.getBody());
	}

	@Override
	public AlfHarResponse inspect(String binId) {
		URI uri = qb().path("/{binId}/view").buildAndExpand(binId).toUri();
		
		@SuppressWarnings("unchecked")
		ResponseEntity<AlfHarResponse> responseEntity = 
				(ResponseEntity<AlfHarResponse>) createRequest(GET, uri, AlfHarResponse.class);
		
		if (responseEntity != null && 
				responseEntity.getStatusCode() == OK && 
				responseEntity.getBody() != null) {
			return responseEntity.getBody();
		}
		
		// TODO throw BinInspectionException with body message
		
		return null;
	}
	
	@Override
	public ResponseEntity<Object> request(String binId) {
		return request(binId, null, null, null, null, null);
	}

	@Override
	public ResponseEntity<Object> request(String binId, HttpMethod method, String path, String query, HttpHeaders headers, Object body) {
		BaseApiUriComponentsBuilder<?> builder = qb().path("/{binId}");
		
		if (method == null) {
			method = GET;
		}
		
		if (path != null && !path.isEmpty()) {
			builder = builder.path(path);
		}
		
		if (query != null && !query.isEmpty()) {
			builder = builder.query(query);
		}
		
		URI uri = builder.buildAndExpand(binId).toUri();
		
		@SuppressWarnings("unchecked")
		ResponseEntity<Object> responseEntity = 
				(ResponseEntity<Object>) createRequest(method, uri, body, headers, String.class);
		
		return responseEntity;
	}

	@Override
	public AlfHar accessLog(String binId) {
		URI uri = qb().path("/{binId}/log").buildAndExpand(binId).toUri();
		
		@SuppressWarnings("unchecked")
		ResponseEntity<AlfHar> responseEntity = 
				(ResponseEntity<AlfHar>) createRequest(GET, uri, AlfHar.class);
		
		if (responseEntity != null && 
				responseEntity.getStatusCode() == OK && 
				responseEntity.getBody() != null) {
			return responseEntity.getBody();
		}
		
		throw new ApiException(MOCKBIN_PROVIDER_ID, "Unable to return access log for Bin " + binId);
	}

}
