package modelo;

import java.io.Serializable;

public class Paciente implements Serializable {
    private static final long serialVersionUID = 1L;

    private String rut;
    private String nombre;
    private int edad;
    private String tipoConsulta;
    private String genero;
    private String prevision;

    public Paciente(String rut, String nombre, int edad, String tipoConsulta,
            String genero, String prevision) {
        this.rut = rut;
        this.nombre = nombre;
        this.edad = edad;
        this.tipoConsulta = tipoConsulta;
        this.genero = genero;
        this.prevision = prevision;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getTipoConsulta() {
        return tipoConsulta;
    }

    public void setTipoConsulta(String tipoConsulta) {
        this.tipoConsulta = tipoConsulta;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getPrevision() {
        return prevision;
    }

    public void setPrevision(String prevision) {
        this.prevision = prevision;
    }

    @Override
    public String toString() {
        return String.format("RUT: %s | Nombre: %s | Edad: %d | Consulta: %s | Género: %s | Previsión: %s",
                rut, nombre, edad, tipoConsulta, genero, prevision);
    }
}
