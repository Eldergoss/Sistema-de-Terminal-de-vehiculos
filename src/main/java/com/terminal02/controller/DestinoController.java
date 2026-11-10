
package com.terminal02.controller;

import com.terminal02.model.Destino;
import com.terminal02.service.DestinoService;

import java.util.List;

public class DestinoController {

    // Servicio encargado de la lógica de destinos.
    private final DestinoService destinoService;

    // Constructor: recibe el servicio.
    public DestinoController(DestinoService destinoService) {
        this.destinoService = destinoService;
    }

    // Registrar un destino.
    public boolean registrarDestino(Destino destino) {
        return destinoService.registrarDestino(destino);
    }

    // Consultar todos los destinos.
    public List<Destino> consultarDestinos() {
        return destinoService.consultarDestinos();
    }
}
