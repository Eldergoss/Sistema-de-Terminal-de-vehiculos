
package com.terminal02.service;

import java.util.List;
import java.util.Optional;

import com.terminal02.model.Unidad;
import com.terminal02.repository.UnidadRepository;

// Clase responsable de aplicar las reglas de negocio de las unidades.
public class UnidadService {

    // Dependencia del repositorio.
    private final UnidadRepository unidadRepository;

    // Constructor: recibe el repositorio mediante inyección de dependencia.
    public UnidadService(UnidadRepository unidadRepository) {
        this.unidadRepository = unidadRepository;
    }

    // Consulta todas las unidades.
    public List<Unidad> consultarUnidades() {
        return unidadRepository.consultarUnidades();
    }

    // Busca una unidad por su placa.
    public Optional<Unidad> buscarPorPlaca(String placa) {
        return unidadRepository.buscarPorPlaca(placa);
    }

    // Busca la unidad asociada a un usuario transportista.
    public Optional<Unidad> buscarPorIdUsuario(int idUsuario) {
        return unidadRepository.buscarPorIdUsuario(idUsuario);
    }

    // Actualiza la placa de una unidad.
    public boolean actualizarPlaca(int idUnidad, String nuevaPlaca) {


        //validar que  la plca no sea nula ni este vacia.

        if (nuevaPlaca == null || nuevaPlaca.isBlank()){
            return false;
        }



        // Busca si la nueva placa ya está registrada.
        Optional<Unidad> unidadEncontrada =
                unidadRepository.buscarPorPlaca(nuevaPlaca);

        // Comprueba si la placa pertenece a otra unidad.
        if (unidadEncontrada.isPresent() &&
                unidadEncontrada.get().getId() != idUnidad) {

            return false;
        }

        // Si la placa está disponible, solicita la actualización.
        return unidadRepository.actualizarPlaca(idUnidad, nuevaPlaca);
    }

    // Registra una nueva unidad aplicando las validaciones del negocio.
    public boolean registrarUnidad(Unidad unidad) {

        // Comprobar que el objeto recibido exista.
        if (unidad == null) {
            return false;
        }

        // Comprobar que el número no sea nulo ni esté vacío.
        if (unidad.getNumero() == null ||
                unidad.getNumero().isBlank()) {
            return false;
        }

        // Comprobar que la placa no sea nula ni esté vacía.
        if (unidad.getPlaca() == null ||
                unidad.getPlaca().isBlank()) {
            return false;
        }

        // Buscar si ya existe una unidad con el mismo número.
        Optional<Unidad> unidadPorNumero =
                unidadRepository.buscarPorNumero(unidad.getNumero());

        if (unidadPorNumero.isPresent()) {
            return false;
        }

        // Buscar si ya existe una unidad con la misma placa.
        Optional<Unidad> unidadPorPlaca =
                unidadRepository.buscarPorPlaca(unidad.getPlaca());

        if (unidadPorPlaca.isPresent()) {
            return false;
        }

        // Si todas las validaciones pasan, registrar la unidad.
        return unidadRepository.registrarUnidad(unidad);
    }
}
