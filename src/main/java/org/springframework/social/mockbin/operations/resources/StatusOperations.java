/**
 * 
 */
package org.springframework.social.mockbin.operations.resources;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.social.mockbin.dto.StatusResponse;
import org.springframework.social.operations.resources.BaseApiResourceOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.BaseApiUriComponentsBuilder;

/**
 * Return Custom HTTP Status responses
 *
 * @author robin
 */
public interface StatusOperations extends BaseApiResourceOperations<BaseApiUriComponentsBuilder<?>> {
	
	/**
	 * Returns a response with the given {@link HttpStatus}'s code and reason phrase
	 * 
	 * @param httpStatus The {@link HttpStatus} to return. Spring's {@link RestTemplate} does not support
	 * status codes not included in the {@link HttpStatus} enum
	 * @return {@link ResponseEntity} with the status code of the supplied {@link HttpStatus} and a {@link StatusResponse} body
	 */
	public ResponseEntity<StatusResponse> customStatus(HttpStatus httpStatus);
	
	/**
	 * Returns a response with the given HTTP Status code and a custom reason phrase
	 * 
	 * @param httpStatus The {@link HttpStatus} to return. Spring's {@link RestTemplate} does not support
	 * status codes not included in the {@link HttpStatus} enum
	 * @param reason The custom reason phrase to include in the response
	 * @return {@link ResponseEntity} with the status code of the supplied {@link HttpStatus} and a {@link StatusResponse} body
	 */
	public ResponseEntity<StatusResponse> customStatus(HttpStatus httpStatus, String reason);

}
