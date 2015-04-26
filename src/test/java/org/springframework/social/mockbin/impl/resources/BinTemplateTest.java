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
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_PLAIN;
import static org.springframework.social.har.dto.mocks.MockHar.alfHarWithLogContainingSingleEntry;
import static org.springframework.social.har.dto.mocks.MockHarResponses.alfHarResponseWithEightHeadersAndHelloWorldHtmlBody;
import static org.springframework.social.har.dto.mocks.MockHarResponses.helloWorldHtmlContent;
import static org.springframework.social.har.dto.mocks.MockHttpHeaders.tenHttpHeaders;
import static org.springframework.social.har.dto.mocks.MockResponseEntities.responseEntityWithTenHeadersAndHelloWorldHtml;
import static org.springframework.social.test.BaseApiTestUtils.readFileAsJsonString;
import static org.springframework.social.test.BaseApiTestUtils.readFileAsString;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withCreatedEntity;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.client.response.DefaultResponseCreator;

import com.sportslabs.amp.har.dto.alf.har.AlfHar;
import com.sportslabs.amp.har.dto.alf.har.entries.AlfHarResponse;

/**
 * @author robin
 *
 */
public class BinTemplateTest extends AbstractMockbinResourceTest {
	
	private static final String RESOURCE_FOLDER = "mockbin/bins";
	
	private final String expectedBinId = "800a818b-5fb6-40d4-a342-75a1fb8599db";
	
	@Test
	public void create_WithAlfHarResponseOf201CreatedAndHelloWorldJson_ReturnsLocationHeaderAndBinIdOfNewlyCreatedBin() throws URISyntaxException, IOException {
		URI expectedUri = uriBuilder().path("/bin/create").build().toUri();
		
		String alfHarResponseJson = readFileAsString(RESOURCE_FOLDER, "GET_response_AlfHarResponse_InspectBin.json");
		
		DefaultResponseCreator response = 
				withCreatedEntity(new URI("/bin/" + expectedBinId))
					.contentType(TEXT_PLAIN)
					.body(expectedBinId);
		
		mockServer.expect(requestTo(expectedUri))
			.andExpect(method(POST))
			.andExpect(content().string(alfHarResponseJson))
			.andRespond(response);
		
		String newBinId = mockbin.binOperations().create(alfHarResponseWithEightHeadersAndHelloWorldHtmlBody());
		
		assertThat(newBinId, equalTo(expectedBinId));
	}
	
	@Test
	public void inspect_WithBinId_ReturnsAlfHarResponseWithTenHeadersAndHelloWorldHtmlBody() throws Exception {
		URI expectedUri = uriBuilder().path("/bin/{id}/view").buildAndExpand(expectedBinId).toUri();
		
		String alfHarResponseJson = readFileAsString(RESOURCE_FOLDER, "GET_response_AlfHarResponse_InspectBin.json");
		
		mockServer.expect(requestTo(expectedUri))
			.andExpect(method(GET))
			.andExpect(header(ACCEPT, "application/json, application/yaml, application/xml, application/*+json, text/xml, application/*+xml"))
			.andRespond(withSuccess(alfHarResponseJson, APPLICATION_JSON));
		
		AlfHarResponse response = mockbin.binOperations().inspect(expectedBinId);
		
		assertThat(response, equalTo(alfHarResponseWithEightHeadersAndHelloWorldHtmlBody()));
	}
	
	@Test
	public void inspect_WithBinIdAndAcceptYAML_ReturnsAlfHarResponseWithTenHeadersAndHelloWorldHtmlBody() throws Exception {
		URI expectedUri = uriBuilder().path("/bin/{id}/view").buildAndExpand(expectedBinId).toUri();
		
		String alfHarResponseYaml = readFileAsString(RESOURCE_FOLDER, "GET_response_AlfHarResponse_InspectBin.yml");
				
		mockServer.expect(requestTo(expectedUri))
			.andExpect(method(GET))
			.andExpect(header(ACCEPT, "application/json, application/yaml, application/xml, application/*+json, text/xml, application/*+xml"))
			.andRespond(withSuccess(alfHarResponseYaml, new MediaType("application", "yaml")));
		
		AlfHarResponse response = mockbin.binOperations().inspect(expectedBinId);
		
		assertThat(response, equalTo(alfHarResponseWithEightHeadersAndHelloWorldHtmlBody()));
	}
	
