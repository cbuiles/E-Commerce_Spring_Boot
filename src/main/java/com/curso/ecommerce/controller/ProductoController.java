package com.curso.ecommerce.controller;

import com.curso.ecommerce.model.Producto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/productos")
public class ProductoController {

//    Para obtener los Logs de nuestro controlador
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @GetMapping("")
    public String show(){

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

        return "redirect:/productos";

    }

}
