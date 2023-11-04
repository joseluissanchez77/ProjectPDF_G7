package org.citas_medidas.entidades;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.ArrayList;
import java.util.List;

public class TipoMedico {

    private List<String> tipo_medico;
    private String general;
    private String especialista;

    private int cantidad_med_general;
    private int cantidad_med_especilista;

    public TipoMedico() {
    }

    public TipoMedico(List<String> tipo_medico, String general, String especialista, int cantidad_med_general, int cantidad_med_especilista) {
        this.tipo_medico = tipo_medico;
        this.general = general;
        this.especialista = especialista;
        this.cantidad_med_general = cantidad_med_general;
        this.cantidad_med_especilista = cantidad_med_especilista;
    }

    public List<String> getTipo_medico() {
        return tipo_medico;
    }

    public void setTipo_medico(ArrayNode tipoMedicoNode) {
        tipo_medico = new ArrayList<>();
        for (JsonNode node : tipoMedicoNode) {
            tipo_medico.add(node.asText()); // Convierte el nodo a una cadena
        }
    }

    public String getGeneral() {
        return general;
    }

    public void setGeneral(String general) {
        this.general = general;
    }

    public String getEspecialista() {
        return especialista;
    }

    public void setEspecialista(String especialista) {
        this.especialista = especialista;
    }

    public int getCantidad_med_general() {
        return cantidad_med_general;
    }

    public void setCantidad_med_general(int cantidad_med_general) {
        this.cantidad_med_general = cantidad_med_general;
    }

    public int getCantidad_med_especilista() {
        return cantidad_med_especilista;
    }

    public void setCantidad_med_especilista(int cantidad_med_especilista) {
        this.cantidad_med_especilista = cantidad_med_especilista;
    }
}
