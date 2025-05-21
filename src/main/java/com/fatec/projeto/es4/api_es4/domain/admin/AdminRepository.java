package com.fatec.projeto.es4.api_es4.domain.admin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fatec.projeto.es4.api_es4.entities.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    public Optional<Admin> findByUsuario(String usuario);
}
