/**
 * 
 */
package org.springframework.social.mockbin.impl.resources;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.OK;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.social.impl.resources.AbstractBaseApiResourceTemplate;
import org.springframework.social.mockbin.dto.StatusResponse;
import org.springframework.social.mockbin.operations.resources.StatusOperations;
import org.springframework.social.settings.ClientSettings;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.BaseApiUriComponentsBuilder;

/**
 * @author robin
 * @see StatusOperations
 */
public class StatusTemplate extends AbstractBaseApiResourceTemplate<BaseApiUriComponentsBuilder<?>> implements StatusOperations {
	
	public static final String RESOURCES_PATH = "/status";
	public static final String RESOURCE_ID_PATH = "/{id}";

	/**
	 * @param settings
	 * @param restTemplate
	 */
	public StatusTemplate(ClientSettings settings, RestTemplate restTemplate) {
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
		return ResponseEntity.class;
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
	public ResponseEntity<StatusResponse> customStatus(HttpStatus httpStatus) {
		String reason;
		
		if (httpStatus == null) {
			httpStatus = OK;
			reason = OK.getReasonPhrase();
		} else {
			reason = httpStatus.getReasonPhrase();
		}
		
		return customStatus(httpStatus, reason);
	}

	@Override
	public ResponseEntity<StatusResponse> customStatus(HttpStatus httpStatus, String reason) {
		if (httpStatus == null) {
			httpStatus = OK;
		}
		
		if (reason == null || reason.isEmpty()) {
			reason = OK.getReasonPhrase();
		}
		
		URI uri = qb().path("/{code}/{reason}").buildAndExpand(httpStatus.value(), reason).toUri();
		
		@SuppressWarnings("unchecked")
		ResponseEntity<StatusResponse> responseEntity = 
				(ResponseEntity<StatusResponse>) createRequest(GET, uri, StatusResponse.class);
		
		return responseEntity;
	}

}
