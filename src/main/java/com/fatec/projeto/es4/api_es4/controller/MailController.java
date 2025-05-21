package com.fatec.projeto.es4.api_es4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.projeto.es4.api_es4.domain.MailService;
import com.fatec.projeto.es4.api_es4.entities.MailStructure;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private MailService mailService;

    @PostMapping("/send/{mail}")
    public String senMail(@PathVariable String mail, @RequestBody MailStructure mailStructure) throws MessagingException {
        mailService.senMail(mail, mailStructure);
        return "Sucesso ao enviar e-mail";
    }
}
