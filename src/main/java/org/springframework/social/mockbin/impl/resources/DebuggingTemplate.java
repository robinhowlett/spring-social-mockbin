/**
 *
 */
package org.springframework.social.mockbin.impl.resources;

import com.sportslabs.amp.har.dto.alf.har.AlfHar;
import com.sportslabs.amp.har.dto.alf.har.entries.AlfHarEntry;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.social.impl.resources.AbstractBaseApiResourceTemplate;
import org.springframework.social.mockbin.operations.resources.DebuggingOperations;
import org.springframework.social.settings.ClientSettings;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.BaseApiUriComponentsBuilder;

import java.net.URI;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

/**
 * @author robin
 * @see DebuggingOperations
 */
public class DebuggingTemplate extends AbstractBaseApiResourceTemplate<BaseApiUriComponentsBuilder<?>> implements DebuggingOperations {

    public static final String RESOURCES_PATH = "/";
    public static final String RESOURCE_ID_PATH = null;

    /**
     * @param settings
     * @param restTemplate
     */
    public DebuggingTemplate(ClientSettings settings, RestTemplate restTemplate) {
        super(settings, restTemplate);
    }

    /* (non-Javadoc)
     * @see org.springframework.social.impl.resources.AbstractBaseApiResourceTemplate#getResourcePath()
     */
    @Override
    protected String getResourcePath() {
        return RESOURCES_PATH;
    }

    /* (non-Javadoc)
     * @see org.springframework.social.impl.resources.AbstractBaseApiResourceTemplate#getResourceIdPath()
     */
    @Override
    protected String getResourceIdPath() {
        return RESOURCE_ID_PATH;
    }

    /* (non-Javadoc)
     * @see org.springframework.social.impl.resources.AbstractBaseApiResourceTemplate#getResourceClass()
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    protected Class getResourceClass() {
        return AlfHar.class;
    }

    /* (non-Javadoc)
     * @see org.springframework.social.impl.resources.AbstractBaseApiResourceTemplate#getPageClass()
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    protected Class getPageClass() {
        return null;
    }

    @Override
    public ResponseEntity<Object> echo(HttpMethod method, String path, String query, HttpHeaders headers, Object body, Class<?> responseClass) {
        BaseApiUriComponentsBuilder<?> builder = qb().path("/echo");

        if (method == null) {
            method = GET;
        }

        if (path != null && !path.isEmpty()) {
            builder = builder.path(path);
        }

        if (query != null && !query.isEmpty()) {
            builder = builder.query(query);
        }

        URI uri = builder.build().toUri();

        @SuppressWarnings("unchecked")
        ResponseEntity<Object> responseEntity =
                (ResponseEntity<Object>) createRequest(POST, uri, body, headers, responseClass);

        return responseEntity;
    }

    @Override
    public AlfHarEntry request(HttpMethod method, String path, String query, HttpHeaders headers, Object body) {
        BaseApiUriComponentsBuilder<?> builder = qb().path("/request");

        if (method == null) {
            method = GET;
        }

        if (path != null && !path.isEmpty()) {
            builder = builder.path(path);
        }

        if (query != null && !query.isEmpty()) {
            builder = builder.query(query);
        }

        URI uri = builder.build().toUri();

        @SuppressWarnings("unchecked")
        ResponseEntity<AlfHarEntry> responseEntity =
                (ResponseEntity<AlfHarEntry>) createRequest(method, uri, body, headers, AlfHarEntry.class);

        if (responseEntity != null) {
            return responseEntity.getBody();
        }

        // TODO throw exception
        return null;
    }

    @Override
    public AlfHar har(HttpMethod method, String path, String query, HttpHeaders headers, Object body) {
        BaseApiUriComponentsBuilder<?> builder = qb().path("/har");

        if (method == null) {
            method = GET;
        }

        if (path != null && !path.isEmpty()) {
            builder = builder.path(path);
        }

        if (query != null && !query.isEmpty()) {
            builder = builder.query(query);
        }

        URI uri = builder.build().toUri();

        @SuppressWarnings("unchecked")
        ResponseEntity<AlfHar> responseEntity =
                (ResponseEntity<AlfHar>) createRequest(method, uri, body, headers, AlfHar.class);

        if (responseEntity != null) {
            return responseEntity.getBody();
        }

        // TODO throw exception
        return null;
    }

}
