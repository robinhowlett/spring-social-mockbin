/**
 * 
 */
package org.springframework.social.mockbin.impl.resources;

import static java.util.Arrays.asList;
import static org.springframework.http.HttpHeaders.COOKIE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.util.Assert.notNull;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.social.impl.resources.AbstractBaseApiResourceTemplate;
import org.springframework.social.mockbin.operations.resources.CookieOperations;
import org.springframework.social.settings.ClientSettings;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.BaseApiUriComponentsBuilder;

import com.sportslabs.amp.har.dto.entries.HarCookie;

/**
 * @author robin
 * @see CookieOperations
 */
public class CookieTemplate extends AbstractBaseApiResourceTemplate<BaseApiUriComponentsBuilder<?>> implements CookieOperations {
	
	public static final String RESOURCES_PATH = "/";
	public static final String RESOURCE_ID_PATH = "/{id}";

	/**
	 * @param settings
	 * @param restTemplate
	 */
	public CookieTemplate(ClientSettings settings, RestTemplate restTemplate) {
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
		return HarCookie.class;
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
	public List<HarCookie> get(Map<String, String> cookies) {
		URI uri = qb().path("/cookies").build().toUri();
		
		StringBuilder cookieValue = new StringBuilder();
		String sep = "";
		for (Entry<String, String> cookie : cookies.entrySet()) {
			cookieValue.append(sep);
			cookieValue.append(cookie.getKey() + "=" + cookie.getValue());
			sep = "; ";
		}
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(COOKIE, cookieValue.toString());
		
		@SuppressWarnings("unchecked")
		ResponseEntity<HarCookie[]> responseEntity = 
				(ResponseEntity<HarCookie[]>) createRequest(GET, uri, EMPTY_BODY, headers, HarCookie[].class);
		
		if (responseEntity != null) {
			return asList(responseEntity.getBody());
		}
		
		// TODO throw Exception
		
		return null;
	}

	@Override
	public String cookieValue(Map<String, String> cookies, String cookieName) {
		notNull(cookieName, "cookieName must not be null");
		
		URI uri = qb().path("/cookie/{name}").buildAndExpand(cookieName).toUri();
		
		StringBuilder cookieValue = new StringBuilder();
		String sep = "";
		for (Entry<String, String> cookie : cookies.entrySet()) {
			cookieValue.append(sep);
			cookieValue.append(cookie.getKey() + "=" + cookie.getValue());
			sep = "; ";
		}
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(COOKIE, cookieValue.toString());
		
		@SuppressWarnings("unchecked")
		ResponseEntity<String> responseEntity = 
				(ResponseEntity<String>) createRequest(GET, uri, EMPTY_BODY, headers, String.class);
		
		if (responseEntity != null) {
			return responseEntity.getBody();
		}
		
		// TODO throw Exception
		
		return null;
	}

}
