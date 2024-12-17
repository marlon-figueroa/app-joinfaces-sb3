package com.app.handler;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Custom404 implements ErrorController {

    //Este  detecta la peticion por defecto en la ruta /error y la reemplazamos
    @GetMapping("/error")
    public String getErrorPath() {
        // Esto mantendrá la URL actual y renderizará la vista 404.xhtml
        return "/404.xhtml";
    }

}
