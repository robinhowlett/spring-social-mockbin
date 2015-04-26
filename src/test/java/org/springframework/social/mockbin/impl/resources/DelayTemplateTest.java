/**
 * 
 */
package org.springframework.social.mockbin.impl.resources;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;

import java.net.URI;

import org.junit.Test;
import org.springframework.social.mockbin.dto.DelayResponse;
import org.springframework.test.web.client.response.MockRestResponseCreators;

/**
 * @author robin
 *
 */
public class DelayTemplateTest extends AbstractMockbinResourceTest {
	
	@Test
	public void delay_WithDefaultDelay_ReturnsDelayResponseBody() throws Exception {
		URI expectedUri = uriBuilder().path("/delay/200").build().toUri();
		
		DelayResponse delayResponse = new DelayResponse("200");
		
		mockServer.expect(requestTo(expectedUri))
			.andExpect(method(GET))
			.andRespond(MockRestResponseCreators.withSuccess("{\"delay\": \"200\"}", APPLICATION_JSON));

		DelayResponse response = mockbin.delayOperations().delay();
		
		assertThat(response, equalTo(delayResponse));
	}
	
	@Test
	public void delay_WithProvidedDelay_ReturnsDelayResponseBody() throws Exception {
		URI expectedUri = uriBuilder().path("/delay/5000").build().toUri();
		
		DelayResponse delayResponse = new DelayResponse("5000");
		
		mockServer.expect(requestTo(expectedUri))
			.andExpect(method(GET))
			.andRespond(MockRestResponseCreators.withSuccess("{\"delay\": \"5000\"}", APPLICATION_JSON));

		DelayResponse response = mockbin.delayOperations().delay(5000);
		
		assertThat(response, equalTo(delayResponse));
	}
	
}
