package io.helidon.apigateway.oci.facade;

import io.helidon.apigateway.oci.entity.OciHttpHeaders;

public interface OciFacade {

	OciHttpHeaders getHttpHeaders(String method);
}
