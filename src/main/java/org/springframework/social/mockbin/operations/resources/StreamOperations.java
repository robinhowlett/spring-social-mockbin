/**
 * 
 */
package org.springframework.social.mockbin.operations.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.social.operations.resources.BaseApiResourceOperations;
import org.springframework.web.util.BaseApiUriComponentsBuilder;

/**
 * Streams a chunked response
 * 
 * @author robin
 */
public interface StreamOperations extends BaseApiResourceOperations<BaseApiUriComponentsBuilder<?>> {

	/**
	 * Streams a chunked response, defaults to 10 chunks with an upper limit of 100
	 * 
	 * @param numberOfChunks The number of chunks to return; default value is 10 if null
	 * @return {@link ResponseEntity} of the chunked response
	 */
	public ResponseEntity<String> output(Integer numberOfChunks);
	
}
