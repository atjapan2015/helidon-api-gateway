package io.helidon.apigateway.oci.facade.impl;

import javax.json.JsonObject;

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
	public OciHttpHeaders getHttpHeaders(String method, String uri, JsonObject jsonObject) {

		OciHttpHeaders ociHttpHeaders = new OciHttpHeaders();

		RestUtil restUtil = RestUtil.getInstance();
		String result[] = restUtil.RestHeaders(apiKey, privateKeyFilename, method, uri, jsonObject);

		ociHttpHeaders.setDate(result[0]);
		ociHttpHeaders.setAuthorization(result[1]);
		ociHttpHeaders.setContentsha256(result[2]);
		ociHttpHeaders.setContenttype(result[3]);
		ociHttpHeaders.setContentlength(result[4]);

		return ociHttpHeaders;
	}

	@Override
	public OciHttpHeaders getHttpHeaders(String method, String uri, String jsonObject) {

		OciHttpHeaders ociHttpHeaders = new OciHttpHeaders();

		RestUtil restUtil = RestUtil.getInstance();
		String result[] = restUtil.RestHeaders(apiKey, privateKeyFilename, method, uri, jsonObject);

		ociHttpHeaders.setDate(result[0]);
		ociHttpHeaders.setAuthorization(result[1]);
		ociHttpHeaders.setContentsha256(result[2]);
		ociHttpHeaders.setContenttype(result[3]);
		ociHttpHeaders.setContentlength(result[4]);

		return ociHttpHeaders;
	}
}
