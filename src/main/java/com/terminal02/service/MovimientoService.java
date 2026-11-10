
package com.terminal02.service;

import com.terminal02.model.Movimiento;
import com.terminal02.repository.MovimientoRepository;
import com.terminal02.repository.UnidadRepository;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MovimientoService {

    // Repository encargado de registrar y consultar movimientos.
    private final MovimientoRepository movimientoRepository;

    // Repository encargado de consultar unidades.
    private final UnidadRepository unidadRepository;

    // Constructor: recibe ambos Repository.
    public MovimientoService(
            MovimientoRepository movimientoRepository,
            UnidadRepository unidadRepository) {

        this.movimientoRepository = movimientoRepository;
        this.unidadRepository = unidadRepository;
    }

    // Registra un movimiento después de validar sus datos y reglas de negocio.
    public boolean registrarMovimiento(Movimiento movimiento) {

        // Comprobar que el objeto no sea nulo.
        if (movimiento == null) {
            return false;
        }

        // Comprobar que el ID de unidad sea válido.
        if (movimiento.getIdUnidad() <= 0) {
            return false;
        }

        // Validar el tipo de movimiento.
        if (!"ENTRADA".equals(movimiento.getTipoMovimiento()) &&
                !"SALIDA".equals(movimiento.getTipoMovimiento())) {
            return false;
        }

        // Comprobar que la unidad exista en la base de datos.
        if (unidadRepository.buscarPorId(
                movimiento.getIdUnidad()).isEmpty()) {
            return false;
        }

        // Consultar el último movimiento registrado de la unidad.
        Optional<String> ultimoMovimiento =
                movimientoRepository.buscarUltimoTipoMovimiento(
                        movimiento.getIdUnidad());

        // Validar las reglas de entradas y salidas.
        if ("ENTRADA".equals(movimiento.getTipoMovimiento())) {

            // No permitir otra entrada si la unidad ya está dentro.
            if (ultimoMovimiento.isPresent() &&
                    "ENTRADA".equals(ultimoMovimiento.get())) {
                return false;
            }

        } else if ("SALIDA".equals(movimiento.getTipoMovimiento())) {

            // No permitir una salida si no existe una entrada activa.
            if (ultimoMovimiento.isEmpty() ||
                    "SALIDA".equals(ultimoMovimiento.get())) {
                return false;
            }
        }

        // Obtener la fecha y hora actuales del sistema.
        LocalDateTime ahora = LocalDateTime.now();

        // Generar automáticamente la fecha.
        movimiento.setFecha(
                ahora.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        // Generar automáticamente la hora.
        movimiento.setHora(
                ahora.format(DateTimeFormatter.ofPattern("HH:mm")));

        // Registrar el movimiento después de superar las validaciones.
        return movimientoRepository.registrarMovimiento(movimiento);
    }

    // Consultar todos los movimientos registrados.
    public List<Movimiento> consultarMovimientos() {
        return movimientoRepository.consultarMovimientos();
    }

    // Consultar el historial de movimientos de una unidad.
    public List<Movimiento> consultarMovimientosPorUnidad(int idUnidad) {

        // Validar que el ID sea positivo.
        if (idUnidad <= 0) {
            return List.of();
        }

        // Comprobar que la unidad exista.
        if (unidadRepository.buscarPorId(idUnidad).isEmpty()) {
            return List.of();
        }

        // Consultar los movimientos de esa unidad.
        return movimientoRepository.consultarMovimientosPorUnidad(idUnidad);
    }
}
