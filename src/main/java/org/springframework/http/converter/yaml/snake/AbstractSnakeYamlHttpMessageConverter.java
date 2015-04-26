/**
 * 
 */
package org.springframework.http.converter.yaml.snake;

import org.springframework.http.converter.yaml.AbstractYamlHttpMessageConverter;
import org.yaml.snakeyaml.Yaml;

/**
 * SnakeYaml Abstract Message Converter
 * 
 * @author robin
 * @see <a href="http://snakeyaml.org">SnakeYAML</a>
 * @see <a href="http://www.lordofthejars.com/2011/10/youre-so-sexy-sex-sex-sexy-feel-me-now.html">@alexsotob's blog post that provided the code</a>
 */
public abstract class AbstractSnakeYamlHttpMessageConverter<T> extends AbstractYamlHttpMessageConverter<T> {

	// SnakeYaml requires one instance for each Thread so ThreadLocal is used.
	private ThreadLocal<Yaml> yamlInterfaces = new ThreadLocal<Yaml>();

	public AbstractSnakeYamlHttpMessageConverter() {
		super();
	}

	protected final Yaml getSnakeYamlInterface() {
		Yaml yaml = yamlInterfaces.get();
		
		if (yaml == null) {
			yaml = new Yaml();
			yamlInterfaces.set(yaml);
		}

		return yaml;
	}

}