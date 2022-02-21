package com.curso.ecommerce.controller;

import com.curso.ecommerce.model.DetalleOrden;
import com.curso.ecommerce.model.Orden;
import com.curso.ecommerce.model.Producto;
import com.curso.ecommerce.model.Usuario;
import com.curso.ecommerce.service.IDetalleOrdenService;
import com.curso.ecommerce.service.IOrdenService;
import com.curso.ecommerce.service.IUsuarioService;
import com.curso.ecommerce.service.ProductoService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")//Apunta a la raiz del proyecto
public class HomeController {

    private final Logger log = LoggerFactory.getLogger(HomeController.class);


    @Autowired
    private ProductoService productoService;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IOrdenService ordenService;

    @Autowired
    public IDetalleOrdenService detalleOrdenService;

//    Para almacenar los detalles de la orden
    List<DetalleOrden> detalles = new ArrayList<DetalleOrden>();

//    Datos de la Orden
    Orden orden = new Orden();

    @GetMapping("")
    public String home(Model modelo, HttpSession session){

        log.info("Session de usuario: {}", session.getAttribute("idusuario"));
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
    public String addCart(@RequestParam Integer id, @RequestParam Integer cantidad, Model modelo){

        DetalleOrden detalleOrden = new DetalleOrden();

        Producto producto = new Producto();

        double sumaTotal = 0;

        Optional<Producto> optionalProducto = productoService.get(id);

        log.info("Producto añádido: {}", optionalProducto.get());
        log.info("Cantidad: {}", cantidad);
        producto = optionalProducto.get();

        detalleOrden.setCantidad(cantidad);
        detalleOrden.setPrecio(producto.getPrecio());
        detalleOrden.setNombre(producto.getNombre());
        detalleOrden.setTotal(producto.getPrecio() * cantidad);
        detalleOrden.setProducto(producto);

//        Validar que el producto no se añada 2 veces
        Integer idProducto = producto.getId();
        boolean ingresado = detalles.stream().anyMatch(p -> p.getProducto().getId() == idProducto);

        if(!ingresado){
            detalles.add(detalleOrden);
        }

        sumaTotal= detalles.stream().mapToDouble(dt->dt.getTotal()).sum();

        orden.setTotal(sumaTotal);

        modelo.addAttribute("cart", detalles);
        modelo.addAttribute("orden", orden);

        return "usuario/carrito";

    }

//    Quitar un producto del carrito
    @GetMapping("/delete/cart/{id}")
    public String deleteProductCart(@PathVariable("id") Integer id, Model modelo){

//        Lista nueva de productos
        List<DetalleOrden> ordenesNueva = new ArrayList<DetalleOrden>();



//        for(DetalleOrden detalleOrden: detalles){
//            if(detalleOrden.getProducto().getId() != id){
//                ordenesNueva.add(detalleOrden);
//            }
//        }
//
////        Poner la nueva lista con los productos restantes
//        detalles = ordenesNueva;

//        ================================================================
//        Filtrado con metodo Lamba
//        ================================================================
        detalles = detalles.stream() //Se crea el Stream
//                Filtro para obtener los productos con el ID enviado
                .filter( dt -> dt.getProducto().getId() != id)
//                Pone las ordenes que se filtraron dentro de una nueva lista
                .collect(Collectors.toList());

        log.info("Esta es la lista actual en el carrito {}", detalles);

        double sumaTotal = 0;

        sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();

        orden.setTotal(sumaTotal);
        modelo.addAttribute("cart", detalles);
        modelo.addAttribute("orden", orden);

        return "usuario/carrito";

    }

    @GetMapping("/getCart")
    public String getCart(Model modelo){

        modelo.addAttribute("cart", detalles);
        modelo.addAttribute("orden", orden);

        return "usuario/carrito";
    }

    @GetMapping("/order")
    public String order(Model modelo, HttpSession session){

        Usuario usuario = usuarioService.findById((Integer) session.getAttribute("idusuario")).get();

        modelo.addAttribute("cart", detalles);
        modelo.addAttribute("orden", orden);
        modelo.addAttribute("usuario", usuario);

        return "usuario/resumenorden";

    }

//    Guardar la orden
    @GetMapping("/saveOrder")
    public  String saveOrder(HttpSession session){

        Date fechaCreacion = new Date();
        orden.setFechaCreacion(fechaCreacion);
        orden.setNumero(ordenService.generarNumeroOrden());

//        Usuario
        Usuario usuario = usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get();

        orden.setUsuario(usuario);
        ordenService.save(orden);

//        Guardar detalles
        for(DetalleOrden dt: detalles){
            dt.setOrden(orden);
            detalleOrdenService.save(dt);
        }

//        Limpieza de la lista y orden
        orden = new Orden();
        detalles.clear();

        return "redirect:/";
    }

    @PostMapping("/search")
    public String searcgProduct(@RequestParam String nombre, Model modelo){

        log.info("Nombre del producto: {}", nombre);
        List<Producto> productos = productoService.findAll().
                stream(). //Crear el flujo de datos
                filter(p-> p.getNombre().contains(nombre)) //Filtrar por cada uno de los nombres que lo contienen (Retorna un String)
                .collect(Collectors.toList());//Convertimos ese String en una lista

        modelo.addAttribute("productos", productos);


        return "usuario/home";

    }
}
