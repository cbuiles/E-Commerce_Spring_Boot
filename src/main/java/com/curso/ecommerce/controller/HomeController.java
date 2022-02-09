package com.curso.ecommerce.controller;

import com.curso.ecommerce.model.Producto;
import com.curso.ecommerce.service.ProductoService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

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
    public String productoHome(@PathVariable("id") Integer id, Model modelo){

        log.info("Id producto enviado como parametro {}", id);

        Producto producto = new Producto();

        Optional<Producto> productoOptional = productoService.get(id);

        producto = productoOptional.get();

        modelo.addAttribute("producto", producto);

        return "usuario/productohome";

    }

    @PostMapping("/cart")
    public String addCart(){

        return "usuario/carrito";

    }

}
