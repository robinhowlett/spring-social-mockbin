/**
 * 
 */
package org.springframework.social.mockbin.impl.resources;

import static org.springframework.http.HttpHeaders.USER_AGENT;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.social.mockbin.Mockbin.MOCKBIN_PROVIDER_ID;
import static org.springframework.util.Assert.notNull;

import java.net.URI;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.social.ApiException;
import org.springframework.social.impl.resources.AbstractBaseApiResourceTemplate;
import org.springframework.social.mockbin.dto.RequestHeaders;
import org.springframework.social.mockbin.operations.resources.HeaderOperations;
import org.springframework.social.settings.ClientSettings;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.BaseApiUriComponentsBuilder;

/**
 * @author robin
 * @see HeaderOperations
 */
public class HeaderTemplate extends AbstractBaseApiResourceTemplate<BaseApiUriComponentsBuilder<?>> implements HeaderOperations {
	
	public static final String RESOURCES_PATH = "/";
	public static final String RESOURCE_ID_PATH = "/{id}";

	/**
	 * @param settings
	 * @param restTemplate
	 */
	public HeaderTemplate(ClientSettings settings, RestTemplate restTemplate) {
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
		return RequestHeaders.class;
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
	public RequestHeaders get(HttpHeaders headers) {
		URI uri = qb().path("/headers").build().toUri();
		
		@SuppressWarnings("unchecked")
		ResponseEntity<RequestHeaders> responseEntity = 
				(ResponseEntity<RequestHeaders>) createRequest(GET, uri, EMPTY_BODY, headers, RequestHeaders.class);
		
		if (responseEntity != null) {
			return responseEntity.getBody();
		}
		
		// TODO throw Exception
		
		return null;
	}

	@Override
	public String headerValue(HttpHeaders headers, String headerName) {
		notNull(headerName, "headerName must not be null");
		
		URI uri = qb().path("/header/{name}").buildAndExpand(headerName).toUri();
		
		@SuppressWarnings("unchecked")
		ResponseEntity<String> responseEntity = 
			(ResponseEntity<String>) createRequest(GET, uri, EMPTY_BODY, headers, String.class);
		
		if (responseEntity != null) {
			return responseEntity.getBody();
		}
		
		// TODO throw Exception
		
		return null;
	}

	@Override
	public String userAgent() {
		URI uri = qb().path("/agent").build().toUri();
		
		@SuppressWarnings("unchecked")
		ResponseEntity<String> responseEntity = 
			(ResponseEntity<String>) createRequest(GET, uri, String.class);
		
		if (responseEntity != null) {
			return responseEntity.getBody();
		}
		
		throw new ApiException(MOCKBIN_PROVIDER_ID, "Could not return " + USER_AGENT + " header value");
	}

}
