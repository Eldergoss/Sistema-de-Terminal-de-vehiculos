package com.terminal02.model;

public class Movimiento {

    // Identificador único del movimiento.
    private int id;

    // ID de la unidad relacionada con el movimiento.
    private int idUnidad;

    // Tipo de movimiento: ENTRADA o SALIDA.
    private String tipoMovimiento;

    // Fecha en la que ocurrió el movimiento.
    private String fecha;

    // Hora en la que ocurrió el movimiento.
    private String hora;

    // ID del destino asociado (puede ser null).
    private Integer idDestino;

    // Constructor vacío.
    public Movimiento() {
    }

    // Constructor con todos los atributos.
    public Movimiento(int id, int idUnidad, String tipoMovimiento,
                      String fecha, String hora, Integer idDestino) {
        this.id = id;
        this.idUnidad = idUnidad;
        this.tipoMovimiento = tipoMovimiento;
        this.fecha = fecha;
        this.hora = hora;
        this.idDestino = idDestino;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(int idUnidad) {
        this.idUnidad = idUnidad;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public Integer getIdDestino() {
        return idDestino;
    }

    public void setIdDestino(Integer idDestino) {
        this.idDestino = idDestino;
    }
}