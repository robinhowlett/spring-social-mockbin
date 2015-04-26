/**
 * 
 */
package org.springframework.http.converter.yaml.snake;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;
import org.yaml.snakeyaml.Yaml;

/**
 * JavaBean Snake Yaml Approach
 * 
 * @author robin
 * @see <a href="http://snakeyaml.org">SnakeYAML</a>
 * @see <a href="http://www.lordofthejars.com/2011/10/youre-so-sexy-sex-sex-sexy-feel-me-now.html">@alexsotob's blog post that provided the code</a>
 */
public class JavaBeanSnakeYamlHttpMessageConverter extends AbstractSnakeYamlHttpMessageConverter<Object> {
	
	public JavaBeanSnakeYamlHttpMessageConverter() {
		super();
	}

	@Override
	protected Object readFromSource(Class<?> clazz, HttpHeaders headers, Reader source) throws IOException {
		Yaml beanLoader = getSnakeYamlInterface();
		
		return beanLoader.loadAs(source, clazz);
	}

	@Override
	protected void writeToResult(Object t, HttpHeaders headers, Writer result) throws IOException {
		Yaml beanDumpper = getSnakeYamlInterface();
		String yamlMessage = beanDumpper.dumpAsMap(t);
		FileCopyUtils.copy(yamlMessage, result);	
	}

	@Override
	public boolean canRead(Class<?> clazz, MediaType mediaType) {
		return canRead(mediaType);
	}

	@Override
	public boolean canWrite(Class<?> clazz, MediaType mediaType) {
		return canWrite(mediaType);
	}

	/*
	 * Should not be called, since we override canRead/Write(non-Javadoc)
	 * 
	 * @see org.springframework.http.converter.AbstractHttpMessageConverter#supports(java.lang.Class)
	 */
	@Override
	protected boolean supports(Class<?> clazz) {
		throw new UnsupportedOperationException();
	}

}