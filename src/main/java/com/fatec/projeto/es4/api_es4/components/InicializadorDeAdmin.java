package com.fatec.projeto.es4.api_es4.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.fatec.projeto.es4.api_es4.domain.admin.AdminRepository;
import com.fatec.projeto.es4.api_es4.entities.Admin;

import jakarta.annotation.PostConstruct;

@Component
public class InicializadorDeAdmin {
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void criarUsuarioPadrao() {
        if (adminRepository.count() == 0) {
            Admin admin = new Admin("admin", "admin@email.com", passwordEncoder.encode("1234"));
            adminRepository.save(admin);
            System.out.println("Usuário admin criado: nome=admin, senha=1234");
        }
    }
}
