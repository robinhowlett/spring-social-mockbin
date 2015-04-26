/**
 * 
 */
package org.springframework.social.mockbin.impl.resources;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpHeaders.COOKIE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_PLAIN;
import static org.springframework.social.har.dto.mocks.MockHarCookies.cookiesAsStringMap;
import static org.springframework.social.har.dto.mocks.MockHarCookies.harCookieArrayWithTwoCookies;
import static org.springframework.social.test.BaseApiTestUtils.readFileAsJsonString;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.net.URI;
import java.util.List;

import org.junit.Test;

import com.sportslabs.amp.har.dto.entries.HarCookie;

/**
 * @author robin
 *
 */
public class CookieTemplateTest extends AbstractMockbinResourceTest {
	
	private static final String RESOURCE_FOLDER = "mockbin/cookies";
	
	@Test
	public void get_WithCookieHeaderWithTwoPairs_ReturnsRequestHeadersResponseBody() throws Exception {
		URI expectedUri = uriBuilder().path("/cookies").build().toUri();
		
		String requestHeadersJson = readFileAsJsonString(
				mockbin.getObjectMapper(), RESOURCE_FOLDER, "GET_response_HarCookies_GetCookies.json");
		
		mockServer.expect(requestTo(expectedUri))
			.andExpect(method(GET))
			.andExpect(header(COOKIE, "foo=bar; my-cookie=ALL YOUR BASE ARE BELONG TO US"))
			.andRespond(withSuccess(requestHeadersJson, APPLICATION_JSON));

		List<HarCookie> cookies = mockbin.cookieOperations().get(cookiesAsStringMap());
		
		assertThat(cookies, equalTo(harCookieArrayWithTwoCookies()));
	}
	
	@Test
	public void cookieValue_WithCookieHeaderWithTwoPairsAndMyCookieValueSought_ReturnsCorrectCookieValueString() throws Exception {
		String cookieName = "my-cookie";
		
		URI expectedUri = uriBuilder().path("/cookie/{name}").buildAndExpand(cookieName).toUri();
		
		String body = "ALL YOUR BASE ARE BELONG TO US";
		
		mockServer.expect(requestTo(expectedUri))
			.andExpect(method(GET))
			.andExpect(header(COOKIE, "foo=bar; my-cookie=ALL YOUR BASE ARE BELONG TO US"))
			.andRespond(withSuccess(body, TEXT_PLAIN));

		String headerValue = mockbin.cookieOperations().cookieValue(cookiesAsStringMap(), cookieName);
		
		assertThat(headerValue, equalTo(body));
	}
	
}
