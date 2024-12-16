package com.app;

import com.app.model.entity.Rol;
import com.app.model.entity.Usuario;
import com.app.repository.RolRepository;
import com.app.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

@SpringBootApplication
public class SpringsecurityJoinfacesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringsecurityJoinfacesApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(UsuarioRepository usrRepo, RolRepository rolRepo, PasswordEncoder passwordEncoder) {
		return args -> {

			// [.] -> Crear usuario administrador de prueba
			if(usrRepo.findByUsername("admin").isEmpty()){
				List<Rol> roles = (List<Rol>) rolRepo.findAll();
				Set<Rol> rolesArray = new HashSet<>();
				for(Rol rol: roles) {
					rolesArray.add(rol);
				}
				var admin = Usuario.builder()
						.username("admin")
						.estado(Boolean.TRUE)
						.password(passwordEncoder.encode("root2024"))
						.roles(rolesArray)
						.build();
				System.out.println("Usuario creado: " + usrRepo.save(admin).getUsername());
			}

			// [.] -> Crear usuario de prueba
			if(usrRepo.findByUsername("test").isEmpty()){
				Rol rol = rolRepo.findByNombre("ROLE_USER").get();
				Set<Rol> rolesArray = new HashSet<>();
				rolesArray.add(rol);

				var admin = Usuario.builder()
						.username("test")
						.estado(Boolean.TRUE)
						.password(passwordEncoder.encode("test2025"))
						.roles(rolesArray)
						.build();
				System.out.println("Usuario creado: " + usrRepo.save(admin).getUsername());
			}

		};
	}

}
