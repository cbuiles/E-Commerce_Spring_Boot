package com.curso.ecommerce.controller;

import com.curso.ecommerce.model.Orden;
import com.curso.ecommerce.model.Producto;
import com.curso.ecommerce.model.Usuario;
import com.curso.ecommerce.service.IOrdenService;
import com.curso.ecommerce.service.IUsuarioService;
import com.curso.ecommerce.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IOrdenService ordenesService;

    @GetMapping("")
    public String home(Model model){

        List<Producto> productos = productoService.findAll() ;
        model.addAttribute("productos", productos);

        return "administrador/home";

    }

    @GetMapping("/usuarios")
    public String usuarios(Model modelo){

        List<Usuario> usuarios = usuarioService.findAll();

        modelo.addAttribute("usuarios", usuarios);

        return "administrador/usuarios";
    }

    @GetMapping("/ordenes")
    public String ordenes(Model model){

        List<Orden> ordenes = ordenesService.findAll();

        model.addAttribute("ordenes", ordenes);

        return "administrador/ordenes";
    }
}
