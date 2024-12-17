package com.app.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.app.model.entity.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);

}
