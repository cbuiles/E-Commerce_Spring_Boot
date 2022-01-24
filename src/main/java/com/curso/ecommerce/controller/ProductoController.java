package com.curso.ecommerce.controller;

import com.curso.ecommerce.model.Producto;
import com.curso.ecommerce.model.Usuario;
import com.curso.ecommerce.service.ProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/productos")
public class ProductoController {

//    Para obtener los Logs de nuestro controlador
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProductoService productoService;

    @GetMapping("")
//    El objeto Model envia la informacion del Obj del Backend a la vista
    public String show(Model model){

        model.addAttribute("productos", productoService.findAll());
        return "productos/show";

    }

    @GetMapping("/create")
    public String create(){
        return "productos/create";
    }

    @PostMapping("/save")
    public String save(Producto producto){

//        Pruebas para el LOGGER
        LOGGER.info("Este es el objeto producto {}", producto);

        Usuario u = new Usuario(1, "", "", "", "", "", "", "" );

        producto.setUsuario(u);

        productoService.save(producto);

        return "redirect:/productos";

    }

}