	@Test
	public void request_WithBinId_ReturnsResponseEntityWithTenHeadersAndHelloWorldHtmlBody() throws Exception {
		URI expectedUri = uriBuilder().path("/bin/{id}").buildAndExpand(expectedBinId).toUri();
		
		DefaultResponseCreator response = 
				withStatus(OK)
					.headers(tenHttpHeaders("20"))
					.body(helloWorldHtmlContent().getText());
		
		mockServer.expect(requestTo(expectedUri))
			.andExpect(method(GET))
			.andRespond(response);

		ResponseEntity<Object> responseEntity = mockbin.binOperations().request(expectedBinId);

		assertThat(responseEntity, equalTo(responseEntityWithTenHeadersAndHelloWorldHtml()));
	}
	
	@Test
	public void request_WithBinIdAndCustomMethodPathQueryHeadersAndBody_ReturnsResponseEntityWithTenHeadersAndHelloWorldHtmlBody() throws Exception {
		String path = "/extra/path";
		String query = "a=b&c=d";
		
		URI expectedUri = uriBuilder().path("/bin/{id}" + path).query(query).buildAndExpand(expectedBinId).toUri();
		
		DefaultResponseCreator response = 
				withStatus(OK)
					.headers(tenHttpHeaders("20"))
					.body(helloWorldHtmlContent().getText());
		
		mockServer.expect(requestTo(expectedUri))
			.andExpect(method(POST))
			.andExpect(header(ACCEPT, 				"text/plain, application/json, application/*+json, */*"))
			.andExpect(header(CONTENT_TYPE, 		"text/html; charset=UTF-8"))
			.andExpect(header(DATE, 				"Wed, 21 Jan 2015 23:36:35 GMT"))
			.andExpect(header(SERVER, 				"Apache"))
			.andExpect(header(TRANSFER_ENCODING,	"chunked"))
			.andExpect(header(CACHE_CONTROL, 		"max-age=7200"))
			.andExpect(header(CONNECTION, 			"Keep-Alive"))
			.andExpect(header("Keep-Alive", 		"timeout=5, max=50"))
			.andExpect(header(EXPIRES, 				"Thu, 22 Jan 2015 01:36:35 GMT"))
			.andExpect(header(CONTENT_LENGTH, 		"20"))
			.andExpect(content().string(helloWorldHtmlContent().getText()))
			.andRespond(response);
		
		ResponseEntity<Object> responseEntity = mockbin.binOperations().request(
				expectedBinId, POST, path, query, tenHttpHeaders("20"), helloWorldHtmlContent().getText());
		
		assertThat(responseEntity, equalTo(responseEntityWithTenHeadersAndHelloWorldHtml()));
	}
	
	@Test
	public void accessLog_WithBinId_ReturnsAlfHarContainAlfLogOfASingleAlfHarEntry() throws Exception {
		URI expectedUri = uriBuilder().path("/bin/{id}/log").buildAndExpand(expectedBinId).toUri();
		
		String alfHarWithLogContainingSingleEntryJson = readFileAsJsonString(
				mockbin.getObjectMapper(), RESOURCE_FOLDER, "GET_response_AlfHar_AccessLog.json");
		
		mockServer.expect(requestTo(expectedUri))
			.andExpect(method(GET))
			.andRespond(withSuccess(alfHarWithLogContainingSingleEntryJson, APPLICATION_JSON));
		
		AlfHar alfHar = mockbin.binOperations().accessLog(expectedBinId);
		
		assertThat(alfHar, equalTo(alfHarWithLogContainingSingleEntry()));
	}

}
