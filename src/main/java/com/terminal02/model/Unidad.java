
package com.terminal02.model;

/**
 * Representa una unidad de transporte dentro del sistema.
 */
public class Unidad {

    // Atributos que representan los datos de una unidad.
    private int id;
    private String numero;
    private String placa;

    // Constructor vacío: permite crear el objeto sin argumentos.
    public Unidad() {
    }

    // Constructor completo: permite crear una unidad con todos sus datos.
    public Unidad(int id, String numero, String placa) {
        this.id = id;
        this.numero = numero;
        this.placa = placa;
    }

    // Devuelve el identificador de la unidad.
    public int getId() {
        return id;
    }

    // Devuelve el número de la unidad.
    public String getNumero() {
        return numero;
    }

    // Devuelve la placa de la unidad.
    public String getPlaca() {
        return placa;
    }

    // Modifica el identificador de la unidad.
    public void setId(int id) {
        this.id = id;
    }

    // Modifica el número de la unidad.
    public void setNumero(String numero) {
        this.numero = numero;
    }

    // Modifica la placa de la unidad.
    public void setPlaca(String placa) {
        this.placa = placa;
    }
}
