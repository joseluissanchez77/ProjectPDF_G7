package org.citas_medidas;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.citas_medidas.entidades.*;
import org.citas_medidas.excepciones.MensageErrorException;

import javax.swing.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {

        // Inicializa un objeto ObjectMapper de Jackson
        ObjectMapper objectMapper = new ObjectMapper();
        List<Horarios> horariosList = new ArrayList<>();

        try {
            // El valor de nombreProyecto contendrá la ruta completa del proyecto
            String nombreProyecto = new File("").getAbsolutePath();
            String rutaRelativaProyecto = nombreProyecto.replace("\\", "/");
            String rutaDiccionario = rutaRelativaProyecto
                    + "/src/main/java/org/citas_medidas/diccionario/diccionario.txt";

            File archivo = new File(rutaDiccionario);

            if (!archivo.exists())
                MensageErrorException.detenerEjecucion("El archivo [diccionario.txt] no existe");

            StringBuilder contenido = new StringBuilder();
            BufferedReader lector = new BufferedReader(new FileReader(rutaDiccionario));
            String linea;

            while ((linea = lector.readLine()) != null) {
                contenido.append(linea);
            }

            lector.close();

            String contenidoComoString = contenido.toString();
            // Lee el archivo JSON y lo convierte en un árbol JSON
            JsonNode contenidoDicc = objectMapper.readTree(contenidoComoString);

            // ========================================================
            // leer y asignar el tipo de paciente al objeto
            ArrayNode tipoPacienteNode = (ArrayNode) contenidoDicc.get("tipo_paciente");

            if (tipoPacienteNode == null)
                MensageErrorException.detenerEjecucion("No se encontró el valor de 'tipo_paciente' en el archivo.");

            TipoPaciente tipoPacienteObj = new TipoPaciente();
            tipoPacienteObj.setTipo_paciente(tipoPacienteNode);

            List<String> tipoPacienteList = tipoPacienteObj.getTipo_paciente();
            for (String valor : tipoPacienteList) {
                switch (valor) {
                    case "ADULTO":
                        tipoPacienteObj.setAdulto(valor);
                        break;
                    case "PMENOR":
                        tipoPacienteObj.setMenor(valor);
                        break;
                }
            }

            // ---------------------------------------------------------
            // leer y asignar el tipo de documento al objeto
            ArrayNode tipoDocumentoNode = (ArrayNode) contenidoDicc.get("tipo_documento");

            if (tipoDocumentoNode == null)
                MensageErrorException.detenerEjecucion("No se encontró el valor de 'tipo_documento' en el archivo.");

            TipoDocumento tipoDocumentoObj = new TipoDocumento();
            tipoDocumentoObj.setTipo_documento(tipoDocumentoNode);

            List<String> tipoDocumentoList = tipoDocumentoObj.getTipo_documento();
            for (String valor : tipoDocumentoList) {
                switch (valor) {
                    case "C":
                        tipoDocumentoObj.setCedula(valor);
                        break;
                    case "P":
                        tipoDocumentoObj.setPasaporte(valor);
                        break;
                }
            }

            // leer y asignar feriado al objeto
            ArrayNode feriadosNode = (ArrayNode) contenidoDicc.get("feriados");

            if (feriadosNode == null)
                MensageErrorException.detenerEjecucion("No se encontró el valor de 'feriados' en el archivo.");

            Feriados feriadosObj = new Feriados();
            feriadosObj.setFecha_feriado(feriadosNode);

            // List<String> feriadoList = feriadosObj.getFecha_feriado();
            // for (String valor : feriadoList) {
            // System.out.println("Feriado "+valor);
            // }

            // leer y asignar el tipo de medico
            ArrayNode tipoMedicoNode = (ArrayNode) contenidoDicc.get("tipo_medico");

            if (tipoMedicoNode == null)
                MensageErrorException.detenerEjecucion("No se encontró el valor de 'tipo_medico' en el archivo.");

            TipoMedico tipoMedicoObj = new TipoMedico();
            tipoMedicoObj.setTipo_medico(tipoMedicoNode);

            List<String> tipoMedicoList = tipoMedicoObj.getTipo_medico();
            for (String valor : tipoMedicoList) {
                switch (valor) {
                    case "GENERAL":
                        tipoMedicoObj.setGeneral(valor);
                        tipoMedicoObj.setCantidad_med_general(2);
                        break;
                    case "ESPECIALISTA":
                        tipoMedicoObj.setEspecialista(valor);
                        tipoMedicoObj.setCantidad_med_especilista(3);
                        break;
                }
            }

            // leer y asignar la espacialidad
            ArrayNode especialidadNode = (ArrayNode) contenidoDicc.get("especialidad");

            if (especialidadNode == null)
                MensageErrorException.detenerEjecucion("No se encontró el valor de 'especialidad' en el archivo.");

            Especialidad especialidadObj = new Especialidad();
            especialidadObj.setEspecialidad(especialidadNode);

            List<String> especialidadList = especialidadObj.getEspecialidad();
            // for (String valor : especialidadList) {
            // System.out.println("Espacialidad: "+valor);
            // }

            // leer y asignar horarios
            // Accede a los datos del archivo JSON según su estructura
            JsonNode horariosNode = contenidoDicc.get("horarios");

            if (horariosNode == null)
                MensageErrorException.detenerEjecucion("No se encontró el valor de 'horarios' en el archivo.");

            Iterator<Map.Entry<String, JsonNode>> fields = horariosNode.fields();

            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> entry = fields.next();
                String dia = entry.getKey(); // Obtiene el día (clave)
                JsonNode horarioNode = entry.getValue(); // Obtiene el valor (horario)

                // Crea un objeto Horario y agrégalo a la lista
                Horarios horario = new Horarios(dia, horarioNode.get(0).asText(), horarioNode.get(1).asText());
                horariosList.add(horario);

            }
            // =====================================================================0
            // System.out.println(tipoPacienteObj.getAdulto());
            // System.out.println(tipoDocumentoObj.getCedula());
            // System.out.println(tipoMedicoObj.getEspecialista());
            // for (Horarios horario : horariosList) {
            // System.out.println("Día: " + horario.getDia());
            // System.out.println("Horario de inicio: " + horario.getHora_incio());
            // System.out.println("Horario de fin: " + horario.getHora_fin());
            // }

            System.out.println("====COMIENZA A LEER EL TXT====");

            // El valor de nombreProyecto contendrá la ruta completa del proyecto
            String rutaInputMed = rutaRelativaProyecto
                    + "/src/main/java/org/citas_medidas/input/med_input.txt";

            File archivoMedInput = new File(rutaInputMed);

            if (!archivoMedInput.exists())
                MensageErrorException.detenerEjecucion("El archivo [med_input.txt] no existe");

            List<Cita> citas = new ArrayList<>();
            List<String> citasNoCumplenRequerimientos = new ArrayList<>();
            Cita citaActual = new Cita();

            // StringBuilder contenido = new StringBuilder();
            BufferedReader lectorInputMed = new BufferedReader(new FileReader(rutaInputMed));
            String citaLinea;

            String fecha_actual = null;

            boolean nueva_cita = false;
            // Obtener los feriados
            List<String> feriadoList = feriadosObj.getFecha_feriado();
            String fecha_cita = null;
            while ((citaLinea = lectorInputMed.readLine()) != null) {

                String[] parte = citaLinea.split("\\|");

                nueva_cita = citaLinea.trim().equals("NUEVA CITA") || nueva_cita == true ? true : false;

                // registar las citas que ya estan agendadas al objeto
                if (!(citaLinea == null) && !(citaLinea.isEmpty()) && nueva_cita == false) {
                    if (parte.length == 1)
                        fecha_cita = parte[0];
                    if (parte.length >= 9) {
                        citaActual.setFecha(fecha_cita);
                        citaActual.setHora(parte[0]);
                        citaActual.setTipo_cita(parte[1]);
                        citaActual.setEspecialidad(parte[2]);
                        citaActual.setPaciente(parte[3]);
                        citaActual.setTipo_paciente(parte[4]);
                        citaActual.setTipo_documento(parte[5]);
                        citaActual.setDocumento(parte[6]);
                        citaActual.setTelefono(parte[7]);
                        citaActual.setFecha_nacimiento(parte[8]);

                        if (parte.length > 10) {
                            citaActual.setObservaciones(parte[9]);
                            citaActual.setResponsable(parte[10]);
                            citaActual.setTipo_documento_responsable(parte[11]);
                            citaActual.setDocumento_responsable(parte[12]);
                            citaActual.setFecha_nacimiento_responsable(parte[13]);
                        }

                        // Asegúrate de agregar la última cita después del bucle
                        if (citaActual != null) {
                            citas.add(citaActual);
                        }
                        citaActual = new Cita();
                    }
                }

                // La cadena no está vacía CITAS NUEVAS ***
                if (!(citaLinea == null) && !(citaLinea.isEmpty()) && nueva_cita && parte.length >=9) {

                    // validar que la fecha sea valida y sea diferente q la actual
                    if (esFechaValida(citaLinea, "yyyy-MM-dd") && fecha_actual != citaLinea) {
                        fecha_actual = citaLinea;
                    }

                    if (citaLinea.trim().equals("NUEVA CITA")) {
                        fecha_actual = "";
                    }
                    //validar el horario de atencion
                    Boolean validarFecha = esFechaValida(parte[0], "yyyy-MM-dd");
                    String fecha_atc = validarFecha ? parte[0] : fecha_actual;

                    //Los días feriados no hay atención de ningún servicio.
                    boolean validateFechaFeriado = validateFechaFeriado(fecha_atc, feriadoList);

                    String hora_cita = "";
                    boolean validarHorariosConsultaMedica = false;
                    if (parte.length >= 9) {
                        hora_cita = !validarFecha ? parte[0] : parte[1];
                        validarHorariosConsultaMedica = validarHorariosConsultaMedica(fecha_atc, hora_cita,
                                horariosList);
                    }

                    //validar que Solo se pueden registrar citas en el futuro.
                    boolean validarRegistroCitasSoloFuturo = validarRegistroCitasSoloFuturo(obtenerFechaActual(),obtenerHoraActual(),fecha_atc,hora_cita);

                    //*Para el caso de menores de edad, se debe registrar un adulto apoderado con los
                    // mismos datos que un paciente y los datos de contacto serán los del apoderado
                    // en lugar de los datos del paciente
                    //*Los apoderados deben ser mayores de edad
                    String tipo_paciente = "";
                    String fecha_nacimiento_paciente = "";
                    String fecha_nacimiento_apoderado = "";
                    boolean validarApoderadosPacientes = false;
                    if (parte.length >= 9) {
                        tipo_paciente = !validarFecha ? parte[4] : parte[5];
                        fecha_nacimiento_paciente = !validarFecha ? parte[8] : parte[9];
                        if(parte.length > 10){
                            fecha_nacimiento_apoderado = !validarFecha ? parte[13] : parte[14];
                        }
                        //si retorna FALSE no puede registar si es TRUE si
                        validarApoderadosPacientes = validarApoderadosPacientes(tipo_paciente, tipoPacienteObj, fecha_nacimiento_paciente,parte.length, fecha_nacimiento_apoderado);


                    }



                    //Un paciente no puede tener citas simultáneas ni en el mismo servicio ni en distintos servicios
                    boolean validarCitasSimultaneasServicios = false;

                    String doc_idententificacion= "";
                    if (parte.length >= 9) {
                        doc_idententificacion = !validarFecha ? parte[6] : parte[7];
                        //si retorna FALSE no existe   TRUE ya existe
                        validarCitasSimultaneasServicios = validarCitasSimultaneasServicios(fecha_atc, hora_cita,doc_idententificacion, citas);

                    }

                    String tipo_cita = "";
                    String especialidad = "";
                    LocalDateTime fecha_hora_cita = null;

                    //Las citas para especialistas deben agendarse con al menos 24h de anticipación.
                    boolean validarCitaEspecialista24hAntes = false;
                    if (parte.length >= 9) {
                        tipo_cita = !validarFecha ? parte[1] : parte[2];

                        fecha_hora_cita = LocalDateTime.parse(( fecha_atc.trim()+" "+hora_cita.trim() ), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

                        //si retorna FALSE se lo puede agregar   TRUE no se lo pude agregar
                        validarCitaEspecialista24hAntes = validarCitaEspecialista24hAntes(fecha_hora_cita,tipo_cita, tipoMedicoObj);
                    }


                    //Ningún profesional puede tener citas simultáneas
                    boolean validarCitasSimultaneasPorProfesional = false;
                    if (parte.length >= 9) {
                        tipo_cita = !validarFecha ? parte[1] : parte[2];
                        especialidad = !validarFecha ? parte[2] : parte[3];
                        //si retorna TRUE se lo pruede agregar   FALSE no se lo puede agregar
                        validarCitasSimultaneasPorProfesional = validarCitasSimultaneasPorProfesional(fecha_atc, hora_cita,especialidad, doc_idententificacion, citas);
                    }

                    //La última cita se puede hacer 20 minutos antes de la hora de cierre.
                    //si retorna TRUE se pude agendar si retorna FALSE no
                    boolean validarUltimaHoraCita = validarUltimaHoraCita(fecha_hora_cita, horariosList);


                    //System.out.println(validateFechaFeriado+ " "+validarHorariosConsultaMedica+ " "+validarRegistroCitasSoloFuturo+ " "+validarCitasSimultaneasServicios + " "+ validarCitasSimultaneasPorProfesional+ " "+ validarCitaEspecialista24hAntes + " "+  validarApoderadosPacientes + " "+  validarUltimaHoraCita );
                    if (parte.length >= 9 && validateFechaFeriado == false && validarHorariosConsultaMedica && validarRegistroCitasSoloFuturo && validarCitasSimultaneasServicios == false && validarCitasSimultaneasPorProfesional == false && validarCitaEspecialista24hAntes && validarApoderadosPacientes && validarUltimaHoraCita ) {

                        //System.out.println(doc_idententificacion);
                        String paciente = !validarFecha ? parte[3] : parte[4];



                        citaActual.setFecha(fecha_atc);
                        citaActual.setHora(hora_cita);
                        citaActual.setTipo_cita(tipo_cita);
                        citaActual.setEspecialidad(especialidad);
                        citaActual.setPaciente(paciente);
                        citaActual.setTipo_paciente(tipo_paciente);
                        citaActual.setTipo_documento(!validarFecha ? parte[5] : parte[6]);
                        citaActual.setDocumento(doc_idententificacion);
                        citaActual.setTelefono(!validarFecha ? parte[7] : parte[8]);
                        citaActual.setFecha_nacimiento(fecha_nacimiento_paciente);

                        if (parte.length > 10) {
                            citaActual.setObservaciones(!validarFecha ? parte[9] : parte[10]);
                            citaActual.setResponsable(!validarFecha ? parte[10] : parte[11]);
                            citaActual.setTipo_documento_responsable(!validarFecha ? parte[11] : parte[12]);
                            citaActual.setDocumento_responsable(!validarFecha ? parte[12] : parte[13]);
                            citaActual.setFecha_nacimiento_responsable(fecha_nacimiento_apoderado);
                        }

                        // Asegúrate de agregar la última cita después del bucle
                        if (citaActual != null) {
                            citas.add(citaActual);
                        }
                        citaActual = new Cita();
                    }
                    // todo lo q no cumple con las condiciones
                    if (parte.length >= 9) {
                        //Los días feriados no hay atención de ningún servicio.
                        //Las consultas médicas tienen los siguientes horarios
                        //Solo se pueden registrar citas en el futuro. validado con la fecha y hora actual
                        if (validateFechaFeriado || !validarHorariosConsultaMedica || !validarRegistroCitasSoloFuturo || validarCitasSimultaneasServicios || validarCitasSimultaneasPorProfesional || !validarCitaEspecialista24hAntes || !validarApoderadosPacientes || !validarUltimaHoraCita)
                            citasNoCumplenRequerimientos.add(citaLinea);

                        citaActual = new Cita();
                    }

                }

                //// contenido.append(linea);
                // if (citaLinea.startsWith("NUEVA CITA")) {
                // if (citaActual != null) {
                // citas.add(citaActual);
                // }
                // citaActual = new Cita();
                // } else if (citaActual != null && !citaLinea.isEmpty()) {
                // String[] parts = citaLinea.split("\\|");
                // if (parts.length >= 9) {
                // citaActual.setFecha(parts[0]);
                // citaActual.setHora(parts[1]);
                // citaActual.setTipo_cita(parts[2]);
                // citaActual.setEspecialidad(parts[3]);
                // citaActual.setPaciente(parts[4]);
                // citaActual.setTipo_paciente(parts[5]);
                // citaActual.setTipo_documento(parts[6]);
                // citaActual.setDocumento(parts[7]);
                // citaActual.setTelefono(parts[8]);
                // citaActual.setFecha_nacimiento(parts[9]);
                //
                // if (parts.length > 10) {
                // citaActual.setObservaciones(parts[10]);
                // citaActual.setResponsable(parts[11]);
                // citaActual.setTipo_documento_responsable(parts[12]);
                // citaActual.setDocumento_responsable(parts[13]);
                // citaActual.setFecha_nacimiento_responsable(parts[14]);
                // }
                // }
                // }
                //

            }

            lectorInputMed.close();

            // Ahora tienes una lista de objetos Cita
            int i = 1;
            System.out.println("=== CITAS APROBADAS ===");
            for (Cita cita : citas) {
                StringBuilder sb = new StringBuilder();
                sb.append(i++);
                sb.append("|");
                sb.append(cita.getFecha());
                sb.append("|");
                sb.append(cita.getHora());
                sb.append("|");
                sb.append(cita.getTipo_cita());
                sb.append("|");
                sb.append(cita.getEspecialidad());
                sb.append("|");
                sb.append(cita.getPaciente());
                sb.append("|");
                sb.append(cita.getTipo_paciente());
                sb.append("|");
                sb.append(cita.getTipo_documento());
                sb.append("|");
                sb.append(cita.getDocumento());
                sb.append("|");
                sb.append(cita.getTelefono());
                sb.append("|");
                sb.append(cita.getFecha_nacimiento());
                sb.append("|");
                if (cita.getObservaciones() != null) {
                    sb.append(cita.getObservaciones());
                    sb.append("|");
                    sb.append(cita.getResponsable());
                    sb.append("|");
                    sb.append(cita.getTipo_documento_responsable());
                    sb.append("|");
                    sb.append(cita.getDocumento_responsable());
                    sb.append("|");
                    sb.append(cita.getFecha_nacimiento_responsable());
                }

                String resultado = sb.toString();
                System.out.println(resultado);

                // System.out.println("Cita:"+ i++);
                // System.out.println("Fecha: " + cita.getFecha());
                // System.out.println("Hora: " + cita.getHora());
                // System.out.println("Tipo de Cita: " + cita.getTipo_cita());
                // System.out.println("Especialidad: " + cita.getEspecialidad());
                // System.out.println("Paciente: " + cita.getPaciente());
                // System.out.println("Tipo de Paciente: " + cita.getTipo_paciente());
                // System.out.println("Tipo de Documento: " + cita.getTipo_documento());
                // System.out.println("Documento: " + cita.getDocumento());
                // System.out.println("Teléfono: " + cita.getTelefono());
                // System.out.println("Fecha de Nacimiento: " + cita.getFecha_nacimiento());
                //
                // if (cita.getObservaciones() != null) {
                // System.out.println("Observaciones: " + cita.getObservaciones());
                // System.out.println("Responsable: " + cita.getResponsable());
                // System.out.println("Tipo de Documento del Responsable: " +
                // cita.getTipo_documento_responsable());
                // System.out.println("Documento del Responsable: " +
                // cita.getDocumento_responsable());
                // System.out.println("Fecha de Nacimiento del Responsable: " +
                // cita.getFecha_nacimiento_responsable());
                // }
                //
                // System.out.println(); // Salto de línea para separar las citas
            }

            System.out.println("=== CITAS QUE NO CUMPLEN ===");
            for (String cita : citasNoCumplenRequerimientos) {
                System.out.println(cita);
            }

        } catch (MensageErrorException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean esFechaValida(String fecha, String formato) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formato);
            LocalDate.parse(fecha, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static boolean validateFechaFeriado(String fecha, List<String> feriados) {
        boolean band = false;// no es feriado
        if (fecha != null && !fecha.isEmpty()) {
            String fecha_actual = fecha.split("-")[1] + "-" + fecha.split("-")[2];

            for (String valor : feriados) {
                if (valor.trim().equals(fecha_actual.trim()))
                    band = true;// Si es feriado
            }
        }
        return band;
    }

    public static boolean validarHorariosConsultaMedica(String fecha_cita, String horaCita,
            List<Horarios> horariosList) {
        //TRUE permite el registro FALSE no se registra
        boolean band = false; // cita fuera de rango
        // Convertir la hora de la cita en un objeto LocalTime
        LocalTime horaCitaLocalTime = LocalTime.parse(horaCita);

        // Analizar la fecha
        LocalDate fecha = LocalDate.parse(fecha_cita);
        // Obtener el día de la semana
        DayOfWeek diaSemana = fecha.getDayOfWeek();
        // Convertir a nombre del día de la semana
        String nombreDia = diaSemana.getDisplayName(TextStyle.FULL, new Locale("es", "ES"));

        for (Horarios horario : horariosList) {

            // Definir el rango de horas deseado (08:00 a 16:00)
            LocalTime horaInicio = LocalTime.parse(horario.getHora_incio());
            LocalTime horaFin = LocalTime.parse(horario.getHora_fin());


            if ((horario.getDia().equals(nombreDia.toUpperCase()))
                    && (horaCitaLocalTime.equals(horaInicio) || horaCitaLocalTime.isAfter(horaInicio))
                    && (horaCitaLocalTime.equals(horaFin) || (horaCitaLocalTime.isBefore(horaFin)))) {
                band = true;// cita en el rango
            }

        }

        return band;

    }

    public static String obtenerFechaActual(){
        // Obtener la fecha actual
        LocalDate fechaActual = LocalDate.now();

        // Definir el formato
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Formatear la fecha en el formato deseado
        String fechaFormateada = fechaActual.format(formato);
        return fechaFormateada;
    }

    public  static String obtenerHoraActual(){
        // Obtener la hora actual
        LocalTime horaActual = LocalTime.now();

        // Definir el formato
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm");

        // Formatear la hora en el formato deseado
        String horaFormateada = horaActual.format(formato);
        return horaFormateada;

    }

    public static Boolean validarRegistroCitasSoloFuturo(String fecha_actual, String hora_actual, String fecha_cita, String hora_cita ){
        boolean band = false;//la fecha y la hora ya pasaron y no se puede registar


        if(!fecha_actual.isEmpty() && !hora_actual.isEmpty() && !fecha_cita.isEmpty() && !hora_cita.isEmpty() ){


            LocalDateTime fecha_actual_sistema = LocalDateTime.parse(( fecha_actual.trim()+" "+hora_actual.trim() ), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            LocalDateTime fecha_cita_medica = LocalDateTime.parse(( fecha_cita.trim()+" "+hora_cita.trim() ), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

            if (fecha_cita_medica.isEqual(fecha_actual_sistema))
                band = true;//aun se puede registar
            if (fecha_cita_medica.isAfter(fecha_actual_sistema))
                band = true;//aun se puede registar

        }



        return band;
    }

    public static Boolean validarCitasSimultaneasServicios(String fecha_cita, String hora_cita, String identificacion, List<Cita> citas){

        //si retorna FALSE no existe  ; TRUE ya existe
        boolean band = true;//SI existen coincidencias
        int count_citas = 0;
        for (Cita cita : citas) {
            boolean doc_identificacion = cita.getDocumento().trim().equals(identificacion.trim());
            LocalDateTime fecha_actual_cita = LocalDateTime.parse(( fecha_cita.trim()+" "+hora_cita.trim() ), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            LocalDateTime fecha_agendadas = LocalDateTime.parse(( cita.getFecha().trim()+" "+cita.getHora().trim() ), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

            boolean fecha_agenda= fecha_actual_cita.equals(fecha_agendadas);

            if(!doc_identificacion || !fecha_agenda) count_citas++;

        }
        long cantidad_citas = citas.stream().count();
        if(count_citas==cantidad_citas)
            band = false;
        return band;
    }

    public static Boolean validarCitasSimultaneasPorProfesional(String fecha_cita, String hora_cita,String tipo_medico, String especialista,  List<Cita> citas)
    {
        //si retorna FALSE es porque no tienen , TRUE ya tiene una cita y no pude repetir otra simultanea
        boolean band = true;

        int count_citas = 0;
        for (Cita cita : citas) {

            LocalDateTime fecha_actual_cita = LocalDateTime.parse(( fecha_cita.trim()+" "+hora_cita.trim() ), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            LocalDateTime fecha_agendadas = LocalDateTime.parse(( cita.getFecha().trim()+" "+cita.getHora().trim() ), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            boolean fecha_agenda = fecha_actual_cita.equals(fecha_agendadas);

            boolean medico_especialidad = (tipo_medico.trim()+especialista.trim()).equals( (cita.getTipo_cita().trim()+cita.getEspecialidad().trim()) );

            if(!fecha_agenda && !medico_especialidad) count_citas++;

        }
        long cantidad_citas = citas.stream().count();
        if(count_citas==cantidad_citas)
            band = false;

        return band;
    }


    public static Boolean validarCitaEspecialista24hAntes(LocalDateTime fecha_hora, String especialista, TipoMedico tipo_medico){
        //si retorna FALSE es porque la cita no esta 24 horas antes , TRUE si cumple las 24 horas minimo de anticipacion

        boolean band = true;


        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime fecha_hora_actual = LocalDateTime.now();

        // Calcular la duración en horas entre las fechas
        Duration duracion = Duration.between(fecha_hora_actual, fecha_hora);
        long horas_pasadas = duracion.toHours();

        if(especialista.equals(tipo_medico.getGeneral())) band=true;
        if(horas_pasadas<=24 && especialista.equals(tipo_medico.getEspecialista())) band =false;

        return band;
    }

    public static Boolean validarApoderadosPacientes(String tipo_paciente, TipoPaciente tipo_paciente_obj,String fecha_nacimiento_paciente, int cadena , String fecha_nacimiento_apoderado){
        //si retorna TRUE se lo puede registra FALSE no se lo pude registar
        boolean band = true;



        int edad_paciente = calcularEdad(fecha_nacimiento_paciente);
        int edad_apoderado = fecha_nacimiento_apoderado.isEmpty() ? 0 :  calcularEdad(fecha_nacimiento_apoderado);

        if(tipo_paciente.trim().equals(tipo_paciente_obj.getMenor()) && cadena <= 10) band = false;

        if(tipo_paciente.trim().equals(tipo_paciente_obj.getMenor()) && edad_paciente <=18 ) band = false;

        if(tipo_paciente.trim().equals(tipo_paciente_obj.getMenor()) && edad_paciente <=18 && cadena>10) band = true;

        if(tipo_paciente.trim().equals(tipo_paciente_obj.getAdulto()) && edad_paciente >=18) band = true;

        if(tipo_paciente.trim().equals(tipo_paciente_obj.getAdulto()) && edad_paciente <18) band = false;

        if(tipo_paciente.trim().equals(tipo_paciente_obj.getMenor()) && edad_paciente > 18 ) band = false;

        if(edad_apoderado <=18 && cadena>10) band = false;
//
        if(edad_paciente <=18 && edad_apoderado >=18 && cadena>10) band = true;


        return band;
    }

    public static int calcularEdad(String fecha_nacimiento){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // Calcular la edad
        Period edad = Period.between(LocalDate.parse(fecha_nacimiento, formatter), LocalDate.now());

        int edad_anios_paciente = edad.getYears();

        return edad_anios_paciente;
    }

    public static boolean validarUltimaHoraCita(LocalDateTime fecha_hora_cita, List<Horarios> horariosList){
        boolean band = false;


        DateTimeFormatter fomato_hora = DateTimeFormatter.ofPattern("HH:mm");
        String hora_cita = fecha_hora_cita.format(fomato_hora);
        String dia_cita = fecha_hora_cita.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("es", "ES")).toUpperCase().trim();


        for (Horarios horario : horariosList) {

            LocalTime horaFin = LocalTime.parse(horario.getHora_fin());
            // Calcular la diferencia en minutos
            long diferencia_minutos = ChronoUnit.MINUTES.between(LocalTime.parse( hora_cita) , horaFin);

            if ( horario.getDia().equals(dia_cita.toUpperCase()) && diferencia_minutos >= 20)
                band = true;// hora de cita fuera de rango

        }

        return band;
    }
}
