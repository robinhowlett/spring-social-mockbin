/**
 * 
 */
package org.springframework.social.mockbin.impl.resources;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.TEXT_PLAIN;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * @author robin
 *
 */
public class IpTemplateTest extends AbstractMockbinResourceTest {
	
	@Test
	public void ip_ReturnsIpAddressString() throws Exception {
		URI expectedUri = uriBuilder().path("/ip").build().toUri();
		
		String ipAddress = "10.10.10.1";
		
		mockServer.expect(requestTo(expectedUri))
			.andExpect(method(GET))
			.andRespond(withSuccess(ipAddress, TEXT_PLAIN));

		String originIp = mockbin.ipOperations().originIp();
		
		assertThat(originIp, equalTo(ipAddress));
	}
	
	@Test
	public void ips_ReturnsListOfIpAddressStrings() throws Exception {
		URI expectedUri = uriBuilder().path("/ips").build().toUri();
		
		String ipAddress1 = "10.10.10.1";
		String ipAddress2 = "10.10.10.2";
		String ipAddress3 = "10.10.10.3";
		
		List<String> ipAddresses = new ArrayList<String>();
		ipAddresses.add(ipAddress1);
		ipAddresses.add(ipAddress2);
		ipAddresses.add(ipAddress3);
		
		String response = "[" + ipAddress1 + ", " + ipAddress2 + ", " + ipAddress3 + "]";
		
		mockServer.expect(requestTo(expectedUri))
			.andExpect(method(GET))
			.andRespond(withSuccess(response, TEXT_PLAIN));

		List<String> proxiedIps = mockbin.ipOperations().proxiedIps();
		
		assertThat(proxiedIps, equalTo(ipAddresses));
	}
	
	@Test
	public void ips_ReturnsEmptyListOfIpAddressStrings() throws Exception {
		URI expectedUri = uriBuilder().path("/ips").build().toUri();
		
		String response = "[]";
		
		mockServer.expect(requestTo(expectedUri))
			.andExpect(method(GET))
			.andRespond(withSuccess(response, TEXT_PLAIN));

		List<String> proxiedIps = mockbin.ipOperations().proxiedIps();
		
		assertTrue(proxiedIps.isEmpty());
	}
	
}
