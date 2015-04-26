/**
 * 
 */
package org.springframework.social.mockbin.impl.resources;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpHeaders.CACHE_CONTROL;
import static org.springframework.http.HttpHeaders.CONNECTION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpHeaders.DATE;
import static org.springframework.http.HttpHeaders.EXPIRES;
import static org.springframework.http.HttpHeaders.SERVER;
import static org.springframework.http.HttpHeaders.TRANSFER_ENCODING;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_HTML;
import static org.springframework.http.MediaType.TEXT_HTML_VALUE;
import static org.springframework.social.har.dto.mocks.MockHar.alfHarWithLogContainingSingleEntry;
import static org.springframework.social.har.dto.mocks.MockHarRequests.getBinEntryWithRequestWithTenHeaders;
import static org.springframework.social.har.dto.mocks.MockHarResponses.helloWorldHtmlContent;
import static org.springframework.social.har.dto.mocks.MockHttpHeaders.tenHttpHeaders;
import static org.springframework.social.har.dto.mocks.MockResponseEntities.responseEntityWithContentTypeHeaderAndHelloWorldHtml;
import static org.springframework.social.test.BaseApiTestUtils.readFileAsJsonString;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.net.URI;

import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.client.response.DefaultResponseCreator;

import com.sportslabs.amp.har.dto.alf.har.AlfHar;
import com.sportslabs.amp.har.dto.alf.har.entries.AlfHarEntry;

/**
 * @author robin
 *
 */
public class DebuggingTemplateTest extends AbstractMockbinResourceTest {
	
	private static final String RESOURCE_FOLDER = "mockbin/debugging";

	@Test
	public void echo_WithFooBarJsonBody_ReturnsResponseEntityWithFooBarJsonBody() throws Exception {
		String path = "/extra/path";
		String query = "a=b&c=d";
		
		URI expectedUri = uriBuilder().path("/echo" + path).query(query).build().toUri();
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(CONTENT_TYPE, TEXT_HTML_VALUE);
		
		mockServer.expect(requestTo(expectedUri))
			.andExpect(method(POST))
			.andExpect(header(CONTENT_TYPE, TEXT_HTML_VALUE))
			.andRespond(withSuccess(helloWorldHtmlContent().getText(), TEXT_HTML));

		ResponseEntity<Object> responseEntity = 
				mockbin.debuggingOperations().echo(POST, path, query, headers, helloWorldHtmlContent().getText(), String.class);
		
		assertThat(responseEntity, equalTo(responseEntityWithContentTypeHeaderAndHelloWorldHtml()));
	}
	
	@Test
	public void request_WithCustomMethodPathQueryHeadersAndBody_ReturnsResponseEntityWithTenHeadersAndHelloWorldHtmlBody() throws Exception {
		String path = "/extra/path";
		String query = "a=b&c=d";
		
		URI expectedUri = uriBuilder().path("/request" + path).query(query).build().toUri();
		
		String alfHarEntryWithRequestJson = readFileAsJsonString(
				mockbin.getObjectMapper(), RESOURCE_FOLDER, "GET_request_AlfHarEntryWithRequest_HttpRequest.json");
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(CONTENT_TYPE, APPLICATION_JSON_VALUE);
		
		DefaultResponseCreator response = 
				withStatus(OK)
					.body(alfHarEntryWithRequestJson)
					.headers(headers);
		
		mockServer.expect(requestTo(expectedUri))
			.andExpect(method(POST))
			.andExpect(header(CONTENT_TYPE, 		"text/html; charset=UTF-8"))
			.andExpect(header(DATE, 				"Wed, 21 Jan 2015 23:36:35 GMT"))
			.andExpect(header(SERVER, 				"Apache"))
			.andExpect(header(TRANSFER_ENCODING,	"chunked"))
			.andExpect(header(CACHE_CONTROL, 		"max-age=7200"))
			.andExpect(header(CONNECTION, 			"Keep-Alive"))
			.andExpect(header("Keep-Alive", 		"timeout=5, max=50"))
			.andExpect(header(EXPIRES, 				"Thu, 22 Jan 2015 01:36:35 GMT"))
			.andExpect(content().string(helloWorldHtmlContent().getText()))
			.andRespond(response);
		
		AlfHarEntry entry = 
				mockbin.debuggingOperations().request(POST, path, query, tenHttpHeaders("20"), helloWorldHtmlContent().getText());
		
		assertThat(entry, equalTo(getBinEntryWithRequestWithTenHeaders()));
	}
	
	@Test
	public void har_WithCustomMethodPathQueryHeadersAndBody_ReturnsResponseEntityWithEightHeadersAndHelloWorldHtmlBody() throws Exception {
		String path = "/extra/path";
		String query = "a=b&c=d";
		
		String alfHarWithLogContainingSingleEntryJson = readFileAsJsonString(
				mockbin.getObjectMapper(), RESOURCE_FOLDER, "GET_response_AlfHar_HttpArchive.json");
		
		URI expectedUri = uriBuilder().path("/har" + path).query(query).build().toUri();
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(CONTENT_TYPE, APPLICATION_JSON_VALUE);
		
		DefaultResponseCreator response = 
				withStatus(OK)
					.body(alfHarWithLogContainingSingleEntryJson)
					.headers(headers);
		
		mockServer.expect(requestTo(expectedUri))
			.andExpect(method(POST))
			.andExpect(header(CONTENT_TYPE, 		"text/html; charset=UTF-8"))
			.andExpect(header(DATE, 				"Wed, 21 Jan 2015 23:36:35 GMT"))
			.andExpect(header(SERVER, 				"Apache"))
			.andExpect(header(TRANSFER_ENCODING,	"chunked"))
			.andExpect(header(CACHE_CONTROL, 		"max-age=7200"))
			.andExpect(header(CONNECTION, 			"Keep-Alive"))
			.andExpect(header("Keep-Alive", 		"timeout=5, max=50"))
			.andExpect(header(EXPIRES, 				"Thu, 22 Jan 2015 01:36:35 GMT"))
			.andExpect(content().string(helloWorldHtmlContent().getText()))
			.andRespond(response);
		
		AlfHar alfHar = 
				mockbin.debuggingOperations().har(POST, path, query, tenHttpHeaders("20"), helloWorldHtmlContent().getText());
		
		assertThat(alfHar, equalTo(alfHarWithLogContainingSingleEntry()));
	}
}
