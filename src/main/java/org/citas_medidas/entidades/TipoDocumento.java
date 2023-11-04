package org.citas_medidas.entidades;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.ArrayList;
import java.util.List;

public class TipoDocumento {
    private String cedula;
    private String pasaporte;
    private List<String> tipo_documento;
    public TipoDocumento() {
    }

    public TipoDocumento(String cedula, String pasaporte, List<String> tipo_documento) {
        this.cedula = cedula;
        this.pasaporte = pasaporte;
        this.tipo_documento = tipo_documento;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getPasaporte() {
        return pasaporte;
    }

    public void setPasaporte(String pasaporte) {
        this.pasaporte = pasaporte;
    }

    public List<String> getTipo_documento() {
        return tipo_documento;
    }

    public void setTipo_documento(ArrayNode tipoDocumentoNode) {
        tipo_documento = new ArrayList<>();
        for (JsonNode node : tipoDocumentoNode) {
            tipo_documento.add(node.asText()); // Convierte el nodo a una cadena
        }
    }
}
