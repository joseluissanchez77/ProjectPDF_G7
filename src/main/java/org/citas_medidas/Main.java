package org.citas_medidas;

import org.citas_medidas.entidades.TipoPaciente;
import org.citas_medidas.entidades.TipoPacienteWrapper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {

//        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // El valor de nombreProyecto contendrá la ruta completa del proyecto
            String nombreProyecto = new File("").getAbsolutePath();
            String rutaRelativaProyecto = nombreProyecto.replace("\\", "/");
            String rutaDiccionario = rutaRelativaProyecto
                    + "/src/main/java/org/citas_medidas/diccionario/diccionario.txt";

            File archivo = new File(rutaDiccionario);

            if (!archivo.exists())
                detenerEjecucion("El archivo [diccionario.txt] no existe");

            StringBuilder contenido = new StringBuilder();
            BufferedReader lector = new BufferedReader(new FileReader(rutaDiccionario));
            String linea;

            while ((linea = lector.readLine()) != null) {
                contenido.append(linea);
            }

            lector.close();

            String contenidoComoString = contenido.toString();
            String tipoPaciente = extraerTipoPaciente(contenidoComoString, "tipo_paciente");

            if (tipoPaciente == null)
                detenerEjecucion("No se encontró el valor de 'tipo_paciente' en el archivo.");

            TipoPaciente tipoPacienteObj = new TipoPaciente();
            tipoPacienteObj.setTipo(tipoPaciente);

            System.out.println(tipoPacienteObj.getTipo().split(",")[0]);
            System.out.println(tipoPacienteObj.getTipo().split(",")[1]);
//            List<TipoPacienteWrapper> listaTipoPacientes = new ArrayList<>();
//            TipoPacienteWrapper wrapper = new TipoPacienteWrapper();
//            List<TipoPaciente> listaTipoPaciente = new ArrayList<>();
//            listaTipoPaciente.add(tipoPacienteObj);
//            wrapper.setTipoPaciente(listaTipoPaciente);
//
//            listaTipoPacientes.add(wrapper);
//            System.out.println("Tipo de pacientes:");
//
//            for (TipoPaciente tipoPaciente1 : wrapper.getTipoPaciente()) {
//                System.out.println(tipoPaciente1.getTipo());
//            }

//            TipoPaciente tipoPacienteObj = new TipoPaciente();
//            tipoPacienteObj.setTipo(tipoPaciente);
//
//            TipoPacienteWrapper wrapper = new TipoPacienteWrapper();
//            List<TipoPaciente> listaTipoPacientes = new ArrayList<>();
//            listaTipoPacientes.add(tipoPacienteObj);
//            wrapper.setTipoPaciente(listaTipoPacientes);
//
//            listaTipoPacientes.add(wrapper);
//
//             for (TipoPaciente tipoPacienteFor : listaTipoPacientes) {
//             System.out.println("Tipo de paciente: " + tipoPacienteFor.getTipo().[0]);
//             }

//            TipoPacienteWrapper wrapper = new TipoPacienteWrapper();
//            List<TipoPaciente> listaTipoPacientes = wrapper.getTipoPaciente();
//            wrapper.setTipoPaciente(listaTipoPacientes);
//            System.out.println(tipoPaciente);
//
//            System.out.println("Tipo de paciente: " + tipoPaciente);

//            Path rutaArchivo = Paths.get(rutaDiccionario);
            // List<String> lineas = Files.readAllLines(rutaArchivo);
            // for (String linea : lineas) {
            // System.out.println(linea);
            // }

            // TipoPacienteWrapper wrapper = objectMapper.readValue(new
            // File(rutaDiccionario), TipoPacienteWrapper.class);
            // List<TipoPaciente> listaTipoPacientes = wrapper.getTipoPaciente();
            //
            // for (TipoPaciente tipoPaciente : listaTipoPacientes) {
            // System.out.println("Tipo de paciente: " + tipoPaciente.getTipo());
            // }

        } catch (MensageErrorException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String detenerEjecucion(String $mensaje) throws MensageErrorException {
        throw new MensageErrorException("Error : " + $mensaje);
    }

    public static String extraerTipoPaciente(String contenido, String clave) {
        String tipoPaciente = null;
        String patternString = "\"" + clave + "\": \\[(.*?)\\]";

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(contenido);

        if (matcher.find()) {
            tipoPaciente = matcher.group(1);
        }

        return tipoPaciente;
    }
}

class MensageErrorException extends Exception {
    public MensageErrorException(String mensaje) {
        super(mensaje);
    }
}