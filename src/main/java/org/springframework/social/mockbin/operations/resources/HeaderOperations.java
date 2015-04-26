/**
 * 
 */
package org.springframework.social.mockbin.operations.resources;

import org.springframework.http.HttpHeaders;
import org.springframework.social.mockbin.dto.RequestHeaders;
import org.springframework.social.operations.resources.BaseApiResourceOperations;
import org.springframework.web.util.BaseApiUriComponentsBuilder;

import com.sportslabs.amp.har.dto.entries.HarHeader;

/**
 * List HTTP Headers used in request, and return value of a particular header
 * 
 * @author robin
 */
public interface HeaderOperations extends BaseApiResourceOperations<BaseApiUriComponentsBuilder<?>> {

	/**
	 * Returns list of all headers used in the request as well as total number of bytes
	 * 
	 * @param headers HTTP headers to include in the request
	 * @return {@link RequestHeaders} object that wraps a List of {@link HarHeader}s and the headers size 
	 */
	public RequestHeaders get(HttpHeaders headers);
	
	/**
	 * Returns the value of header with the supplied name
	 * 
	 * @param headers HTTP headers to include in the request
	 * @param headerName The name of the header whose value is to be returned
	 * @return The value of the header with the supplied name
	 */
	public String headerValue(HttpHeaders headers, String headerName);
	
	/**
	 * @return The value of the {@link HttpHeaders#USER_AGENT} header
	 */
	public String userAgent();
	
}
