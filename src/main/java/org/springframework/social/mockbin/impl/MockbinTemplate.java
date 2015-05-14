/**
 * 
 */
package org.springframework.social.mockbin.impl;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.MapperFeature.SORT_PROPERTIES_ALPHABETICALLY;
import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;
import static com.fasterxml.jackson.databind.SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.http.converter.yaml.snake.JavaBeanSnakeYamlHttpMessageConverter;
import org.springframework.social.BaseApi;
import org.springframework.social.impl.AbstractBaseApiTemplate;
import org.springframework.social.mockbin.Mockbin;
import org.springframework.social.mockbin.impl.resources.BinTemplate;
import org.springframework.social.mockbin.impl.resources.CompressionTemplate;
import org.springframework.social.mockbin.impl.resources.CookieTemplate;
import org.springframework.social.mockbin.impl.resources.DebuggingTemplate;
import org.springframework.social.mockbin.impl.resources.DelayTemplate;
import org.springframework.social.mockbin.impl.resources.HeaderTemplate;
import org.springframework.social.mockbin.impl.resources.IpTemplate;
import org.springframework.social.mockbin.impl.resources.RedirectTemplate;
import org.springframework.social.mockbin.impl.resources.StatusTemplate;
import org.springframework.social.mockbin.impl.resources.StreamTemplate;
import org.springframework.social.mockbin.operations.resources.BinOperations;
import org.springframework.social.mockbin.operations.resources.CompressionOperations;
import org.springframework.social.mockbin.operations.resources.CookieOperations;
import org.springframework.social.mockbin.operations.resources.DebuggingOperations;
import org.springframework.social.mockbin.operations.resources.DelayOperations;
import org.springframework.social.mockbin.operations.resources.HeaderOperations;
import org.springframework.social.mockbin.operations.resources.IpOperations;
import org.springframework.social.mockbin.operations.resources.RedirectOperations;
import org.springframework.social.mockbin.operations.resources.StatusOperations;
import org.springframework.social.mockbin.operations.resources.StreamOperations;
import org.springframework.social.mockbin.settings.MockbinClientSettings;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.joda.JodaMapper;

/**
 * Implementation of {@link Mockbin} {@link BaseApi} interface
 * 
 * <ul>
 * 	<li>Default constructor initializes {@link MockbinTemplate} to configures it to connect to mockbin.com
 * 	<li>{@link JodaMapper} is used for JSON responses
 * 	<li>Message converter priority is JSON, YAML, XML, and HTML/text
 * </ul> 
 *
 * @author robin
 */
public class MockbinTemplate extends AbstractBaseApiTemplate implements Mockbin {
	
	private BinOperations binOperations;
	private CompressionOperations compressionOperations;
	private CookieOperations cookieOperations;
	private DelayOperations delayOperations;
	private HeaderOperations headerOperations;
	private IpOperations ipOperations;
	private RedirectOperations redirectOperations;
	private StatusOperations statusOperations;
	private StreamOperations streamOperations;
	private DebuggingOperations debuggingOperations;
	
	public MockbinTemplate() {
		super(new MockbinClientSettings());
	}

	public MockbinTemplate(MockbinClientSettings settings) {
		super(settings);
	}
	
	@Override
	public MockbinClientSettings getSettings() {
		return (MockbinClientSettings) super.getSettings();
	}
	
	@Override
	protected ObjectMapper createObjectMapper() {
		JodaMapper mapper = new JodaMapper();
		mapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.enable(INDENT_OUTPUT);
		mapper.enable(ORDER_MAP_ENTRIES_BY_KEYS);
		mapper.enable(SORT_PROPERTIES_ALPHABETICALLY);
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.setTimeZone(getSettings().getDefaultTimeZone().toTimeZone());
		mapper.setWriteDatesAsTimestamps(false);
		
		return mapper;
	}
	
