package org.citas_medidas.excepciones;

public class MensageErrorException extends Exception{

    public MensageErrorException() {
    }

    public MensageErrorException(String mensaje) {
        super(mensaje);
    }
    public static String detenerEjecucion(String $mensaje) throws MensageErrorException {
        throw new MensageErrorException("Error : " + $mensaje);
    }
}
