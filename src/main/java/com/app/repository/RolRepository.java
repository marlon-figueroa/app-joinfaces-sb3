package com.app.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.app.model.entity.Rol;

public interface RolRepository extends CrudRepository<Rol, Long> {

    Optional<Rol> findByNombre(String nombre);

}
