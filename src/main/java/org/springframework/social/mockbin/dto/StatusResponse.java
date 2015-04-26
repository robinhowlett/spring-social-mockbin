/**
 * 
 */
package org.springframework.social.mockbin.dto;

import org.springframework.social.mockbin.operations.resources.StatusOperations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author robin
 * @see StatusOperations#customStatus(org.springframework.http.HttpStatus)
 * @see StatusOperations#customStatus(org.springframework.http.HttpStatus, String)
 */
public class StatusResponse {
	
	private final Integer code;
	private final String message;

	@JsonCreator
	public StatusResponse(@JsonProperty("code") Integer code, @JsonProperty("message") String message) {
		this.code = code;
		this.message = message;
	}

	/**
	 * @return the code
	 */
	public Integer getCode() {
		return code;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "StatusResponse [code=" + code + ", message=" + message + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
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
		StatusResponse other = (StatusResponse) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		return true;
	}
	
}
