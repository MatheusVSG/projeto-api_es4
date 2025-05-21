package com.fatec.projeto.es4.api_es4.security;

import jakarta.validation.constraints.NotBlank;

public class LoginDto {
    @NotBlank
    private String usuario;

    @NotBlank
    private String senha;

    public @NotBlank String getUsuario() {
        return usuario;
    }

    public @NotBlank String getSenha() {
        return senha;
    }
}