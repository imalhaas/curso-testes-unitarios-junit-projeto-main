package com.algaworks.junit.blog.negocio;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

public class GerenciadorEnvioEmail {

    void enviarEmail(Mensagem mensagem) {
        try {
            Email email = new SimpleEmail();
            email.setHostName("lucas@gmail.com");
            email.setSmtpPort(465);
            email.setAuthenticator(new DefaultAuthenticator("Lucas.algaworks", ""));
            email.setSSLOnConnect(true);
            email.setFrom("lucas@gmail.com");
            email.setSubject(mensagem.getAssunto());
            email.setMsg(mensagem.getConteudo());
            email.addTo(mensagem.getAssunto());
            email.send();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
