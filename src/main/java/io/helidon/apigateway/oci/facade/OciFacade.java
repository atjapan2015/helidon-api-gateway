package io.helidon.apigateway.oci.facade;

import javax.json.JsonObject;

import io.helidon.apigateway.oci.entity.OciHttpHeaders;

public interface OciFacade {

	OciHttpHeaders getHttpHeaders(String method, String uri, JsonObject jsonObject);

	OciHttpHeaders getHttpHeaders(String method, String uri, String jsonObject);

}
