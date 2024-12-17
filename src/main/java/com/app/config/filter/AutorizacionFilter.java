package com.app.config.filter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.app.service.IRutaService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AutorizacionFilter extends OncePerRequestFilter {

	@Autowired
	private IRutaService rutaService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		try {
			log.debug("la URL solicitada");
			String requestURI = request.getRequestURI();
	
			log.debug("Cargar rutas y roles desde la base de datos");
			Map<String, List<String>> mapaRutas = rutaService.getRutaRolMappings();
	
			log.debug("Encontrar roles autorizados para la URL solicitad");
			List<String> authRoles = mapaRutas.get(requestURI);
			if (authRoles != null) {
				log.debug("Verificar si el usuario autenticado tiene uno de los roles permitidos");
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	
				if (authentication != null && authentication.isAuthenticated()) {
					boolean accesoRol = authentication.getAuthorities()
							.stream()
							.anyMatch(grantedAuthority -> authRoles.contains(grantedAuthority.getAuthority()));
					
					log.info("{} TIENE ACCESO A LA RUTA {}", accesoRol ? "SI": "NO", requestURI);
					
					if (!accesoRol) {
						log.debug("Si no tiene permiso, redirigir a la página de acceso denegado");
						request.getRequestDispatcher("/403.xhtml").forward(request, response);
						return;
					}
				}
			}
	
			filterChain.doFilter(request, response);
		} catch(Throwable e ) {
			log.error(e.getMessage());
			log.error(e.getLocalizedMessage());
			log.error(e.getCause().getMessage());
		}
	}

}
