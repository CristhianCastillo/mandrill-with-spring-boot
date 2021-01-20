package com.ptesa.demoemailmandril.controller;

import com.ptesa.demoemailmandril.service.EmailClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    @Autowired
    private EmailClientService emailClientService;

    @PostMapping("/send/email/lutung/test")
    public Object sendEmailLutung() {
        try {
            return emailClientService.sendEmailLutung();
        } catch (Exception ex) {
            ex.printStackTrace();
            final String s = ex.getMessage();
            return s;
        }
    }

    @PostMapping("/send/email/cribbstechnologies/test")
    public Object sendEmailCribbstechnologies() {
        try {
            return emailClientService.sendEmailCribbstechnologies();
        } catch (Exception ex) {
            ex.printStackTrace();
            final String s = ex.getMessage();
            return s;
        }
    }
}
