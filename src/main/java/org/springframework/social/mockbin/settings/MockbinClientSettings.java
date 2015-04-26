/**
 * 
 */
package org.springframework.social.mockbin.settings;

import org.springframework.social.mockbin.Mockbin;
import org.springframework.social.settings.AbstractClientSettings;
import org.springframework.social.settings.ClientSettings;

/**
 * {@link ClientSettings} for {@link Mockbin}
 *
 * <p>Default constructor uses default {@link MockbinClientPlatformSettings} that configures connection to {@link mockbin.com}
 * @author robin
 */
public class MockbinClientSettings extends AbstractClientSettings {
	
	public MockbinClientSettings() {
		super(new MockbinClientPlatformSettings());
	}

	/**
	 * @param platformSettings
	 */
	public MockbinClientSettings(MockbinClientPlatformSettings platformSettings) {
		super(platformSettings);
	}

	/* (non-Javadoc)
	 * @see org.springframework.social.settings.ClientSettings#getUserAgent()
	 */
	@Override
	public String getUserAgent() {
		return "Spring Social Mockbin";
	}

}
