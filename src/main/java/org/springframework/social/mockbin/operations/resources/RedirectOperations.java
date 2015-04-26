/**
 * 
 */
package org.springframework.social.mockbin.operations.resources;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.social.operations.resources.BaseApiResourceOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.BaseApiUriComponentsBuilder;

/**
 * Redirect requests and (optionally) redirect a number of times, customize the status code and/or the URL to redirect to
 * 
 * {@link RestTemplate} will follow redirects by default
 * 
 * @author robin
 */
public interface RedirectOperations extends BaseApiResourceOperations<BaseApiUriComponentsBuilder<?>> {

	/**
	 * Return redirect response with a 302 status code and the default {@link HttpHeaders#LOCATION} value
	 * 
	 * @return {@link ResponseEntity} with the default {@link HttpStatus#FOUND} status code and the default redirect URL 
	 */
	public ResponseEntity<String> redirect();
	
	/**
	 * Return redirect response with the supplied status code and the default {@link HttpHeaders#LOCATION} value
	 * 
	 * @param status The custom {@link HttpStatus} to include in the response
	 * @return {@link ResponseEntity} with the supplied {@link HttpStatus} status code and the default redirect URL
	 */
	public ResponseEntity<String> redirect(HttpStatus status);
	
	/**
	 * Return redirect response with the supplied status code and the URL to use as the {@link HttpHeaders#LOCATION} value
	 * 
	 * @param status The custom {@link HttpStatus} to include in the response
	 * @param url The URL to redirect to; returned as {@link HttpHeaders#LOCATION} in the response
	 * @return {@link ResponseEntity} with the supplied {@link HttpStatus} status code and redirect URL
	 */
	public ResponseEntity<String> redirect(HttpStatus status, String url);
	
	/**
	 * Starts a redirect loop using the custom {@link HttpStatus} code, looping through the URL pattern {@code /redirect/:status/[:count -1]}, 
	 * eventually landing on the default redirect URL {@code /redirect/:status/0}
	 * 
	 * @param status The custom {@link HttpStatus} to include in the response
	 * @param count The number of redirect loops to perform
	 * @return {@link ResponseEntity} with the supplied {@link HttpStatus} status code and {@link HttpHeaders#LOCATION} value corresponding to 
	 * the next URL to be redirected to
	 */
	public ResponseEntity<String> redirectLoop(HttpStatus status, Integer count);
	
	/**
	 * Starts a redirect loop using the custom {@link HttpStatus} code, looping through the URL pattern {@code /redirect/:status/[:count -1]}, 
	 * eventually landing on the supplied redirect URL
	 * 
	 * @param status The custom {@link HttpStatus} to include in the response
	 * @param count The number of redirect loops to perform
	 * @param url The final URL to redirect to
	 * @return {@link ResponseEntity} with the supplied {@link HttpStatus} status code and {@link HttpHeaders#LOCATION} value corresponding to 
	 * the next URL to be redirected to
	 */
	public ResponseEntity<String> redirectLoop(HttpStatus status, Integer count, String url);
	
}
