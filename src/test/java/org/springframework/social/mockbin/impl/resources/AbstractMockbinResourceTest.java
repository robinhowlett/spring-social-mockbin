/**
 * 
 */
package org.springframework.social.mockbin.impl.resources;

import static org.springframework.test.web.client.MockRestServiceServer.createServer;
import static org.springframework.web.util.UriComponentsBuilder.fromUriString;

import org.junit.After;
import org.junit.Before;
import org.springframework.social.mockbin.Mockbin;
import org.springframework.social.mockbin.impl.MockbinTemplate;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author robin
 *
 */
public abstract class AbstractMockbinResourceTest {
	
	protected Mockbin mockbin;
	protected MockRestServiceServer mockServer;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		mockbin = new MockbinTemplate();
		
		mockServer = createServer(mockbin.getRestTemplate());
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		mockServer = null;
		mockbin = null;
	}
	
	protected UriComponentsBuilder uriBuilder() {
		return fromUriString("http://mockbin.com");
	}

}
