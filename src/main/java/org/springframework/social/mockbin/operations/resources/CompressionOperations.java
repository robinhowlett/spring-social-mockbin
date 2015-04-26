/**
 * 
 */
package org.springframework.social.mockbin.operations.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.social.operations.resources.BaseApiResourceOperations;
import org.springframework.web.util.BaseApiUriComponentsBuilder;

/**
 * Return a response with a gzipped response body
 * 
 * @author robin
 */
public interface CompressionOperations extends BaseApiResourceOperations<BaseApiUriComponentsBuilder<?>> {
	
	/**
	 * Returns a compressed response body to a request
	 * 
	 * @return {@link ResponseEntity} containing the compressed response body of "Hello World!"
	 */
	public ResponseEntity<String> gzip();

}
