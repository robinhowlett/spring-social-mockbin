/**
 * 
 */
package org.springframework.social.mockbin.impl.resources;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.FOUND;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.social.impl.resources.AbstractBaseApiResourceTemplate;
import org.springframework.social.mockbin.operations.resources.RedirectOperations;
import org.springframework.social.settings.ClientSettings;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.BaseApiUriComponentsBuilder;

/**
 * @author robin
 * @see RedirectOperations
 */
public class RedirectTemplate extends AbstractBaseApiResourceTemplate<BaseApiUriComponentsBuilder<?>> implements RedirectOperations {
	
	public static final String RESOURCES_PATH = "/redirect";
	public static final String RESOURCE_ID_PATH = "/{id}";

	/**
	 * @param settings
	 * @param restTemplate
	 */
	public RedirectTemplate(ClientSettings settings, RestTemplate restTemplate) {
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
	public ResponseEntity<String> redirect() {
		return redirect(FOUND);
	}

	@Override
	public ResponseEntity<String> redirect(HttpStatus status) {
		return redirect(status, null);
	}

	@Override
	public ResponseEntity<String> redirect(HttpStatus status, String url) {
		BaseApiUriComponentsBuilder<?> builder = qb();
		
		if (status == null) {
			status = FOUND;
		}
		
		if (url != null && !url.isEmpty()) {
			builder.queryParam("to", url);
		}
				
		URI uri = builder.path("/{status}").buildAndExpand(status).toUri();
		
		@SuppressWarnings("unchecked")
		ResponseEntity<String> responseEntity = 
				(ResponseEntity<String>) createRequest(GET, uri, String.class);
		
		return responseEntity;
	}

	@Override
	public ResponseEntity<String> redirectLoop(HttpStatus status, Integer count) {
		return redirectLoop(status, count, null);
	}

	@Override
	public ResponseEntity<String> redirectLoop(HttpStatus status, Integer count, String url) {
		BaseApiUriComponentsBuilder<?> builder = qb();
		
		if (status == null) {
			status = FOUND;
		}
		
		if (count == null) {
			count = 0;
		}
		
		if (url != null && !url.isEmpty()) {
			builder.queryParam("to", url);
		}
				
		URI uri = builder.path("/{status}/{count}").buildAndExpand(status, count).toUri();
		
		@SuppressWarnings("unchecked")
		ResponseEntity<String> responseEntity = 
				(ResponseEntity<String>) createRequest(GET, uri, String.class);
		
		return responseEntity;
	}

}
