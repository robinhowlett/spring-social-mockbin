/**
 * 
 */
package org.springframework.social.mockbin.impl.resources;

import static org.springframework.http.HttpHeaders.ACCEPT_ENCODING;
import static org.springframework.http.HttpMethod.GET;

import java.net.URI;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.social.impl.resources.AbstractBaseApiResourceTemplate;
import org.springframework.social.mockbin.operations.resources.CompressionOperations;
import org.springframework.social.settings.ClientSettings;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.BaseApiUriComponentsBuilder;

/**
 * @author robin
 * @see CompressionOperations
 */
public class CompressionTemplate extends AbstractBaseApiResourceTemplate<BaseApiUriComponentsBuilder<?>> implements CompressionOperations {
	
	public static final String RESOURCES_PATH = "/gzip";
	public static final String RESOURCE_ID_PATH = null;

	/**
	 * @param settings
	 * @param restTemplate
	 */
	public CompressionTemplate(ClientSettings settings, RestTemplate restTemplate) {
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
	public ResponseEntity<String> gzip() {
		URI uri = qb().build().toUri();
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(ACCEPT_ENCODING, "gzip");
		
		@SuppressWarnings("unchecked")
		ResponseEntity<String> responseEntity = 
				(ResponseEntity<String>) createRequest(GET, uri, EMPTY_BODY, headers, String.class);

		return responseEntity;
	}

}
