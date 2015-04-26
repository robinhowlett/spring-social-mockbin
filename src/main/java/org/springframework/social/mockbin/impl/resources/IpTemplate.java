/**
 * 
 */
package org.springframework.social.mockbin.impl.resources;

import static java.util.Arrays.asList;
import static org.springframework.util.StringUtils.delimitedListToStringArray;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.social.impl.resources.AbstractBaseApiResourceTemplate;
import org.springframework.social.mockbin.operations.resources.IpOperations;
import org.springframework.social.settings.ClientSettings;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.BaseApiUriComponentsBuilder;

/**
 * @author robin
 * @see IpOperations
 */
public class IpTemplate extends AbstractBaseApiResourceTemplate<BaseApiUriComponentsBuilder<?>> implements IpOperations {
	
	public static final String RESOURCES_PATH = "/";
	public static final String RESOURCE_ID_PATH = "/{id}";

	/**
	 * @param settings
	 * @param restTemplate
	 */
	public IpTemplate(ClientSettings settings, RestTemplate restTemplate) {
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
		return String.class;
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
	public String originIp() {
		URI uri = qb().path("/ip").build().toUri();
		
		@SuppressWarnings("unchecked")
		ResponseEntity<String> responseEntity = 
				(ResponseEntity<String>) createRequest(HttpMethod.GET, uri, String.class);
		
		if (responseEntity != null) {
			return responseEntity.getBody();
		}
		
		// TODO throw exception
		
		return null;
	}

	@Override
	public List<String> proxiedIps() {
		URI uri = qb().path("/ips").build().toUri();
		
		@SuppressWarnings("unchecked")
		ResponseEntity<String> responseEntity = 
				(ResponseEntity<String>) createRequest(HttpMethod.GET, uri, String.class);
		
		if (responseEntity != null && responseEntity.getBody() != null && !responseEntity.getBody().isEmpty()) {
			String[] ipAddresses = delimitedListToStringArray(responseEntity.getBody(), ",", "[] ");
			if (ipAddresses != null) {
				List<String> list = new ArrayList<String>(asList(ipAddresses));
				list.removeAll(asList("", null));
				return list;
			}
		}
		
		// TODO throw exception
		
		return null;
	}

}
