/**
 * 
 */
package org.springframework.social.mockbin.operations.resources;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.social.operations.resources.BaseApiResourceOperations;
import org.springframework.web.util.BaseApiUriComponentsBuilder;

import com.sportslabs.amp.har.dto.alf.har.AlfHar;
import com.sportslabs.amp.har.dto.alf.har.entries.AlfHarEntry;
import com.sportslabs.amp.har.dto.alf.har.entries.AlfHarRequest;

/**
 * Echo requests explicitly, as a {@link AlfHarRequest} (wrapped in a {@link AlfHarEntry}) or {@link AlfHar} response formats
 *
 * @author robin
 */
public interface DebuggingOperations extends BaseApiResourceOperations<BaseApiUriComponentsBuilder<?>> {
	
	/**
	 * Returns a response with identical headers and body (of type responseClass) to what is in the request
	 * 
	 * <p>You can request this endpoint with any combination of the following:
	 * 
	 * <ul>
	 * 	<li>HTTP methods (e.g. POST, XXPUT)
	 * 	<li>Path arguments (e.g. /bin/3c149e20-bc9c-4c68-8614-048e6023a108/any/extra/path/)
	 * 	<li>Query string (e.g. ?foo=bar)
	 * 	<li>HTTP headers (e.g. X-My-Header-Name: Value)
	 * 	<li>Body content (max of 100mb)
	 * </ul>
	 * 
	 * @param method The {@link HttpMethod} to use for the request
	 * @param path The additional path arguments
	 * @param query Query string
	 * @param headers HTTP headers
	 * @param body Request Body
	 * @param responseClass Response Body type
	 * @return {@link ResponseEntity} with headers and body identical to what was in the request
	 */
	public ResponseEntity<Object> echo(HttpMethod method, String path, String query, HttpHeaders headers, Object body, Class<?> responseClass);
	
	/**
	 * Returns back all the info sent through your request in <a href="http://www.softwareishard.com/blog/har-12-spec/#request">HAR Request Object</a> format.
	 * 
	 * <p>You can request this endpoint with any combination of the following:
	 * 
	 * <ul>
	 * 	<li>HTTP methods (e.g. POST, XXPUT)
	 * 	<li>Path arguments (e.g. /bin/3c149e20-bc9c-4c68-8614-048e6023a108/any/extra/path/)
	 * 	<li>Query string (e.g. ?foo=bar)
	 * 	<li>HTTP headers (e.g. X-My-Header-Name: Value)
	 * 	<li>Body content (max of 100mb)
	 * </ul>
	 * 
	 * @param method The {@link HttpMethod} to use for the request
	 * @param path The additional path arguments
	 * @param query Query string
	 * @param headers HTTP headers
	 * @param body Request Body
	 * @return {@link AlfHarEntry} wrapping the {@link AlfHarRequest} object representing the request sent
	 */
	public AlfHarEntry request(HttpMethod method, String path, String query, HttpHeaders headers, Object body);
	
	/**
	 * Returns back all the info sent through your request in <a href="http://www.softwareishard.com/blog/har-12-spec/">HAR Object</a> format.
	 * 
	 * <p>You can request this endpoint with any combination of the following:
	 * 
	 * <ul>
	 * 	<li>HTTP methods (e.g. POST, XXPUT)
	 * 	<li>Path arguments (e.g. /bin/3c149e20-bc9c-4c68-8614-048e6023a108/any/extra/path/)
	 * 	<li>Query string (e.g. ?foo=bar)
	 * 	<li>HTTP headers (e.g. X-My-Header-Name: Value)
	 * 	<li>Body content (max of 100mb)
	 * </ul>
	 * 
	 * @param method The {@link HttpMethod} to use for the request
	 * @param path The additional path arguments
	 * @param query Query string
	 * @param headers HTTP headers
	 * @param body Request Body
	 * @return {@link AlfHar} HAR Object representing all the information sent through the request
	 */
	public AlfHar har(HttpMethod method, String path, String query, HttpHeaders headers, Object body);

}
