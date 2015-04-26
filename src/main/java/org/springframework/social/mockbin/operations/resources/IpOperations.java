/**
 * 
 */
package org.springframework.social.mockbin.operations.resources;

import java.util.List;

import org.springframework.social.operations.resources.BaseApiResourceOperations;
import org.springframework.web.util.BaseApiUriComponentsBuilder;

/**
 * Return Origin or Proxied IP addresses
 * 
 * @author robin
 */
public interface IpOperations extends BaseApiResourceOperations<BaseApiUriComponentsBuilder<?>> {
	
	/**
	 * @return Origin IP
	 */
	public String originIp();
	
	/**
	 * Parses the "X-Forwarded-For" IP address list and returns an array. Otherwise, an empty array is returned.
	 * 
	 * @return List of proxied IP addresses
	 */
	public List<String> proxiedIps();

}
