/**
 * 
 */
package org.springframework.social.mockbin.operations.resources;

import org.springframework.social.mockbin.dto.DelayResponse;
import org.springframework.social.operations.resources.BaseApiResourceOperations;
import org.springframework.web.util.BaseApiUriComponentsBuilder;

/**
 * Return responses after a default or specified delay
 * 
 * @author robin
 */
public interface DelayOperations extends BaseApiResourceOperations<BaseApiUriComponentsBuilder<?>> {
	
	/**
	 * Returns a response after a delay of 200ms
	 * 
	 * @return {@link DelayResponse} with a {@code delay} value of "200"
	 */
	public DelayResponse delay();
	
	/**
	 * Returns a response after a delay of {@code n} milliseconds
	 * 
	 * @param milliseconds The number of milliseconds for the response to be delayed
	 * @return {@link DelayResponse} with a {@code delay} value matching the supplied {@code milliseconds} parameter
	 */
	public DelayResponse delay(int milliseconds);

}
