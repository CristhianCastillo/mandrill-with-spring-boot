package com.ptesa.demoemailmandril.service;

import com.cribbstechnologies.clients.mandrill.exception.RequestFailedException;
import com.cribbstechnologies.clients.mandrill.model.MandrillAttachment;
import com.cribbstechnologies.clients.mandrill.model.MandrillHtmlMessage;
import com.cribbstechnologies.clients.mandrill.model.MandrillMessageRequest;
import com.cribbstechnologies.clients.mandrill.model.MandrillRecipient;
import com.cribbstechnologies.clients.mandrill.model.response.message.SendMessageResponse;
import com.cribbstechnologies.clients.mandrill.request.MandrillMessagesRequest;
import com.cribbstechnologies.clients.mandrill.request.MandrillRESTRequest;
import com.cribbstechnologies.clients.mandrill.util.MandrillConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MandrillHelpers {

    private static final String API_VERSION = "1.0";
    private static final String BASE_URL = "https://mandrillapp.com/api";
    private static final String MANDRILL_API_KEY = "pt5vSDT15Fi6eo8PB5xRDA";

    private static MandrillRESTRequest request = new MandrillRESTRequest();
    private static MandrillConfiguration config = new MandrillConfiguration();
    private static MandrillMessagesRequest messagesRequest = new MandrillMessagesRequest();
    private static HttpClient client = new DefaultHttpClient();
    private static ObjectMapper mapper = new ObjectMapper();

    public MandrillHelpers() {
        config.setApiKey(MANDRILL_API_KEY);
        config.setApiVersion(API_VERSION);
        config.setBaseURL(BASE_URL);
        request.setConfig(config);
        request.setObjectMapper(mapper);
        request.setHttpClient(client);
        messagesRequest.setRequest(request);
    }

    public SendMessageResponse sendMessage(
            String subject,
            MandrillRecipient[] recipients,
            String senderName,
            String senderEmail,
            String content,
            List<MandrillAttachment> attachments
    ) throws RequestFailedException {
        MandrillMessageRequest mmr = new MandrillMessageRequest();
        MandrillHtmlMessage message = new MandrillHtmlMessage();

        Map<String, String> headers = new HashMap<>();
        message.setFrom_email(senderEmail);
        message.setFrom_name(senderName);
        message.setHeaders(headers);

        message.setHtml(content);
        message.setSubject(subject);
        message.setAttachments(attachments);
        message.setTo(recipients);
        message.setTrack_clicks(true);
        message.setTrack_opens(true);
        mmr.setMessage(message);

        return messagesRequest.sendMessage(mmr);
    }
}