	@Override
	protected void associateObjectMapperWithRestTemplate() {
		// JSON first
		super.associateObjectMapperWithRestTemplate();
		
		List<HttpMessageConverter<?>> messageConverters = getRestTemplate().getMessageConverters();
		List<HttpMessageConverter<?>> newMessageConverters = new ArrayList<>();
		for (HttpMessageConverter<?> converter : messageConverters) {
			if (converter.getClass().isAssignableFrom(JavaBeanSnakeYamlHttpMessageConverter.class)) {
				// delay adding until after loop
			} else if (converter.getClass().isAssignableFrom(MappingJackson2XmlHttpMessageConverter.class)) {
				// delay adding until after loop
			} else if (converter.getClass().isAssignableFrom(StringHttpMessageConverter.class)) {
				// delay adding until after loop
			} else {
				newMessageConverters.add(converter);
			}
		}
		
		// then YAML
		JavaBeanSnakeYamlHttpMessageConverter yamlMessageConverter = new JavaBeanSnakeYamlHttpMessageConverter();
		newMessageConverters.add(yamlMessageConverter);
		
		// then XML, JodaMapper is not compatible so a new XmlMapper is created
		XmlMapper xmlObjectMapper = new XmlMapper();
		xmlObjectMapper.enable(INDENT_OUTPUT);
		xmlObjectMapper.enable(ORDER_MAP_ENTRIES_BY_KEYS);
		xmlObjectMapper.enable(SORT_PROPERTIES_ALPHABETICALLY);
		xmlObjectMapper.setSerializationInclusion(Include.NON_NULL);
		xmlObjectMapper.setTimeZone(getSettings().getDefaultTimeZone().toTimeZone());
		
		MappingJackson2XmlHttpMessageConverter jacksonXmlMessageConverter = new MappingJackson2XmlHttpMessageConverter();
		jacksonXmlMessageConverter.setObjectMapper(xmlObjectMapper);
		newMessageConverters.add(jacksonXmlMessageConverter);
		
		// finally HTML/text
		newMessageConverters.add(new StringHttpMessageConverter(UTF_8));
		
		getRestTemplate().setMessageConverters(newMessageConverters);
	}

	/* (non-Javadoc)
	 * @see org.springframework.social.ApiBinding#isAuthorized()
	 */
	@Override
	public boolean isAuthorized() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.springframework.social.impl.AbstractBaseApiTemplate#initializeResourceOperations()
	 */
	@Override
	protected void initializeResourceOperations() {
		// Bin
		binOperations 			= new BinTemplate(getSettings(), getRestTemplate());
		
		// Utility
		debuggingOperations 	= new DebuggingTemplate(getSettings(), getRestTemplate());
		ipOperations 			= new IpTemplate(getSettings(), getRestTemplate());
		statusOperations 		= new StatusTemplate(getSettings(), getRestTemplate());
		headerOperations 		= new HeaderTemplate(getSettings(), getRestTemplate());
		cookieOperations 		= new CookieTemplate(getSettings(), getRestTemplate());
		redirectOperations 		= new RedirectTemplate(getSettings(), getRestTemplate());
		streamOperations 		= new StreamTemplate(getSettings(), getRestTemplate());
		delayOperations 		= new DelayTemplate(getSettings(), getRestTemplate());
		compressionOperations 	= new CompressionTemplate(getSettings(), getRestTemplate());
	}

	/* (non-Javadoc)
	 * @see org.springframework.social.mockbin.Mockbin#binOperations()
	 */
	@Override
	public BinOperations binOperations() {
		return binOperations;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.social.mockbin.Mockbin#debuggingOperations()
	 */
	@Override
	public DebuggingOperations debuggingOperations() {
		return debuggingOperations;
	}

	/* (non-Javadoc)
	 * @see org.springframework.social.mockbin.Mockbin#ipOperations()
	 */
	@Override
	public IpOperations ipOperations() {
		return ipOperations;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.social.mockbin.Mockbin#statusOperations()
	 */
	@Override
	public StatusOperations statusOperations() {
		return statusOperations;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.social.mockbin.Mockbin#headerOperations()
	 */
	@Override
	public HeaderOperations headerOperations() {
		return headerOperations;
	}

	/* (non-Javadoc)
	 * @see org.springframework.social.mockbin.Mockbin#cookieOperations()
	 */
	@Override
	public CookieOperations cookieOperations() {
		return cookieOperations;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.social.mockbin.Mockbin#redirectOperations()
	 */
	@Override
	public RedirectOperations redirectOperations() {
		return redirectOperations;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.social.mockbin.Mockbin#streamOperations()
	 */
	@Override
	public StreamOperations streamOperations() {
		return streamOperations;
	}

	/* (non-Javadoc)
	 * @see org.springframework.social.mockbin.Mockbin#delayOperations()
	 */
	@Override
	public DelayOperations delayOperations() {
		return delayOperations;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.social.mockbin.Mockbin#compressionOperations()
	 */
	@Override
	public CompressionOperations compressionOperations() {
		return compressionOperations;
	}

	@Override
	protected void addInterceptorsToRestTemplate() { }

}
