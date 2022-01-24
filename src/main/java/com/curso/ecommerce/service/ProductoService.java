package com.curso.ecommerce.service;

import com.curso.ecommerce.model.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoService {

    public Producto save(Producto producto);

//    Retorna objeto Optional pq nos da la opcion de corroborar si nos devuelve el objeto
    public Optional<Producto> get(Integer id);

    public void update(Producto producto);

    public void delete(Integer id);

    public List<Producto> findAll();

}
