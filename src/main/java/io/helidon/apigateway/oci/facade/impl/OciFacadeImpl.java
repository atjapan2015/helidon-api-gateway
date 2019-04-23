package io.helidon.apigateway.oci.facade.impl;

import io.helidon.apigateway.oci.config.AppConfig;
import io.helidon.apigateway.oci.entity.OciHttpHeaders;
import io.helidon.apigateway.oci.facade.OciFacade;
import io.helidon.apigateway.oci.util.RestUtil;

public class OciFacadeImpl implements OciFacade {

	private static OciFacade ociFacadeImpl = new OciFacadeImpl();

	public static OciFacade getInstance() {

		return ociFacadeImpl;
	}

	private final String apiKey = (AppConfig.TENANCY_OCID + "/" + AppConfig.USER_OCID + "/" + AppConfig.FINGERPRINT);
	private final String privateKeyFilename = AppConfig.PRIVATE_PEM;

	@Override
	public OciHttpHeaders getHttpHeaders(String method) {

		OciHttpHeaders ociHttpHeaders = new OciHttpHeaders();

		String uri = "http://dummy";
		RestUtil restUtil = RestUtil.getInstance();
		String result[] = restUtil.RestHeaders(apiKey, privateKeyFilename, uri, method);

		ociHttpHeaders.setDate(result[0]);
		ociHttpHeaders.setAuthorization(result[1]);

		return ociHttpHeaders;
	}
}
