/**
 * 
 */
package org.springframework.social.mockbin.impl.resources;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpHeaders.CONNECTION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpHeaders.TRANSFER_ENCODING;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;
import static org.springframework.social.test.BaseApiTestUtils.readFileAsString;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

import java.net.URI;

import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

/**
 * @author robin
 *
 */
public class StreamTemplateTest extends AbstractMockbinResourceTest {
	
	private static final String RESOURCE_FOLDER = "mockbin/streams";
	
	@Test
	public void output_WithChunkSizeOf4_ReturnsTransferEncodedHeaderAnd4StreamChunks() throws Exception {
		URI expectedUri = uriBuilder().path("/stream/4").build().toUri();
		
		String streamOutput = readFileAsString(RESOURCE_FOLDER, "GET_response_Streams_StreamOutput.json");
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(CONTENT_TYPE, TEXT_PLAIN_VALUE);
		headers.add(TRANSFER_ENCODING, "chunked");
		headers.add(CONNECTION, "keep-alive");
		
		mockServer.expect(requestTo(expectedUri))
			.andExpect(method(GET))
			.andRespond(withStatus(OK).body(streamOutput).headers(headers));

		ResponseEntity<String> streams = mockbin.streamOperations().output(4);
		
		assertThat(streams, equalTo(new ResponseEntity<String>(streamOutput, headers, OK)));
	}
	
}
