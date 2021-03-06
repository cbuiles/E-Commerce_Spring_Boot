package com.curso.ecommerce.repository;

import com.curso.ecommerce.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUsuarioRepository extends JpaRepository <Usuario, Integer> {

//    Implementar busqueda por otro campo
    Optional<Usuario> findByEmail(String email);

}
