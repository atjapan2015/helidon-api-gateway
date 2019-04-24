package io.helidon.apigateway.oci.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.tomitribe.auth.signatures.PEM;

import io.helidon.apigateway.oci.util.Signing.RequestSigner;

public class RestUtil {

	private static RestUtil restUtil = new RestUtil();

	public static RestUtil getInstance() {

		return restUtil;
	}

	public String[] RestHeaders(String apiKey, String privateKeyFilename, String uri, String method) {

		String[] httpHeaders = new String[] { "", "" };

		HttpRequestBase request;

		// This is the keyId for a key uploaded through the console
		PrivateKey privateKey = loadPrivateKey(privateKeyFilename);
		RequestSigner signer = new RequestSigner(apiKey, privateKey);

		if (method.equals("get")) {
			request = new HttpGet(uri);
		} else if (method.equals("post")) {
			request = new HttpPost(uri);
		} else if (method.equals("put")) {
			request = new HttpPut(uri);
		} else if (method.equals("patch")) {
			request = new HttpPatch(uri);
		} else if (method.equals("delete")) {
			request = new HttpDelete(uri);
		} else {
			request = new HttpGet(uri);
		}

		signer.signRequest(request);

		httpHeaders[0] = request.getFirstHeader("x-date").getValue();
		httpHeaders[1] = request.getFirstHeader("Authorization").getValue();

		return httpHeaders;
	}

	private PrivateKey loadPrivateKey(String privateKeyFilename) {

		try (InputStream privateKeyStream = Files.newInputStream(Paths.get(privateKeyFilename))) {
			return PEM.readPrivateKey(privateKeyStream);
		} catch (InvalidKeySpecException e) {
			throw new RuntimeException("Invalid format for private key");
		} catch (IOException e) {
			throw new RuntimeException("Failed to load private key");
		}
	}
}
