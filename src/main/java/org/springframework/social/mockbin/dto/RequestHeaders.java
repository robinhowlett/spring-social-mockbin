/**
 * 
 */
package org.springframework.social.mockbin.dto;

import java.util.List;

import org.springframework.social.mockbin.operations.resources.HeaderOperations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sportslabs.amp.har.dto.entries.HarHeader;

/**
 * @author robin
 * @see HeaderOperations#get(org.springframework.http.HttpHeaders)
 */
public class RequestHeaders {
	
	private final List<HarHeader> 		headers;
	private final Integer 				headersSize;
	
	/**
	 * @param headers		List of header objects
	 * @param headersSize	Total number of bytes from the start of the HTTP request message until (and including) the double 
	 * 						CRLF before the body. Set to -1 if the info is not available
	 */
	@JsonCreator
	public RequestHeaders(
			@JsonProperty("headers") 		List<HarHeader> headers,
			@JsonProperty("headersSize") 	Integer headersSize) {
		super();
		this.headers = headers;
		this.headersSize = headersSize;
	}

	/**
	 * @return the headers
	 */
	public List<HarHeader> getHeaders() {
		return headers;
	}

	/**
	 * @return the headersSize
	 */
	public Integer getHeadersSize() {
		return headersSize;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RequestHeaders [headers=" + headers + ", headersSize="
				+ headersSize + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((headers == null) ? 0 : headers.hashCode());
		result = prime * result
				+ ((headersSize == null) ? 0 : headersSize.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RequestHeaders other = (RequestHeaders) obj;
		if (headers == null) {
			if (other.headers != null)
				return false;
		} else if (!headers.equals(other.headers))
			return false;
		if (headersSize == null) {
			if (other.headersSize != null)
				return false;
		} else if (!headersSize.equals(other.headersSize))
			return false;
		return true;
	}
	
}
