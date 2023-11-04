package org.citas_medidas.entidades;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.ArrayList;
import java.util.List;

public class Especialidad {
    private List<String> especialidad;

    public Especialidad() {
    }

    public Especialidad(List<String> especialidad) {
        this.especialidad = especialidad;
    }

    public List<String> getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(ArrayNode especialidadNode) {
        especialidad = new ArrayList<>();
        for (JsonNode node : especialidadNode) {
            especialidad.add(node.asText()); // Convierte el nodo a una cadena
        }
    }
}
