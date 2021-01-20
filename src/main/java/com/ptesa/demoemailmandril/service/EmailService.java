package com.ptesa.demoemailmandril.service;

import com.microtripit.mandrillapp.lutung.model.MandrillApiError;
import com.microtripit.mandrillapp.lutung.view.MandrillMessageStatus;
import com.ptesa.demoemailmandril.exception.EmailException;
import com.ptesa.demoemailmandril.model.EmailMessage;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {

    MandrillMessageStatus[] sendEmail(EmailMessage emailMessage) throws EmailException;

    MandrillMessageStatus[] sendEmailLutung(EmailMessage emailMessage) throws MandrillApiError, EmailException;
}
