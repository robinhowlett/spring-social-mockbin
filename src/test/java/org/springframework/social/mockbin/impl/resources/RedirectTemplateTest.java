/**
 * 
 */
package org.springframework.social.mockbin.impl.resources;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.MOVED_PERMANENTLY;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.PERMANENT_REDIRECT;
import static org.springframework.http.MediaType.TEXT_PLAIN;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.net.URI;

import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

/**
 * @author robin
 *
 */
public class RedirectTemplateTest extends AbstractMockbinResourceTest {
	
	@Test
	public void redirect_RoutesRequestToCorrectRedirectUrl() throws Exception {
		URI expectedUri = uriBuilder().path("/redirect/302").build().toUri();
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(CONTENT_TYPE, TEXT_PLAIN_VALUE);
		
		ResponseEntity<String> expectedResponseEntity = 
				new ResponseEntity<String>("redirect finished", headers, OK);
		
		mockServer.expect(requestTo(expectedUri))
			.andExpect(method(GET))
			.andRespond(withSuccess("redirect finished", TEXT_PLAIN));

		ResponseEntity<String> responseEntity = mockbin.redirectOperations().redirect();
		
		assertThat(responseEntity, equalTo(expectedResponseEntity));
	}
	
	@Test
	public void redirect_With308Status_RoutesRequestToCorrectRedirectUrl() throws Exception {
		URI expectedUri = uriBuilder().path("/redirect/308").build().toUri();
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(CONTENT_TYPE, TEXT_PLAIN_VALUE);
		
		ResponseEntity<String> expectedResponseEntity = 
				new ResponseEntity<String>("redirect finished", headers, OK);
		
		mockServer.expect(requestTo(expectedUri))
			.andExpect(method(GET))
			.andRespond(withSuccess("redirect finished", TEXT_PLAIN));

		ResponseEntity<String> responseEntity = mockbin.redirectOperations().redirect(PERMANENT_REDIRECT);
		
		assertThat(responseEntity, equalTo(expectedResponseEntity));
	}
	
	@Test
	public void redirect_With308StatusAndExampleComUrl_RoutesRequestToCorrectRedirectUrl() throws Exception {
		URI expectedUri = uriBuilder().path("/redirect/308").queryParam("to", "http://example.com").build().toUri();
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(CONTENT_TYPE, TEXT_PLAIN_VALUE);
		
		ResponseEntity<String> expectedResponseEntity = 
				new ResponseEntity<String>("redirect finished", headers, OK);
		
		mockServer.expect(requestTo(expectedUri))
			.andExpect(method(GET))
			.andRespond(withSuccess("redirect finished", TEXT_PLAIN));

		ResponseEntity<String> responseEntity = mockbin.redirectOperations().redirect(PERMANENT_REDIRECT, "http://example.com");
		
		assertThat(responseEntity, equalTo(expectedResponseEntity));
	}
	
	@Test
	public void redirectLoop_With301StatusAnd3Loops_RoutesRequestToCorrectRedirectUrl() throws Exception {
		URI expectedUri = uriBuilder().path("/redirect/301/3").build().toUri();
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(CONTENT_TYPE, TEXT_PLAIN_VALUE);
		
		ResponseEntity<String> expectedResponseEntity = 
				new ResponseEntity<String>("redirect finished", headers, OK);
		
		mockServer.expect(requestTo(expectedUri))
			.andExpect(method(GET))
			.andRespond(withSuccess("redirect finished", TEXT_PLAIN));

		ResponseEntity<String> responseEntity = mockbin.redirectOperations().redirectLoop(MOVED_PERMANENTLY, 3);
		
		assertThat(responseEntity, equalTo(expectedResponseEntity));
	}
	
	@Test
	public void redirectLoop_With301StatusAnd3LoopsAndExampleComUrl_RoutesRequestToCorrectRedirectUrl() throws Exception {
		URI expectedUri = uriBuilder().path("/redirect/301/3").queryParam("to", "http://example.com").build().toUri();
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(CONTENT_TYPE, TEXT_PLAIN_VALUE);
		
		ResponseEntity<String> expectedResponseEntity = 
				new ResponseEntity<String>("redirect finished", headers, OK);
		
		mockServer.expect(requestTo(expectedUri))
			.andExpect(method(GET))
			.andRespond(withSuccess("redirect finished", TEXT_PLAIN));

		ResponseEntity<String> responseEntity = mockbin.redirectOperations().redirectLoop(MOVED_PERMANENTLY, 3, "http://example.com");
		
		assertThat(responseEntity, equalTo(expectedResponseEntity));
	}
	
}
