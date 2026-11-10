
package com.terminal02.controller;

import com.terminal02.model.Movimiento;
import com.terminal02.service.MovimientoService;

import java.util.List;

public class MovimientoController {

    // Servicio encargado de la lógica de movimientos.
    private final MovimientoService movimientoService;

    // Constructor: recibe el Service.
    public MovimientoController(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }

    // Registrar una entrada o salida.
    public boolean registrarMovimiento(Movimiento movimiento) {
        return movimientoService.registrarMovimiento(movimiento);
    }

    // Consultar todos los movimientos registrados.
    public List<Movimiento> consultarMovimientos() {
        return movimientoService.consultarMovimientos();
    }

    // Consultar los movimientos de una unidad específica.
    public List<Movimiento> consultarMovimientosPorUnidad(int idUnidad) {
        return movimientoService.consultarMovimientosPorUnidad(idUnidad);
    }
}
