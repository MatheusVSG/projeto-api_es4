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

    public void senMail(String mail, MailStructure mailStructure) throws MessagingException {
        /*
         * SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
         * simpleMailMessage.setFrom(fromMail);
         * simpleMailMessage.setSubject(mailStructure.getSubject());
         * simpleMailMessage.setText(mailStructure.getMessage());
         * simpleMailMessage.setTo(mail);
         * 
         * mailSender.send(simpleMailMessage);
         */

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        String htmlMessage = """
                            <html lang="pt-BR">
                <body style="
                font-family: 'Segoe UI', sans-serif;
                  background-image: url('https://raw.githubusercontent.com/MatheusVSG/Projeto-ES4/refs/heads/main/src/assets/images/plano-de-fundo.png') !important;
                  background-size: cover;
                  background-repeat: no-repeat;
                  background-position: center;
                  margin: 0;
                  padding: 0;
                  font-family: Arial, sans-serif;
                ">

                    <table align="center" cellpadding="0" cellspacing="0" style="width: 100%%; margin-bottom: 40px;">
                        <tr>
                            <td style="text-align: start; padding: 8px; background-color: #198754;">
                                <img src="https://raw.githubusercontent.com/MatheusVSG/Projeto-ES4/refs/heads/main/src/assets/images/logo.png"
                                    alt="Maicosoft" width="64" style="width: 64px; height: auto;">
                            </td>
                        </tr>
                    </table>

                    <table align="center" cellpadding="0" cellspacing="0"
                        style="width: 80%%; margin-bottom: 40px; background-color: rgba(255,255,255,0.9); border-radius: 8px; padding: 20px; box-shadow: 0 0 10px rgba(0,0,0,0.1);">

                        <!-- TÍTULO -->
                        <tr>
                            <td align="center" style="padding-bottom: 20px;">
                                <h2 style="margin: 0; color: #0056b3;;">Novo Cliente Cadastrado</h2>
                            </td>
                        </tr>

                        <!-- INFORMAÇÕES COMERCIAIS -->
                        <tr>
                            <td style="padding-bottom: 24px;">
                                <h3 style="margin-top: 0; color: #0056b3;">Informações Comerciais</h3>
                                <p><strong>CNPJ:</strong> %s</p>
                                <p><strong>Data de Abertura:</strong> %s</p>
                                <p><strong>Nome:</strong> %s</p>
                                <p><strong>Nome Fantasial:</strong> %s</p>
                                <p><strong>Loja:</strong> %s</p>
                                <p><strong>Tipo:</strong> %s</p>
                                <hr>
                            </td>
                        </tr>

                        <!-- CONTATO -->
                        <tr>
                            <td style="padding-bottom: 24px;">
                                <h3 style="margin-top: 0; color: #0056b3;">Contato</h3>
                                <p><strong>Telefone:</strong> (%s) %s</p>
                                <p><strong>E-mail:</strong> %s</p>
                                <p><strong>HomePage:</strong> %s</p>
                                <hr>
                            </td>
                        </tr>

                        <!-- ENDEREÇO -->
                        <tr>
                            <td style="padding-bottom: 24px;">
                                <h3 style="margin-top: 0; color: #0056b3;">Endereço</h3>
                                <p><strong>Endereço:</strong> %s</p>
                                <p><strong>Bairro:</strong> %s</p>
                                <p><strong>Código do Município:</strong> %s</p>
                                <p><strong>Município:</strong> %s</p>
                                <p><strong>Estado:</strong> %s</p>
                                <p><strong>CEP:</strong> %s</p>
                            </td>
                        </tr>

                        <!-- RODAPÉ -->
                        <tr>
                            <td align="center" style="font-size: 14px; font-weight: 700; color: #212529;;">
                                <p>Este é um e-mail automático. Por favor, não responda.</p>
                            </td>
                        </tr>
                    </table>

                    <table align="center" cellpadding="0" cellspacing="0" style="width: 100%%; height: 84px; background-color: #f8f9fa;">
                        <tr style="text-align: center;">
                            <td style="padding: 8px;">
                            </td>

                            <td style="padding: 8px;">
                                <a style="font-weight: 700; text-decoration: none;  color: #198754;"
                                    href="https://github.com/MatheusVSG/Projeto-ES4">
                                    <span>GitHub</span>
                                </a>
                            </td>

                            <td style=" padding: 8px;">
                            </td>
                        </tr>
                    </table>
                </body>

                </html>
                            """
                .formatted(
                        mailStructure.getCliente().getCnpj(),
                        mailStructure.getCliente().getDataAbertura(),
                        mailStructure.getCliente().getNome(),
                        mailStructure.getCliente().getNomeFantasia(),
                        mailStructure.getCliente().getLoja(),
                        mailStructure.getCliente().getTipo(),
                        mailStructure.getCliente().getDdd(),
                        mailStructure.getCliente().getTelefone(),
                        mailStructure.getCliente().getEmail(),
                        mailStructure.getCliente().getHomePage(),
                        mailStructure.getCliente().getLogradouro(),
                        mailStructure.getCliente().getBairro(),
                        mailStructure.getCliente().getCodMunicipio(),
                        mailStructure.getCliente().getMunicipio(),
                        mailStructure.getCliente().getEstado(),
                        mailStructure.getCliente().getCep());

        helper.setFrom(fromMail);
        helper.setTo(mail);
        helper.setSubject(mailStructure.getSubject());
        helper.setText(htmlMessage, true); // true = HTML

        mailSender.send(mimeMessage);
    }
}
