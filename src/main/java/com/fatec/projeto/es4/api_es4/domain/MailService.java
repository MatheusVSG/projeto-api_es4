package com.fatec.projeto.es4.api_es4.domain;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.fatec.projeto.es4.api_es4.entities.MailStructure;

@Service
public class MailService {
    
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromMail;

    public void senMail(String mail, MailStructure mailStructure) throws MessagingException{
        /*SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromMail);
        simpleMailMessage.setSubject(mailStructure.getSubject());
        simpleMailMessage.setText(mailStructure.getMessage());
        simpleMailMessage.setTo(mail);
        
        mailSender.send(simpleMailMessage);*/

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        String htmlMessage = """
            <html>
                <body >
                    <h2>%s</h2>
                    <p>%s</p>
                    <p><b>Equipe Chame Serviços</b></p>
                </body>
            </html>
            """.formatted(mailStructure.getSubject(), mailStructure.getMessage());

        helper.setFrom(fromMail);
        helper.setTo(mail);
        helper.setSubject(mailStructure.getSubject());
        helper.setText(htmlMessage, true); // true = HTML

        mailSender.send(mimeMessage);
    }
}
