package org.citas_medidas.entidades;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.ArrayList;
import java.util.List;

public class TipoPaciente {
    private String adulto;
    private String menor;
    private List<String> tipo_paciente;
    public TipoPaciente() {
    }

    public TipoPaciente(String adulto, String menor, List<String> tipo_paciente) {
        this.adulto = adulto;
        this.menor = menor;
        this.tipo_paciente = tipo_paciente;
    }

    public String getAdulto() {
        return adulto;
    }

    public void setAdulto(String adulto) {
        this.adulto = adulto;
    }

    public String getMenor() {
        return menor;
    }

    public void setMenor(String menor) {
        this.menor = menor;
    }

    public List<String> getTipo_paciente() {
        return tipo_paciente;
    }

    public void setTipo_paciente(ArrayNode tipoPacienteNode) {
        tipo_paciente = new ArrayList<>();
        for (JsonNode node : tipoPacienteNode) {
            tipo_paciente.add(node.asText()); // Convierte el nodo a una cadena
        }
    }
}
