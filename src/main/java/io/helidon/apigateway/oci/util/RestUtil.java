package io.helidon.apigateway.oci.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;

import javax.json.JsonObject;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.tomitribe.auth.signatures.PEM;

import io.helidon.apigateway.oci.util.Signing.RequestSigner;

public class RestUtil {

	private static RestUtil restUtil = new RestUtil();

	public static RestUtil getInstance() {

		return restUtil;
	}

	public String[] RestHeaders(String apiKey, String privateKeyFilename, String method, String uri,
			JsonObject jsonObject) {

		String[] httpHeaders = new String[] { "", "", "", "", "" };

		HttpRequestBase request;

		// This is the keyId for a key uploaded through the console
		PrivateKey privateKey = loadPrivateKey(privateKeyFilename);
		RequestSigner signer = new RequestSigner(apiKey, privateKey);

		if (method.equals("get")) {
			request = new HttpGet(uri);
		} else if (method.equals("post")) {
			request = new HttpPost(uri);
			setentity(request, jsonObject);
		} else if (method.equals("put")) {
			request = new HttpPut(uri);
			setentity(request, jsonObject);
		} else if (method.equals("patch")) {
			request = new HttpPatch(uri);
			setentity(request, jsonObject);
		} else if (method.equals("delete")) {
			request = new HttpDelete(uri);
		} else {
			request = new HttpGet(uri);
		}

		signer.signRequest(request);

		httpHeaders[0] = request.getFirstHeader("x-date").getValue();
		httpHeaders[1] = request.getFirstHeader("Authorization").getValue();
		if (method.equals("post") || method.equals("put") || method.equals("patch")) {
			httpHeaders[2] = request.getFirstHeader("x-content-sha256").getValue();
			httpHeaders[3] = request.getFirstHeader("content-type").getValue();
			httpHeaders[4] = request.getFirstHeader("content-length").getValue();
		}

		return httpHeaders;
	}

	public String[] RestHeaders(String apiKey, String privateKeyFilename, String method, String uri,
			String jsonObject) {

		String[] httpHeaders = new String[] { "", "", "", "", "" };

		HttpRequestBase request;

		// This is the keyId for a key uploaded through the console
		PrivateKey privateKey = loadPrivateKey(privateKeyFilename);
		RequestSigner signer = new RequestSigner(apiKey, privateKey);

		if (method.equals("get")) {
			request = new HttpGet(uri);
		} else if (method.equals("post")) {
			request = new HttpPost(uri);
			setentity(request, jsonObject);
		} else if (method.equals("put")) {
			request = new HttpPut(uri);
			setentity(request, jsonObject);
		} else if (method.equals("patch")) {
			request = new HttpPatch(uri);
			setentity(request, jsonObject);
		} else if (method.equals("delete")) {
			request = new HttpDelete(uri);
		} else {
			request = new HttpGet(uri);
		}

		signer.signRequest(request);

		httpHeaders[0] = request.getFirstHeader("x-date").getValue();
		httpHeaders[1] = request.getFirstHeader("Authorization").getValue();
		if (method.equals("post") || method.equals("put") || method.equals("patch")) {
			httpHeaders[2] = request.getFirstHeader("x-content-sha256").getValue();
			httpHeaders[3] = request.getFirstHeader("content-type").getValue();
			httpHeaders[4] = request.getFirstHeader("content-length").getValue();
		}

		return httpHeaders;
	}

	private void setentity(HttpRequestBase request, JsonObject jsonObject) {

		try {
			HttpEntity entity = new StringEntity(jsonObject.toString());
			((HttpPost) request).setEntity(entity);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	private void setentity(HttpRequestBase request, String jsonObject) {

		try {
			HttpEntity entity = new StringEntity(jsonObject.toString());
			((HttpPost) request).setEntity(entity);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
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
