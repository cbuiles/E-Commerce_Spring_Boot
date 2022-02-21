package com.curso.ecommerce.controller;

import com.curso.ecommerce.model.Producto;
import com.curso.ecommerce.model.Usuario;
import com.curso.ecommerce.service.IUsuarioService;
import com.curso.ecommerce.service.ProductoService;
import com.curso.ecommerce.service.UploadFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/productos")
public class ProductoController {

//    Para obtener los Logs de nuestro controlador
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProductoService productoService;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private UploadFileService upload;

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
    public String save(Producto producto, @RequestParam("img") MultipartFile file, HttpSession session) throws IOException {

//        Pruebas para el LOGGER
        LOGGER.info("Este es el objeto producto {}", producto);

        Usuario u = usuarioService.findById((Integer) session.getAttribute("idusuario")).get();

        producto.setUsuario(u);

//        Imagen
        if(producto.getId() == null){//Cuando se crea un producto
            String nombreImagen = upload.saveImage(file);

            producto.setImagen(nombreImagen);

        }else{



        }

        productoService.save(producto);

        return "redirect:/productos";

    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model){

        Producto producto = new Producto();

        Optional<Producto> optionalProducto = productoService.get(id);

        producto = optionalProducto.get();

        LOGGER.info("Producto buscado: {}", producto);

        model.addAttribute("producto", producto);

        return "productos/edit";
    }

    @PostMapping("/update")
    public String update(Producto producto, @RequestParam("img") MultipartFile file) throws IOException {

        Producto p = new Producto();

        p = productoService.get(producto.getId()).get();

        if(file.isEmpty()){//Cuando editamos un producto pero no cambiamos la imagen
            producto.setImagen(p.getImagen());

        }else{// cuando se edita tambien la imagen

//        Eliminar cuando no sea la imagen por defecto
            if(!p.getImagen().equals("default.jpg")){

                upload.deleteImage(p.getImagen());

            }

            String nombreImagen = upload.saveImage(file);

            producto.setImagen(nombreImagen);

        }

        producto.setUsuario(p.getUsuario());

        productoService.update(producto);

        return "redirect:/productos";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id){

        Producto p = new Producto();

        p = productoService.get(id).get();

//        Eliminar cuando no sea la imagen por defecto
        if(!p.getImagen().equals("default.jpg")){

            upload.deleteImage(p.getImagen());

        }

        productoService.delete(id);

        return "redirect:/productos";
    }

}
