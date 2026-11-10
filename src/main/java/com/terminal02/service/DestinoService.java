package com.terminal02.service;

import com.terminal02.model.Destino;
import com.terminal02.repository.DestinoRepository;

import java.util.List;

public class DestinoService {

    // Repository encargado de comunicarse con SQLite.
    private final DestinoRepository destinoRepository;

    // Constructor: recibe el Repository.
    public DestinoService(DestinoRepository destinoRepository) {
        this.destinoRepository = destinoRepository;
    }

    // Registrar un destino después de validar sus datos.
    public boolean registrarDestino(Destino destino) {

        // Comprobar que el objeto no sea nulo.
        if (destino == null) {
            return false;
        }

        // Comprobar que el nombre no sea nulo ni esté vacío.
        if (destino.getNombre() == null ||
                destino.getNombre().isBlank()) {
            return false;
        }

        // Eliminar espacios innecesarios al inicio y al final.
        destino.setNombre(destino.getNombre().trim());

        return destinoRepository.registrarDestino(destino);
    }

    // Consultar todos los destinos registrados.
    public List<Destino> consultarDestinos() {
        return destinoRepository.consultarDestinos();
    }
}
