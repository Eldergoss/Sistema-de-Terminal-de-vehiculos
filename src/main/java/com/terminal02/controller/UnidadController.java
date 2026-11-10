package com.terminal02.controller;

import java.util.List;
import java.util.Optional;

import com.terminal02.model.Unidad;
import com.terminal02.service.UnidadService;

public class UnidadController {

    private final UnidadService unidadService;

    public UnidadController(UnidadService unidadService) {
        this.unidadService = unidadService;
    }

    public List<Unidad> consultarUnidades() {
        return unidadService.consultarUnidades();
    }

       // Busca la unidad asociada a un usuario transportista mediante su ID.
    public Optional<Unidad> buscarPorIdUsuario(int idUsuario) {

        // Delega la búsqueda al Service y devuelve el resultado.
        return unidadService.buscarPorIdUsuario(idUsuario);
    
    }
        // Solicita la actualización de la placa de una unidad.


    public boolean actualizarPlaca(int idUnidad , String nuevaPlaca){
        // Delega la operación al Service y devuelve el resultado.



        return unidadService.actualizarPlaca(idUnidad , nuevaPlaca);
    }

    public boolean registrarUnidad(Unidad unidad){
        return unidadService.registrarUnidad(unidad);
    }

}