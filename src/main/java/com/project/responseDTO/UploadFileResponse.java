package com.project.responseDTO;


public class UploadFileResponse {
	private String fileName;
	private String fileDownloadUri;
	private String fileContentType;
	private long fileSize;
	public UploadFileResponse(String fileName, String fileDownloadUri, String fileContentType, long fileSize) {
		super();
		this.fileName = fileName;
		this.fileDownloadUri = fileDownloadUri;
		this.fileContentType = fileContentType;
		this.fileSize = fileSize;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileDownloadUri() {
		return fileDownloadUri;
	}
	public void setFileDownloadUri(String fileDownloadUri) {
		this.fileDownloadUri = fileDownloadUri;
	}
	public String getFileContentType() {
		return fileContentType;
	}
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	public UploadFileResponse() {
		super();
	}
	
	
}

