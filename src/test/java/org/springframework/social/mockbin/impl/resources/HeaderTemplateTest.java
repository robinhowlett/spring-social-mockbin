/**
 * 
 */
package org.springframework.social.mockbin.impl.resources;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.CACHE_CONTROL;
import static org.springframework.http.HttpHeaders.CONNECTION;
import static org.springframework.http.HttpHeaders.CONTENT_LENGTH;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpHeaders.DATE;
import static org.springframework.http.HttpHeaders.EXPIRES;
import static org.springframework.http.HttpHeaders.SERVER;
import static org.springframework.http.HttpHeaders.TRANSFER_ENCODING;
import static org.springframework.http.HttpHeaders.USER_AGENT;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_PLAIN;
import static org.springframework.social.har.dto.mocks.MockHttpHeaders.tenHttpHeaders;
import static org.springframework.social.mockbin.dto.mocks.MockRequestHeaders.tenHarHeadersAndHeaderSize307;
import static org.springframework.social.test.BaseApiTestUtils.readFileAsJsonString;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.net.URI;

import org.junit.Test;
import org.springframework.social.mockbin.dto.RequestHeaders;

/**
 * @author robin
 *
 */
public class HeaderTemplateTest extends AbstractMockbinResourceTest {
	
	private static final String RESOURCE_FOLDER = "mockbin/headers";
	
	@Test
	public void get_WithTenHeaders_ReturnsRequestHeadersResponseBody() throws Exception {
		URI expectedUri = uriBuilder().path("/headers").build().toUri();
		
		String requestHeadersJson = readFileAsJsonString(
				mockbin.getObjectMapper(), RESOURCE_FOLDER, "GET_response_RequestHeaders_GetHeaders.json");
		
		mockServer.expect(requestTo(expectedUri))
			.andExpect(method(GET))
			.andExpect(header(ACCEPT, 				"text/plain, application/json, application/*+json, */*"))
			.andExpect(header(CONTENT_TYPE, 		"text/html; charset=UTF-8"))
			.andExpect(header(DATE, 				"Wed, 21 Jan 2015 23:36:35 GMT"))
			.andExpect(header(SERVER, 				"Apache"))
			.andExpect(header(TRANSFER_ENCODING,	"chunked"))
			.andExpect(header(CACHE_CONTROL, 		"max-age=7200"))
			.andExpect(header(CONNECTION, 			"Keep-Alive"))
			.andExpect(header("Keep-Alive", 		"timeout=5, max=50"))
			.andExpect(header(EXPIRES, 				"Thu, 22 Jan 2015 01:36:35 GMT"))
			.andExpect(header(CONTENT_LENGTH, 		"0"))
			.andRespond(withSuccess(requestHeadersJson, APPLICATION_JSON));

		RequestHeaders requestHeaders = mockbin.headerOperations().get(tenHttpHeaders("0"));
		
		assertThat(requestHeaders, equalTo(tenHarHeadersAndHeaderSize307()));
	}
	
	@Test
	public void headerValue_WithTenHeadersAndCacheControlHeaderName_ReturnsMaxAge7200() throws Exception {
		URI expectedUri = uriBuilder().path("/header/cache-control").build().toUri();
		
		String expectedCacheControlValue = "max-age=7200";
		
		mockServer.expect(requestTo(expectedUri))
			.andExpect(method(GET))
			.andExpect(header(ACCEPT, 				"text/plain, application/json, application/*+json, */*"))
			.andExpect(header(CONTENT_TYPE, 		"text/html; charset=UTF-8"))
			.andExpect(header(DATE, 				"Wed, 21 Jan 2015 23:36:35 GMT"))
			.andExpect(header(SERVER, 				"Apache"))
			.andExpect(header(TRANSFER_ENCODING,	"chunked"))
			.andExpect(header(CACHE_CONTROL, 		expectedCacheControlValue))
			.andExpect(header(CONNECTION, 			"Keep-Alive"))
			.andExpect(header("Keep-Alive", 		"timeout=5, max=50"))
			.andExpect(header(EXPIRES, 				"Thu, 22 Jan 2015 01:36:35 GMT"))
			.andExpect(header(CONTENT_LENGTH, 		"0"))
			.andRespond(withSuccess(expectedCacheControlValue, TEXT_PLAIN));

		String headerValue = mockbin.headerOperations().headerValue(tenHttpHeaders("0"), "cache-control");
		
		assertThat(headerValue, equalTo(expectedCacheControlValue));
	}
	
	@Test
	public void userAgent_WithSpringSocialMockbinValue_ReturnsSpringSocialMockbinString() throws Exception {
		URI expectedUri = uriBuilder().path("/agent").build().toUri();
		
		String expectedUserAgentValue = "Spring Social Mockbin";
		
		mockServer.expect(requestTo(expectedUri))
			.andExpect(method(GET))
			.andExpect(header(USER_AGENT, expectedUserAgentValue))
			.andRespond(withSuccess(expectedUserAgentValue, TEXT_PLAIN));

		String headerValue = mockbin.headerOperations().userAgent();
		
		assertThat(headerValue, equalTo(expectedUserAgentValue));
	}
	
}
