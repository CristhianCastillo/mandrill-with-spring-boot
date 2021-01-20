package com.ptesa.demoemailmandril.service.imp;

import com.cribbstechnologies.clients.mandrill.exception.RequestFailedException;
import com.cribbstechnologies.clients.mandrill.model.BaseMandrillRequest;
import com.cribbstechnologies.clients.mandrill.model.MandrillAttachment;
import com.cribbstechnologies.clients.mandrill.model.MandrillHtmlMessage;
import com.cribbstechnologies.clients.mandrill.model.MandrillMessageInfoRequest;
import com.cribbstechnologies.clients.mandrill.model.MandrillMessageRequest;
import com.cribbstechnologies.clients.mandrill.model.MandrillRecipient;
import com.cribbstechnologies.clients.mandrill.model.response.message.SendMessageResponse;
import com.cribbstechnologies.clients.mandrill.request.MandrillUsersRequest;
import com.microtripit.mandrillapp.lutung.model.MandrillApiError;
import com.microtripit.mandrillapp.lutung.view.MandrillMessage;
import com.microtripit.mandrillapp.lutung.view.MandrillMessageStatus;
import com.ptesa.demoemailmandril.exception.EmailException;
import com.ptesa.demoemailmandril.model.EmailMessage;
import com.ptesa.demoemailmandril.service.EmailClientService;
import com.ptesa.demoemailmandril.service.EmailService;
import com.ptesa.demoemailmandril.service.MandrillHelpers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmailClientServiceImp implements EmailClientService {

    private final Logger logger = LoggerFactory.getLogger(EmailClientServiceImp.class);

    @Autowired
    private EmailService emailService;

    @Override
    public MandrillMessageStatus[] sendEmailLutung() throws EmailException, MandrillApiError {
        EmailMessage message = new EmailMessage();
        message.setFromEmail("cristhian.castillo@ptesa.com");
        message.setFromName("Cristhian Castillo");
        List<String> to = new ArrayList<>();
        to.add("cece9707@gmail.com");
//        to.add("cristhian-castillo@upc.edu.co");
        message.setTo(to);
        List<String> cc = new ArrayList<>();
//        cc.add("juan-castillo1@upc.edu.co");
        message.setCc(cc);
        message.setSubject("Subject De Pruebas");
        message.setBody("<html><body>Hola! buen dia.</body></html>");
        return emailService.sendEmailLutung(message);
    }

    @Override
    public SendMessageResponse sendEmailCribbstechnologies() {
        BaseMandrillRequest baseMandrillRequest =  new BaseMandrillRequest();
        MandrillHelpers mandrillHelpers = new MandrillHelpers();
        MandrillMessageRequest d = new MandrillMessageRequest();
        List<MandrillAttachment> attachments = new ArrayList<>();
//        String fileName = "file.xlsx";
        MandrillRecipient[] recipients = {
                new MandrillRecipient(
                        "Eduardo Erazo",
                        "cece9707@gmail.com"
                ),
                new MandrillRecipient(
                        "Castillo Erazo",
                        "cristhian-castillo@upc.edu.co"
                )
        };
//        try {
//            attachments.add(new MandrillAttachment("application/vnd.ms-excel", fileName, FileUtils.encodeFileToBase64String(fileName)));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        try {
            MandrillHtmlMessage message = new MandrillHtmlMessage();
            return mandrillHelpers.sendMessage(
                    "Email 2 Option",
                    recipients,
                    "Cristhian Castillo",
                    "cristhian.castillo@ptesa.com",
                    "<html><body>Hola! buen dia.</body></html>", attachments
            );
        } catch (RequestFailedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
