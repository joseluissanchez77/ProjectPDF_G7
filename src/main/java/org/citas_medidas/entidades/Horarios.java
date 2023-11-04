package org.citas_medidas.entidades;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Horarios {

    private String dia;
    private String hora_incio;
    private String hora_fin;

    public Horarios() {
    }

    public Horarios(String dia, String hora_incio, String hora_fin) {
        this.dia = dia;
        this.hora_incio = hora_incio;
        this.hora_fin = hora_fin;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getHora_incio() {
        return hora_incio;
    }

    public void setHora_incio(String hora_incio) {
        this.hora_incio = hora_incio;
    }

    public String getHora_fin() {
        return hora_fin;
    }

    public void setHora_fin(String hora_fin) {
        this.hora_fin = hora_fin;
    }
}
