/**
 * 
 */
package org.springframework.social.mockbin.impl.resources;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.SEE_OTHER;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

import java.net.URI;

import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.social.mockbin.dto.StatusResponse;
import org.springframework.test.web.client.response.DefaultResponseCreator;

/**
 * @author robin
 *
 */
public class StatusTemplateTest extends AbstractMockbinResourceTest {
	
	@Test
	public void status_WithCode_ReturnsCorrectStatusCodeAndReasonAndStatusResponseBody() throws Exception {
		HttpStatus httpStatus = SEE_OTHER;
		
		URI expectedUri = uriBuilder().path("/status/{code}/See Other").buildAndExpand(httpStatus.value()).toUri();
		
		StatusResponse statusResponse = new StatusResponse(httpStatus.value(), httpStatus.getReasonPhrase());
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(CONTENT_TYPE, APPLICATION_JSON_VALUE);
		
		ResponseEntity<StatusResponse> expectedResponseEntity = 
				new ResponseEntity<StatusResponse>(statusResponse, headers, SEE_OTHER);
		
		DefaultResponseCreator response = 
				withStatus(SEE_OTHER)
					.body("{\"code\": 303, \"message\": \"See Other\"}")
					.contentType(APPLICATION_JSON);
		
		mockServer.expect(requestTo(expectedUri))
			.andExpect(method(GET))
			.andRespond(response);

		ResponseEntity<StatusResponse> responseEntity = 
				mockbin.statusOperations().customStatus(httpStatus);
		
		assertThat(responseEntity, equalTo(expectedResponseEntity));
	}
	
	@Test
	public void status_WithCodeAndReason_ReturnsCorrectStatusCodeAndReasonAndStatusResponseBody() throws Exception {
		HttpStatus httpStatus = SEE_OTHER;
		String reason = "OK";
		
		URI expectedUri = uriBuilder().path("/status/{code}/{reason}").buildAndExpand(httpStatus.value(), reason).toUri();
		
		StatusResponse statusResponse = new StatusResponse(httpStatus.value(), reason);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(CONTENT_TYPE, APPLICATION_JSON_VALUE);
		
		ResponseEntity<StatusResponse> expectedResponseEntity = 
				new ResponseEntity<StatusResponse>(statusResponse, headers, OK);
		
		DefaultResponseCreator response = 
				withStatus(OK)
					.body("{\"code\": 303, \"message\": \"OK\"}")
					.contentType(APPLICATION_JSON);
		
		mockServer.expect(requestTo(expectedUri))
			.andExpect(method(GET))
			.andRespond(response);

		ResponseEntity<StatusResponse> responseEntity = mockbin.statusOperations().customStatus(httpStatus, reason);
		
		assertThat(responseEntity, equalTo(expectedResponseEntity));
	}
	
}
