/**
 * 
 */
package org.springframework.social.mockbin;

import org.springframework.social.BaseApi;
import org.springframework.social.mockbin.operations.resources.BinOperations;
import org.springframework.social.mockbin.operations.resources.CompressionOperations;
import org.springframework.social.mockbin.operations.resources.CookieOperations;
import org.springframework.social.mockbin.operations.resources.DebuggingOperations;
import org.springframework.social.mockbin.operations.resources.DelayOperations;
import org.springframework.social.mockbin.operations.resources.HeaderOperations;
import org.springframework.social.mockbin.operations.resources.IpOperations;
import org.springframework.social.mockbin.operations.resources.RedirectOperations;
import org.springframework.social.mockbin.operations.resources.StatusOperations;
import org.springframework.social.mockbin.operations.resources.StreamOperations;

import com.sportslabs.amp.har.dto.alf.har.AlfHar;
import com.sportslabs.amp.har.dto.alf.har.entries.AlfHarEntry;
import com.sportslabs.amp.har.dto.alf.har.entries.AlfHarRequest;

/**
 * Mockbin allows you to generate custom endpoints to test, mock, and track 
 * HTTP requests & responses between libraries, sockets and APIs.
 * 
 * <p>Bin Operations:
 * 
 * <ul>
 * 	<li>{@link BinOperations}: 			Create, Inspect and Request Bins and view the Bin Access Log
 * </ul>
 * 
 * <p>Utility Operations:
 * 
 * <ul>
 * 	<li> {@link DebuggingOperations}: 	Echo requests explicitly, or as a {@link AlfHarRequest} (wrapped in a {@link AlfHarEntry}) or {@link AlfHar} response formats
 * 	<li> {@link IpOperations}: 			Return Origin or Proxied IP addresses
 * 	<li> {@link StatusOperations}: 		Return Custom HTTP Status responses
 * 	<li> {@link HeaderOperations}: 		List HTTP Headers used in request, and return value of a particular header
 * 	<li> {@link CookieOperations}: 		List Cookies used in request, and return value of a particular cookie
 * 	<li> {@link RedirectOperations}: 	Redirect requests and (optionally) redirect a number of times, customize the status code and/or the URL to redirect to 
 * 	<li> {@link StreamOperations}: 		Streams a chunked response
 * 	<li> {@link DelayOperations}: 		Return responses after a default or specified delay
 *	<li> {@link CompressionOperations}:	Return a response with a gzipped response body
 * </ul>
 *
 * @author robin
 */
public interface Mockbin extends BaseApi {
	
	public static final String MOCKBIN_PROVIDER_ID = "mockbin";
	
	// Bins
	public BinOperations binOperations();
	
	// Utility
	public DebuggingOperations debuggingOperations();
	public IpOperations ipOperations();
	public StatusOperations statusOperations();
	public HeaderOperations headerOperations();
	public CookieOperations cookieOperations();
	public RedirectOperations redirectOperations();
	public StreamOperations streamOperations();
	public DelayOperations delayOperations();
	public CompressionOperations compressionOperations();

}
