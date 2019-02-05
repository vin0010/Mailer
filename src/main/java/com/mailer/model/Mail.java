package com.mailer.model;

import java.net.URI;

public class Mail {
	private String from;
	private String to;
	private String subject;
	private String body;
	private URI uri;
	/**
	 * @return the from
	 */
	public String getFrom() {
		return from;
	}
	/**
	 * @param from the from to set
	 */
	public void setFrom(String from) {
		this.from = from;
	}
	/**
	 * @return the to
	 */
	public String getTo() {
		return to;
	}
	/**
	 * @param to the to to set
	 */
	public void setTo(String to) {
		this.to = to;
	}
	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/**
	 * @return the body
	 */
	public String getBody() {
		return body;
	}
	/**
	 * @param body the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}
	/**
	 * @return the uri
	 */
	public URI getUri() {
		return uri;
	}
	/**
	 * @param uri the uri to set
	 */
	public void setUri(URI uri) {
		this.uri = uri;
	}
	@Override
	public String toString() {
		return "{"
				+ "\"from\":" + this.getFrom() + "\""
				+"\"to\";" + this.getTo() + "\""
				+ "\"subject\":" + this.getSubject() + "\""
				+ "\"body\":" + this.getBody() + "\""
				+ "\"uri\":" + this.getUri() + "\""
				+ "}";
	}
}
