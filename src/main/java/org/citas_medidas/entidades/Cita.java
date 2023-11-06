package org.citas_medidas.entidades;

public class Cita {
    private String fecha;
    private String hora;
    private String tipo_cita;
    private String especialidad;
    private String paciente;
    private String tipo_paciente;
    private String tipo_documento;
    private String documento;
    private String telefono;
    private String fecha_nacimiento;
    private String observaciones;
    private String responsable;
    private String tipo_documento_responsable;
    private String documento_responsable;
    private String fecha_nacimiento_responsable;

    public Cita() {
    }

    public Cita(String fecha, String hora, String tipo_cita, String especialidad, String paciente, String tipo_paciente, String tipo_documento, String documento, String telefono, String fecha_nacimiento, String observaciones, String responsable, String tipo_documento_responsable, String documento_responsable, String fecha_nacimiento_responsable) {
        this.fecha = fecha;
        this.hora = hora;
        this.tipo_cita = tipo_cita;
        this.especialidad = especialidad;
        this.paciente = paciente;
        this.tipo_paciente = tipo_paciente;
        this.tipo_documento = tipo_documento;
        this.documento = documento;
        this.telefono = telefono;
        this.fecha_nacimiento = fecha_nacimiento;
        this.observaciones = observaciones;
        this.responsable = responsable;
        this.tipo_documento_responsable = tipo_documento_responsable;
        this.documento_responsable = documento_responsable;
        this.fecha_nacimiento_responsable = fecha_nacimiento_responsable;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getTipo_cita() {
        return tipo_cita;
    }

    public void setTipo_cita(String tipo_cita) {
        this.tipo_cita = tipo_cita;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    public String getTipo_paciente() {
        return tipo_paciente;
    }

    public void setTipo_paciente(String tipo_paciente) {
        this.tipo_paciente = tipo_paciente;
    }

    public String getTipo_documento() {
        return tipo_documento;
    }

    public void setTipo_documento(String tipo_documento) {
        this.tipo_documento = tipo_documento;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getTipo_documento_responsable() {
        return tipo_documento_responsable;
    }

    public void setTipo_documento_responsable(String tipo_documento_responsable) {
        this.tipo_documento_responsable = tipo_documento_responsable;
    }

    public String getDocumento_responsable() {
        return documento_responsable;
    }

    public void setDocumento_responsable(String documento_responsable) {
        this.documento_responsable = documento_responsable;
    }

    public String getFecha_nacimiento_responsable() {
        return fecha_nacimiento_responsable;
    }

    public void setFecha_nacimiento_responsable(String fecha_nacimiento_responsable) {
        this.fecha_nacimiento_responsable = fecha_nacimiento_responsable;
    }
}
