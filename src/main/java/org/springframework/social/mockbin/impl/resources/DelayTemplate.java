/**
 * 
 */
package org.springframework.social.mockbin.impl.resources;

import static org.springframework.http.HttpMethod.GET;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.social.impl.resources.AbstractBaseApiResourceTemplate;
import org.springframework.social.mockbin.dto.DelayResponse;
import org.springframework.social.mockbin.operations.resources.DelayOperations;
import org.springframework.social.settings.ClientSettings;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.BaseApiUriComponentsBuilder;

/**
 * @author robin
 * @see DelayOperations
 */
public class DelayTemplate extends AbstractBaseApiResourceTemplate<BaseApiUriComponentsBuilder<?>> implements DelayOperations {
	
	public static final String RESOURCES_PATH = "/delay";
	public static final String RESOURCE_ID_PATH = "/{id}";

	/**
	 * @param settings
	 * @param restTemplate
	 */
	public DelayTemplate(ClientSettings settings, RestTemplate restTemplate) {
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
		return DelayResponse.class;
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
	public DelayResponse delay() {
		return delay(200);
	}

	@Override
	public DelayResponse delay(int milliseconds) {
		URI uri = qb().path("/{ms}").buildAndExpand(milliseconds).toUri();
		
		@SuppressWarnings("unchecked")
		ResponseEntity<DelayResponse> responseEntity = 
				(ResponseEntity<DelayResponse>) createRequest(GET, uri, DelayResponse.class);
		
		if (responseEntity != null) {
			return responseEntity.getBody();
		}
		
		// TODO throw exception
		
		return null;
	}

}
