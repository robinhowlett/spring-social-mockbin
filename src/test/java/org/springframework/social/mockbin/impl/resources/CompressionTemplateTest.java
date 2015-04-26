/**
 * 
 */
package org.springframework.social.mockbin.impl.resources;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpHeaders.CONTENT_ENCODING;
import static org.springframework.http.HttpHeaders.TRANSFER_ENCODING;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.TEXT_PLAIN;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

/**
 * @author robin
 *
 */
public class CompressionTemplateTest extends AbstractMockbinResourceTest {

	@Test
	public void gzip_ReturnsCompressedHelloWorldResponse() throws Exception {
		URI expectedUri = uriBuilder().path("/gzip").build().toUri();
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(CONTENT_ENCODING, "gzip");
		headers.add(TRANSFER_ENCODING, "chunked");
		
		String helloWorldCompressed = compressHelloWorld().toString(UTF_8.name());
		
		mockServer.expect(requestTo(expectedUri))
			.andExpect(method(GET))
			.andRespond(withSuccess(helloWorldCompressed, TEXT_PLAIN).headers(headers));

		ResponseEntity<String> responseEntity = mockbin.compressionOperations().gzip();
		
		assertThat(responseEntity.getBody(), equalTo(helloWorldCompressed));
	}
	
	private ByteArrayOutputStream compressHelloWorld() throws IOException, UnsupportedEncodingException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
		gzipOutputStream.write("Hello World!".getBytes(UTF_8));
		gzipOutputStream.close();
		
		return byteArrayOutputStream;
	}
	
}
