package org.pawan.messenger.resource.bean;

import javax.ws.rs.QueryParam;

public class MessageFilterBean {

	private @QueryParam("author") String author;
	private @QueryParam("offset") int offset;
	private @QueryParam("size") int size;
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
}
