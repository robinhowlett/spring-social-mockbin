/**
 * 
 */
package org.springframework.social.mockbin.dto;

import org.springframework.social.mockbin.operations.resources.DelayOperations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author robin
 * @see DelayOperations#delay()
 * @see DelayOperations#delay(int)
 */
public class DelayResponse {

	private final String delay;
	
	@JsonCreator
	public DelayResponse(@JsonProperty("delay") String delay) {
		super();
		this.delay = delay;
	}

	/**
	 * @return the delay
	 */
	public String getDelay() {
		return delay;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DelayResponse [delay=" + delay + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((delay == null) ? 0 : delay.hashCode());
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
		DelayResponse other = (DelayResponse) obj;
		if (delay == null) {
			if (other.delay != null)
				return false;
		} else if (!delay.equals(other.delay))
			return false;
		return true;
	}
}
