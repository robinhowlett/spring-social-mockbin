/**
 * 
 */
package org.springframework.social.mockbin.settings;

import org.springframework.social.mockbin.Mockbin;
import org.springframework.social.settings.AbstractClientPlatformSettings;
import org.springframework.social.settings.ClientPlatformSettings;

/**
 * {@link ClientPlatformSettings} for {@link Mockbin}
 * 
 * <p>Default constructor configures connection to {@link mockbin.com}
 * 
 * @author robin
 */
public class MockbinClientPlatformSettings extends AbstractClientPlatformSettings {
	
	public MockbinClientPlatformSettings() {
		this("mockbin.com");
	}

	/**
	 * @param hostname
	 */
	public MockbinClientPlatformSettings(String hostname) {
		super(hostname);
	}

	/**
	 * @param scheme
	 * @param hostname
	 * @param port
	 * @param basePath
	 */
	public MockbinClientPlatformSettings(String scheme, String hostname, Integer port, String basePath) {
		super(scheme, hostname, port, basePath);
	}
}
