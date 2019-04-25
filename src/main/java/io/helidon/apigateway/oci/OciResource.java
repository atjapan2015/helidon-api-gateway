/*
 * Copyright (c) 2018, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.helidon.apigateway.oci;

import java.util.Collections;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.helidon.apigateway.oci.config.AppConfig;
import io.helidon.apigateway.oci.entity.OciHttpHeaders;
import io.helidon.apigateway.oci.facade.OciFacade;
import io.helidon.apigateway.oci.facade.impl.OciFacadeImpl;

/**
 * A simple JAX-RS resource to greet you. Examples:
 *
 * Get default greeting message: curl -X GET http://localhost:8080/greet
 *
 * Get greeting message for Joe: curl -X GET http://localhost:8080/greet/Joe
 *
 * Change greeting curl -X PUT -H "Content-Type: application/json" -d
 * '{"greeting" : "Howdy"}' http://localhost:8080/greet/greeting
 *
 * The message is returned as a JSON object.
 */
@Path("/oci")
@RequestScoped
public class OciResource {

	private static final JsonBuilderFactory JSON = Json.createBuilderFactory(Collections.emptyMap());

	/**
	 * The greeting message provider.
	 */
	private final OciProvider greetingProvider;

	/**
	 * Using constructor injection to get a configuration property. By default this
	 * gets the value from META-INF/microprofile-config
	 *
	 * @param greetingConfig the configured greeting message
	 */
	@Inject
	public OciResource(OciProvider greetingConfig, @ConfigProperty(name = "tenancy.ocid") final String TENANCY_OCID,
			@ConfigProperty(name = "user.ocid") final String USER_OCID,
			@ConfigProperty(name = "fingerprint") final String FINGERPRINT,
			@ConfigProperty(name = "private.pem") final String PRIVATE_PEM,
			@ConfigProperty(name = "default.compartment.id") final String DEFAULT_COMPARTMENT_ID) {

		this.greetingProvider = greetingConfig;

		if (AppConfig.TENANCY_OCID == null) {
			AppConfig.TENANCY_OCID = TENANCY_OCID;
		}

		if (AppConfig.USER_OCID == null) {
			AppConfig.USER_OCID = USER_OCID;
		}

		if (AppConfig.FINGERPRINT == null) {
			AppConfig.FINGERPRINT = FINGERPRINT;
		}

		if (AppConfig.PRIVATE_PEM == null) {
			AppConfig.PRIVATE_PEM = PRIVATE_PEM;
		}

		if (AppConfig.DEFAULT_COMPARTMENT_ID == null) {
			AppConfig.DEFAULT_COMPARTMENT_ID = DEFAULT_COMPARTMENT_ID;
		}
	}

	@SuppressWarnings({ "checkstyle:designforextension", "static-access" })
	@Path("/headers")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public OciHttpHeaders getHttpPostHeaders(@HeaderParam("oci-method") String method,
			@HeaderParam("oci-uri") String uri, JsonObject jsonObject) {

		OciFacade ociFacade = OciFacadeImpl.getInstance();
		return ociFacade.getHttpHeaders(method, uri, jsonObject);
	}

	/*
	 * This does NOT work
	 */
	@SuppressWarnings({ "checkstyle:designforextension", "static-access" })
	@Path("/headers/string")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public OciHttpHeaders getHttpPostHeaders(@HeaderParam("oci-method") String method,
			@HeaderParam("oci-uri") String uri, @HeaderParam("oci-content-length") String ocicontentlength,
			String jsonObject) {

		OciFacade ociFacade = OciFacadeImpl.getInstance();
		OciHttpHeaders OciHttpHeaders = ociFacade.getHttpHeaders(method, uri, jsonObject);
		OciHttpHeaders.setOcicontentlength(ocicontentlength);
		return OciHttpHeaders;
	}

}
