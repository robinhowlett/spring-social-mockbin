/**
 * 
 */
package org.springframework.social.mockbin.impl.resources;

import static org.springframework.http.HttpMethod.GET;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.social.impl.resources.AbstractBaseApiResourceTemplate;
import org.springframework.social.mockbin.operations.resources.StreamOperations;
import org.springframework.social.settings.ClientSettings;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.BaseApiUriComponentsBuilder;

/**
 * @author robin
 * @see StreamOperations
 */
public class StreamTemplate extends AbstractBaseApiResourceTemplate<BaseApiUriComponentsBuilder<?>> implements StreamOperations {
	
	public static final String RESOURCES_PATH = "/stream";
	public static final String RESOURCE_ID_PATH = "/{id}";

	/**
	 * @param settings
	 * @param restTemplate
	 */
	public StreamTemplate(ClientSettings settings, RestTemplate restTemplate) {
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
	public ResponseEntity<String> output(Integer numberOfChunks) {
		if (numberOfChunks == null || numberOfChunks < 0) {
			numberOfChunks = 10;
		} else if (numberOfChunks > 100) {
			numberOfChunks = 100;
		}
		
		URI uri = qb().path("/{chunks}").buildAndExpand(numberOfChunks).toUri();
		
		@SuppressWarnings("unchecked")
		ResponseEntity<String> responseEntity = 
				(ResponseEntity<String>) createRequest(GET, uri, String.class);
		
		return responseEntity;
	}
	
}