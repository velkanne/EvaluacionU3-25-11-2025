package gestor;

import modelo.Paciente;
import java.io.*;
import java.util.ArrayList;

public class GestorClinica {
    private ArrayList<Paciente> pacientes;
    private static final String ARCHIVO_DATOS = "pacientes.dat";

    public GestorClinica() {
        pacientes = new ArrayList<>();
        cargarDatos();
    }

    public void agregarPaciente(Paciente paciente) {
        pacientes.add(paciente);
        guardarDatos();
    }

    public ArrayList<Paciente> listarPacientes() {
        return pacientes;
    }

    public void guardarDatos() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(ARCHIVO_DATOS))) {
            oos.writeObject(pacientes);
            System.out.println("Datos guardados exitosamente");
        } catch (IOException e) {
            System.err.println("Error al guardar datos: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void cargarDatos() {
        File archivo = new File(ARCHIVO_DATOS);
        if (archivo.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream(ARCHIVO_DATOS))) {
                pacientes = (ArrayList<Paciente>) ois.readObject();
                System.out.println("Datos cargados: " + pacientes.size() + " pacientes");
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error al cargar datos: " + e.getMessage());
                pacientes = new ArrayList<>();
            }
        } else {
            System.out.println("No hay datos previos. Iniciando con lista vacía.");
        }
    }

    public int cantidadPacientes() {
        return pacientes.size();
    }

    public boolean existeRut(String rut) {
        for (Paciente p : pacientes) {
            if (p.getRut().equalsIgnoreCase(rut.trim())) {
                return true;
            }
        }
        return false;
    }
}
