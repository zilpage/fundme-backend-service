package com.skillink.fundme.dto;

public class EmailDetails {

	private long id;
    private String msgSender;
    private String toAddress;
    private String msgSubject;
    private String contentType;
    private String msgBody;
    private String cc;
    private String bcc;
    
    public String getMsgSender() {
        return msgSender;
    }
    public void setMsgSender(String msgSender) {
        this.msgSender = msgSender;
    }
    public String getToAddress() {
        return toAddress;
    }
    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }
    public String getMsgSubject() {
        return msgSubject;
    }
    public void setMsgSubject(String msgSubject) {
        this.msgSubject = msgSubject;
    }
    public String getContentType() {
        return contentType;
    }
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
   
    public String getMsgBody() {
        return msgBody;
    }
    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody;
    }
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCc() {
		return cc;
	}
	public void setCc(String cc) {
		this.cc = cc;
	}
	public String getBcc() {
		return bcc;
	}
	public void setBcc(String bcc) {
		this.bcc = bcc;
	}
	

    
}
