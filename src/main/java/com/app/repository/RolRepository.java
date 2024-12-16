package com.app.repository;

import com.app.model.entity.Rol;
import com.app.model.entity.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RolRepository extends CrudRepository<Rol, Long> {

    Optional<Rol> findByNombre(String nombre);

}
