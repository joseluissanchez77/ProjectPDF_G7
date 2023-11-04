package org.citas_medidas;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.citas_medidas.entidades.*;
import org.citas_medidas.excepciones.MensageErrorException;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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

            //========================================================
            //leer y asignar el tipo de paciente al objeto
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

            //---------------------------------------------------------
            //leer y asignar el tipo de documento al objeto
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

            //leer y asignar feriado al objeto
            ArrayNode feriadosNode = (ArrayNode) contenidoDicc.get("feriados");

            if (feriadosNode == null)
                MensageErrorException.detenerEjecucion("No se encontró el valor de 'feriados' en el archivo.");

            Feriados feriadosObj = new Feriados();
            feriadosObj.setFecha_feriado(feriadosNode);

            List<String> feriadoList = feriadosObj.getFecha_feriado();
            for (String valor : feriadoList) {
                System.out.println("Feriado "+valor);
            }

            //leer y asignar el tipo de medico
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


            //leer y asignar la espacialidad
            ArrayNode especialidadNode = (ArrayNode) contenidoDicc.get("especialidad");

            if (especialidadNode == null)
                MensageErrorException.detenerEjecucion("No se encontró el valor de 'especialidad' en el archivo.");

            Especialidad especialidadObj = new Especialidad();
            especialidadObj.setEspecialidad(especialidadNode);

            List<String> especialidadList = especialidadObj.getEspecialidad();
            for (String valor : especialidadList) {
                System.out.println("Espacialidad:  "+valor);
            }

            //leer y asignar horarios
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
            //=====================================================================0
            System.out.println(tipoPacienteObj.getAdulto());
            System.out.println(tipoDocumentoObj.getCedula());
            System.out.println(tipoMedicoObj.getEspecialista());
            for (Horarios horario : horariosList) {
                System.out.println("Día: " + horario.getDia());
                System.out.println("Horario de inicio: " + horario.getHora_incio());
                System.out.println("Horario de fin: " + horario.getHora_fin());
            }


            System.out.println("====COMIENZA A LEER EL TXT====");

        } catch (MensageErrorException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String extraerDatosDiccionario(String contenido, String clave) {
        String cadena_buscada = null;
        String patternString = "\"" + clave + "\": \\[(.*?)\\]";

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(contenido);

        if (matcher.find()) {
            cadena_buscada = matcher.group(1);
        }

        return cadena_buscada;
    }
}

