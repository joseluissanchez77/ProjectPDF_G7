package org.citas_medidas.entidades;

import java.util.List;

public class TipoPacienteWrapper extends TipoPaciente {
    private List<TipoPaciente> tipo_paciente;

    public List<TipoPaciente> getTipoPaciente() {
        return tipo_paciente;
    }

    public void setTipoPaciente(List<TipoPaciente> tipo_paciente) {
        this.tipo_paciente = tipo_paciente;
    }
}
