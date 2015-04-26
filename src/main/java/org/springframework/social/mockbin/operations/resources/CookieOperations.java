/**
 * 
 */
package org.springframework.social.mockbin.operations.resources;

import java.util.List;
import java.util.Map;

import org.springframework.social.operations.resources.BaseApiResourceOperations;
import org.springframework.web.util.BaseApiUriComponentsBuilder;

import com.sportslabs.amp.har.dto.entries.HarCookie;

/**
 * List Cookies used in request, and return value of a particular cookie
 * 
 * @author robin
 */
public interface CookieOperations extends BaseApiResourceOperations<BaseApiUriComponentsBuilder<?>> {

	/**
	 * Returns list of all cookies sent by the client
	 * 
	 * @param cookies HTTP cookies to include in the request
	 * @return List of {@link HarCookie} objects included in the client request
	 */
	public List<HarCookie> get(Map<String, String> cookies);
	
	/**
	 * Returns the value of the cookie with the supplied name
	 * 
	 * @param cookies HTTP cookies to include in the request
	 * @param cookieName The name of the cookie whose value is to be returned
	 * @return The value of the cookie with the supplied name
	 */
	public String cookieValue(Map<String, String> cookies, String cookieName);
	
}
