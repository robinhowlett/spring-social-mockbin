/**
 * 
 */
package org.springframework.social.mockbin.dto.mocks;


import static org.springframework.social.har.dto.mocks.MockHarHeaders.tenHarRequestHeaders;

import org.springframework.social.mockbin.dto.RequestHeaders;

/**
 * @author robin
 *
 */
public class MockRequestHeaders {
	
	public static final RequestHeaders tenHarHeadersAndHeaderSize307() {
		return new RequestHeaders(tenHarRequestHeaders("307"), 307);
	}

}
