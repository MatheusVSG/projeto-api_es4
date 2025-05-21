package com.fatec.projeto.es4.api_es4.domain.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.fatec.projeto.es4.api_es4.entities.Admin;

@Repository
public interface AuthRepository extends JpaRepository<Admin, Long> {
    Admin findByUsuario(String usuario);
}
