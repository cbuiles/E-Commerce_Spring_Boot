package com.curso.ecommerce.service;

import com.curso.ecommerce.model.DetalleOrden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface IDetalleOrdenService {

    DetalleOrden save(DetalleOrden detalleOrden);

}
