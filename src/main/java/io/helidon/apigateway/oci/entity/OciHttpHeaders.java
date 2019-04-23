package io.helidon.apigateway.oci.entity;

public class OciHttpHeaders {

	private String date;
	private String authorization;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getAuthorization() {
		return authorization;
	}

	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}

}
