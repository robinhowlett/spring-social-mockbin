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
import com.sportslabs.amp.har.dto.alf.har.entries.AlfHarResponse;
import com.sportslabs.amp.har.dto.alf.har.log.AlfHarLog;

/**
 * Create, Inspect and Request Bins and view the Bin Access Log
 * 
 * @author robin
 */
public interface BinOperations extends BaseApiResourceOperations<BaseApiUriComponentsBuilder<?>> {

	/**
	 * Creates a new Bin with a mock HTTP response as described by a 
	 * <a href="http://www.softwareishard.com/blog/har-12-spec/#response">HAR Response Object</a> body
	 * 
	 * @param response The {@link AlfHarResponse} sent at time of creation will determine what the response status, headers, content will be
	 * @return The Bin ID of the newly created Bin
	 */
	public String create(AlfHarResponse response);
	
	/**
	 * Responds with the <a href="http://www.softwareishard.com/blog/har-12-spec/#response">HAR Response Object</a> sent at time of creation.
	 * 
	 * @param binId ID of Bin to inspect
	 * @return The {@link AlfHarResponse} body sent at time of creation
	 */
	public AlfHarResponse inspect(String binId);
	
	/**
	 * Simple version of {@link #request(String, HttpMethod, String, String, HttpHeaders, Object)} to execute a GET request to the Bin
	 * without adding any additional path elements, query parameters, HTTP headers, or a request body.
	 * 
	 * @param binId ID of Bin to request
	 * @return {@link ResponseEntity}
	 * @see #request(String, HttpMethod, String, String, HttpHeaders, Object)
	 */
	public ResponseEntity<Object> request(String binId);
	
	/**
	 * The <a href="http://www.softwareishard.com/blog/har-12-spec/#response">HAR Response Object</a> 
	 * sent at time of creation will determine what the response status, headers, content will be.
	 * 
	 * <p>Each call to this endpoint will be logged by Mockbin (max of 100 requests).
	 * 
	 * <p>You can request this endpoint with any combination of the following:
	 * 
	 * <ul>
	 * 	<li>HTTP methods (e.g. POST, XXPUT)
	 * 	<li>HTTP headers (e.g. X-My-Header-Name: Value)
	 * 	<li>Body content (max of 100MB)
	 * 	<li>Query string (e.g. ?foo=bar)
	 * 	<li>Path arguments (e.g. /bin/3c149e20-bc9c-4c68-8614-048e6023a108/any/extra/path/)
	 * </ul>
	 * 
	 * @param binId ID of Bin to request
	 * @param method HTTP method to use
	 * @param path URL path to append
	 * @param query Query parameters to include
	 * @param headers HTTP headers to include
	 * @param body Request body to include
	 * @return {@link ResponseEntity} determined by the 
	 * <a href="http://www.softwareishard.com/blog/har-12-spec/#response">HAR Response Object</a> sent at time of creation
	 */
	public ResponseEntity<Object> request(String binId, HttpMethod method, String path, String query, HttpHeaders headers, Object body);
	
	/**
	 * List all requests made to this Bin, using <a href="http://www.softwareishard.com/blog/har-12-spec/">HAR</a> log format.
	 * 
	 * @param binId ID of Bin
	 * @return {@link AlfHar} object containing the {@link AlfHarLog} of {@link AlfHarEntry} objects, detailing request-response exchanges 
	 * on this Bin
	 */
	public AlfHar accessLog(String binId);
	
}
