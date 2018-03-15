package com.lemelo.servicos;

import com.lemelo.entidades.Usuario;

public interface EmailService {
    public void notificarAtraso(Usuario usuario);
}
