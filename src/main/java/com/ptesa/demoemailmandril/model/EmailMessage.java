package com.ptesa.demoemailmandril.model;

import java.util.ArrayList;
import java.util.List;

public class EmailMessage {

    private String fromEmail;
    private String fromName;

    private List<String> to = new ArrayList<>();
    private List<String> cc = new ArrayList<>();

    private String subject;
    private String body;

    private String attachmentName;
    private String attachmentContent;

    public EmailMessage() {
    }

    public EmailMessage(String fromEmail, String fromName, List<String> to, List<String> cc, String subject, String body, String attachmentName, String attachmentContent) {
        this.fromEmail = fromEmail;
        this.fromName = fromName;
        this.to = to;
        this.cc = cc;
        this.subject = subject;
        this.body = body;
        this.attachmentName = attachmentName;
        this.attachmentContent = attachmentContent;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public List<String> getTo() {
        return to;
    }

    public void setTo(List<String> to) {
        this.to = to;
    }

    public List<String> getCc() {
        return cc;
    }

    public void setCc(List<String> cc) {
        this.cc = cc;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    public String getAttachmentContent() {
        return attachmentContent;
    }

    public void setAttachmentContent(String attachmentContent) {
        this.attachmentContent = attachmentContent;
    }
}