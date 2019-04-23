package io.helidon.apigateway.oci.facade;

import io.helidon.apigateway.oci.entity.FuncBody;
import io.helidon.apigateway.oci.entity.OciHttpHeaders;

public interface OciFacade {

	OciHttpHeaders getHttpHeaders(FuncBody funcBody);
}
