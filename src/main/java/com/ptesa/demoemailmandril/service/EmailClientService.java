package com.ptesa.demoemailmandril.service;

import com.cribbstechnologies.clients.mandrill.model.response.message.SendMessageResponse;
import com.microtripit.mandrillapp.lutung.model.MandrillApiError;
import com.microtripit.mandrillapp.lutung.view.MandrillMessageStatus;
import com.ptesa.demoemailmandril.exception.EmailException;
import org.springframework.stereotype.Service;

@Service
public interface EmailClientService {

    MandrillMessageStatus[] sendEmailLutung() throws EmailException, MandrillApiError;

    SendMessageResponse sendEmailCribbstechnologies();
}
