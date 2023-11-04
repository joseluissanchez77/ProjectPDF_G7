package org.citas_medidas.entidades;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.ArrayList;
import java.util.List;

public class Feriados {
    private List<String> fecha_feriado;

    public Feriados() {
    }

    public Feriados(List<String> fecha_feriado) {
        this.fecha_feriado = fecha_feriado;
    }

    public List<String> getFecha_feriado() {
        return fecha_feriado;
    }

    public void setFecha_feriado(ArrayNode feriadoNode) {
        fecha_feriado = new ArrayList<>();
        for (JsonNode node : feriadoNode) {
            fecha_feriado.add(node.asText()); // Convierte el nodo a una cadena
        }
    }
}
