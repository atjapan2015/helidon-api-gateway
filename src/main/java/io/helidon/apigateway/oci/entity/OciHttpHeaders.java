package io.helidon.apigateway.oci.entity;

public class OciHttpHeaders {

	private String date;
	private String authorization;
	private String contentsha256;
	private String contenttype;
	private String contentlength;
	private String ocicontentlength;

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

	public String getContentsha256() {
		return contentsha256;
	}

	public void setContentsha256(String contentsha256) {
		this.contentsha256 = contentsha256;
	}

	public String getContenttype() {
		return contenttype;
	}

	public void setContenttype(String contenttype) {
		this.contenttype = contenttype;
	}

	public String getContentlength() {
		return contentlength;
	}

	public void setContentlength(String contentlength) {
		this.contentlength = contentlength;
	}

	public String getOcicontentlength() {
		return ocicontentlength;
	}

	public void setOcicontentlength(String ocicontentlength) {
		this.ocicontentlength = ocicontentlength;
	}

}
