package com.curso.ecommerce.controller;

import com.curso.ecommerce.model.Orden;
import com.curso.ecommerce.model.Usuario;
import com.curso.ecommerce.service.IOrdenService;
import com.curso.ecommerce.service.IUsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    private final Logger log = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IOrdenService iOrdenService;

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

    @PostMapping("/acceder")
    public String acceder(Usuario usuario, HttpSession session){

        log.info("Accesos: {}", usuario);

        Optional<Usuario> user = usuarioService.findByEmail(usuario.getEmail());
        log.info("Usuario de DB : {}", user.get());

//        Validacion para saber que tipo de usuario se loggeo
        if(user.isPresent()){
            session.setAttribute("idusuario", user.get().getId());
            if(user.get().getTipo().equals("ADMIN")){
                return "redirect:/administrador";
            }else{
                return "redirect:/";
            }
        }else {
            log.info("Usuario no existe");
        }

        return "redirect:/";

    }

    @GetMapping("/compras")
    public String obtenerCompras(Model modelo, HttpSession session){

        modelo.addAttribute("sesion", session.getAttribute("idusuario") );

        Usuario usuario = usuarioService.findById((Integer) session.getAttribute("idusuario")).get();

        List<Orden> ordenes = iOrdenService.findByUsuario(usuario);

        modelo.addAttribute("ordenes", ordenes);

        return "usuario/compras";

    }

    @GetMapping("/detalle/{id}")
    public String detalleCompra(@PathVariable("id") Integer id, HttpSession session, Model modelo){

        log.info("Id de la orden {}", id);

        Orden orden = iOrdenService.findById(id).get();

        modelo.addAttribute("detalles", orden.getDetalle());

//        session
        modelo.addAttribute("sesion", session.getAttribute("idusuario"));

        return "usuario/detallecompra";
    }

    @GetMapping("/cerrar")
    public String cerrarSesion(HttpSession session){
        session.removeAttribute("idusuario");
        return "redirect:/";
    }

}
