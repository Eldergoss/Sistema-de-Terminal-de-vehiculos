package com.terminal02.model;

public class Destino {

    // Identificador único del destino.
    private int id;

    // Nombre del destino.
    private String nombre;

    // Constructor vacío para permitir la conversión de JSON.
    public Destino() {
    }

    // Constructor para crear un destino con sus datos.
    public Destino(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // Obtener el identificador.
    public int getId() {
        return id;
    }

    // Modificar el identificador.
    public void setId(int id) {
        this.id = id;
    }

    // Obtener el nombre.
    public String getNombre() {
        return nombre;
    }

    // Modificar el nombre.
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}