package org.citas_medidas.entidades;

public class TipoPaciente {
    private String tipo;

    public TipoPaciente() {
    }
    public TipoPaciente(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
