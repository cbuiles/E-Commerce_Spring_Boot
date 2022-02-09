package com.curso.ecommerce.controller;

import com.curso.ecommerce.service.ProductoService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")//Apunta a la raiz del proyecto
public class HomeController {

    private final Logger log = LoggerFactory.getLogger(HomeController.class);


    @Autowired
    private ProductoService productoService;

    @GetMapping("")
    public String home(Model modelo){

        modelo.addAttribute("productos", productoService.findAll());

        return "usuario/home";

    }

    @GetMapping("productohome/{id}")
    public String productoHome(@PathVariable("id") Integer id){

        log.info("Id producto enviado como parametro {}", id);

        return "usuario/productohome";

    }

}
