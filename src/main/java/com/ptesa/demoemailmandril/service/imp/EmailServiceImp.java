package com.ptesa.demoemailmandril.service.imp;

import com.microtripit.mandrillapp.lutung.MandrillApi;
import com.microtripit.mandrillapp.lutung.model.MandrillApiError;
import com.microtripit.mandrillapp.lutung.view.MandrillMessage;
import com.microtripit.mandrillapp.lutung.view.MandrillMessageStatus;
import com.ptesa.demoemailmandril.exception.EmailException;
import com.ptesa.demoemailmandril.model.EmailMessage;
import com.ptesa.demoemailmandril.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

@Component
public class EmailServiceImp implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImp.class);

    @Autowired
    private MandrillApi mandrillApi;

    @Override
    @Retryable(maxAttempts = 3, value = {MandrillApiError.class}, backoff = @Backoff(value = 60000, multiplier = 2))
    public MandrillMessageStatus[] sendEmail(EmailMessage emailMessage) throws EmailException {
        MandrillMessage message = new MandrillMessage();
        message.setSubject(emailMessage.getSubject());
//        message.setText(emailMessage.getBody());
//        message.setAutoText(true);
        message.setHtml(emailMessage.getBody());
        message.setAutoHtml(true);
        message.setFromEmail(emailMessage.getFromEmail());
        message.setFromName(emailMessage.getFromName());

        ArrayList<MandrillMessage.Recipient> recipients = new ArrayList<>();
        for (String email : emailMessage.getTo()) {
            MandrillMessage.Recipient recipient = new MandrillMessage.Recipient();
            recipient.setEmail(email);
            //recipient.setName("optional name");
            recipient.setType(MandrillMessage.Recipient.Type.TO);
            recipients.add(recipient);
        }

        for (String email : emailMessage.getCc()) {
            MandrillMessage.Recipient recipient = new MandrillMessage.Recipient();
            recipient.setEmail(email);
            recipient.setType(MandrillMessage.Recipient.Type.CC);
            recipients.add(recipient);
        }
        message.setTo(recipients);
        message.setPreserveRecipients(true);

        if (emailMessage.getAttachmentContent() != null) {
            MandrillMessage.MessageContent messageContent = new MandrillMessage.MessageContent();
            messageContent.setContent(emailMessage.getAttachmentContent());
            messageContent.setBinary(true);
            messageContent.setName(emailMessage.getAttachmentName());
            message.setAttachments(Collections.singletonList(messageContent));
        }

        try {
            logger.info("Sending email to - {} with subject {}", emailMessage.getTo(), emailMessage.getSubject());
            MandrillMessageStatus[] messageStatusReports = mandrillApi.messages().send(message, false);
            for (MandrillMessageStatus messageStatusReport : messageStatusReports) {
                final String status = messageStatusReport.getStatus();
                logger.info("MessageStatusReports = " + status);
                if (status.equalsIgnoreCase("rejected") || status.equalsIgnoreCase("invalid")) {
                    logger.error("Could not send email to {} status {}", emailMessage.getTo(), status);
                }
            }
            return messageStatusReports;
        } catch (MandrillApiError mandrillApiError) {
            logger.error("MandrillApiError: " + mandrillApiError.getMandrillErrorAsJson());
            logger.error("MandrillApiError sending email - " + emailMessage.getTo(), mandrillApiError);
            throw new EmailException("MandrillApiError sending email - " + emailMessage.getTo(), mandrillApiError);
        } catch (IOException e) {
            logger.error("IOException sending email - " + emailMessage.getTo(), e);
            throw new EmailException("IOException sending email - " + emailMessage.getTo(), e);
        }
    }

    @Override
    @Retryable(maxAttempts = 3, value = {MandrillApiError.class}, backoff = @Backoff(value = 5000))
    public MandrillMessageStatus[] sendEmailLutung(EmailMessage emailMessage) throws MandrillApiError, EmailException {
        MandrillMessage message = new MandrillMessage();
        message.setSubject(emailMessage.getSubject());
//        message.setText(emailMessage.getBody());
//        message.setAutoText(true);
        message.setHtml(emailMessage.getBody());
        message.setAutoHtml(true);
        message.setFromEmail(emailMessage.getFromEmail());
        message.setFromName(emailMessage.getFromName());

        ArrayList<MandrillMessage.Recipient> recipients = new ArrayList<>();
        for (String email : emailMessage.getTo()) {
            MandrillMessage.Recipient recipient = new MandrillMessage.Recipient();
            recipient.setEmail(email);
            //recipient.setName("optional name");
            recipient.setType(MandrillMessage.Recipient.Type.TO);
            recipients.add(recipient);
        }

        for (String email : emailMessage.getCc()) {
            MandrillMessage.Recipient recipient = new MandrillMessage.Recipient();
            recipient.setEmail(email);
            recipient.setType(MandrillMessage.Recipient.Type.CC);
            recipients.add(recipient);
        }
        message.setTo(recipients);
        message.setPreserveRecipients(true);
        if (emailMessage.getAttachmentContent() != null) {
            MandrillMessage.MessageContent messageContent = new MandrillMessage.MessageContent();
            messageContent.setContent(emailMessage.getAttachmentContent());
            messageContent.setBinary(true);
            messageContent.setName(emailMessage.getAttachmentName());
            message.setAttachments(Collections.singletonList(messageContent));
        }
        logger.info("Sending email to - {} with subject {}", emailMessage.getTo(), emailMessage.getSubject());
        MandrillMessageStatus[] messageStatusReports;
        try {
            messageStatusReports = mandrillApi.messages().send(message, false);
        } catch (IOException e) {
            logger.error("IOException sending email - " + emailMessage.getTo(), e);
            throw new EmailException("IOException sending email - " + emailMessage.getTo(), e);
        }

        boolean emailCorrectSend = false;
        for (MandrillMessageStatus messageStatusReport : messageStatusReports) {
            final String status = messageStatusReport.getStatus();
            logger.info("MessageStatusReports = " + status);
            if (status.equalsIgnoreCase("rejected") || status.equalsIgnoreCase("invalid")) {
                logger.error("Could not send email to {} status {}", emailMessage.getTo(), status);
                emailCorrectSend = false;
                break;
            }
            emailCorrectSend = true;
        }
        if (!emailCorrectSend) {
            throw new MandrillApiError("No fue posible realizar el envio...");
        } else {
            return messageStatusReports;
        }
    }

    @Recover
    public MandrillMessageStatus[] sendEmailLutungRecovery(MandrillApiError mandrillApiError, EmailMessage emailMessage) {
        logger.error("MandrillApiError: " + mandrillApiError.getMandrillErrorAsJson());
//        logger.error("MandrillApiError sending email - " + emailMessage.getTo(), mandrillApiError);
        logger.info(String.format("Retry Recovery - %s", mandrillApiError.getMessage()));
        return null;
    }
}
