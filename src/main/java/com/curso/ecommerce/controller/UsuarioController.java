package com.curso.ecommerce.controller;

import com.curso.ecommerce.model.Usuario;
import com.curso.ecommerce.service.IUsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    private final Logger log = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping("/registro")
    public String create(){

        return "usuario/registro";

    }

    @PostMapping("/save")
    public String save (Usuario usuario){

        log.info("Usuario registro {}", usuario);
        usuario.setTipo("USER");
        usuarioService.save(usuario);

        return "redirect:/";

    }

    @GetMapping("/login")
    public String login(){

        return "usuario/login";

    }

}
