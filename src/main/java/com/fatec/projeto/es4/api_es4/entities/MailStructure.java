package com.fatec.projeto.es4.api_es4.entities;


public class MailStructure {
    private String subject;
    private String message;
    private Cliente cliente;

    public MailStructure(Cliente cliente){
        this.cliente = cliente;
    }

    public Cliente getCliente(){
        return this.cliente;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
}
