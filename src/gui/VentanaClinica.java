package gui;

import gestor.GestorClinica;
import modelo.Paciente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class VentanaClinica extends JFrame {

    private GestorClinica gestor;

    private JTextField txtRut;
    private JTextField txtNombre;
    private JTextField txtEdad;
    private JComboBox<String> cmbTipoConsulta;
    private JRadioButton rbMasculino;
    private JRadioButton rbFemenino;
    private ButtonGroup bgGenero;
    private JCheckBox chkFonasa;
    private JCheckBox chkIsapre;
    private JCheckBox chkParticular;

    private JTextArea txtAreaLista;

    private JButton btnGuardar;
    private JButton btnListar;
    private JButton btnLimpiar;

    public VentanaClinica() {
        gestor = new GestorClinica();

        setTitle("Sistema de Gestión de Clínica - CFT Los Ríos");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        crearPanelTitulo();
        crearPanelFormulario();
        crearPanelBotones();
        crearPanelListado();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                gestor.guardarDatos();
            }
        });

        actualizarListado();
    }

    private void crearPanelTitulo() {
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(41, 128, 185));
        panelTitulo.setPreferredSize(new Dimension(0, 60));

        JLabel lblTitulo = new JLabel("SISTEMA DE GESTIÓN DE PACIENTES");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo);

        add(panelTitulo, BorderLayout.NORTH);
    }

    private void crearPanelFormulario() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel panelFormulario = new JPanel(new GridLayout(7, 2, 10, 15));
        panelFormulario.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(41, 128, 185), 2),
                "Datos del Paciente",
                0, 0,
                new Font("Arial", Font.BOLD, 14),
                new Color(41, 128, 185)));

        panelFormulario.add(new JLabel("RUT:"));
        txtRut = new JTextField();
        panelFormulario.add(txtRut);

        panelFormulario.add(new JLabel("Nombre Completo:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Edad:"));
        txtEdad = new JTextField();
        panelFormulario.add(txtEdad);

        panelFormulario.add(new JLabel("Tipo de Consulta:"));
        String[] tiposConsulta = { "Medicina General", "Pediatría", "Traumatología", "Dermatología" };
        cmbTipoConsulta = new JComboBox<>(tiposConsulta);
        panelFormulario.add(cmbTipoConsulta);

        panelFormulario.add(new JLabel("Género:"));
        JPanel panelGenero = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rbMasculino = new JRadioButton("Masculino", true);
        rbFemenino = new JRadioButton("Femenino");
        bgGenero = new ButtonGroup();
        bgGenero.add(rbMasculino);
        bgGenero.add(rbFemenino);
        panelGenero.add(rbMasculino);
        panelGenero.add(rbFemenino);
        panelFormulario.add(panelGenero);

        panelFormulario.add(new JLabel("Previsión:"));
        JPanel panelPrevision = new JPanel(new FlowLayout(FlowLayout.LEFT));
        chkFonasa = new JCheckBox("Fonasa");
        chkIsapre = new JCheckBox("Isapre");
        chkParticular = new JCheckBox("Particular");
        panelPrevision.add(chkFonasa);
        panelPrevision.add(chkIsapre);
        panelPrevision.add(chkParticular);
        panelFormulario.add(panelPrevision);

        panelFormulario.add(new JLabel(""));
        panelFormulario.add(new JLabel(""));

        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);
        add(panelPrincipal, BorderLayout.CENTER);
    }

    private void crearPanelBotones() {
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));
        panelBotones.setPreferredSize(new Dimension(180, 0));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 20));

        Dimension tamañoBoton = new Dimension(150, 40);
        Font fuenteBoton = new Font("Arial", Font.BOLD, 12);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setPreferredSize(tamañoBoton);
        btnGuardar.setFont(fuenteBoton);
        btnGuardar.setBackground(new Color(46, 204, 113));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFocusPainted(false);
        btnGuardar.addActionListener(e -> guardarPaciente());

        btnListar = new JButton("Listar");
        btnListar.setPreferredSize(tamañoBoton);
        btnListar.setFont(fuenteBoton);
        btnListar.setBackground(new Color(52, 152, 219));
        btnListar.setForeground(Color.WHITE);
        btnListar.setFocusPainted(false);
        btnListar.addActionListener(e -> actualizarListado());

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setPreferredSize(tamañoBoton);
        btnLimpiar.setFont(fuenteBoton);
        btnLimpiar.setBackground(new Color(241, 196, 15));
        btnLimpiar.setForeground(Color.WHITE);
        btnLimpiar.setFocusPainted(false);
        btnLimpiar.addActionListener(e -> limpiarCampos());

        panelBotones.add(btnGuardar);
        panelBotones.add(btnListar);
        panelBotones.add(btnLimpiar);

        add(panelBotones, BorderLayout.EAST);
    }

    private void crearPanelListado() {
        JPanel panelListado = new JPanel(new BorderLayout());
        panelListado.setPreferredSize(new Dimension(0, 200));
        panelListado.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        JLabel lblListado = new JLabel("Listado de Pacientes Registrados:");
        lblListado.setFont(new Font("Arial", Font.BOLD, 12));
        panelListado.add(lblListado, BorderLayout.NORTH);

        txtAreaLista = new JTextArea();
        txtAreaLista.setEditable(false);
        txtAreaLista.setFont(new Font("Monospaced", Font.PLAIN, 11));
        txtAreaLista.setBackground(new Color(245, 245, 245));

        JScrollPane scrollPane = new JScrollPane(txtAreaLista);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panelListado.add(scrollPane, BorderLayout.CENTER);

        add(panelListado, BorderLayout.SOUTH);
    }

    private void guardarPaciente() {
        if (txtRut.getText().trim().isEmpty() ||
                txtNombre.getText().trim().isEmpty() ||
                txtEdad.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Por favor, complete todos los campos obligatorios (RUT, Nombre, Edad)",
                    "Campos Incompletos",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String rut = txtRut.getText().trim();
            String nombre = txtNombre.getText().trim();

            if (gestor.existeRut(rut)) {
                JOptionPane.showMessageDialog(this,
                        "El RUT " + rut + " ya está registrado en el sistema",
                        "RUT Duplicado",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            String[] palabras = nombre.split("\\s+");
            if (palabras.length < 2) {
                JOptionPane.showMessageDialog(this,
                        "El nombre debe incluir al menos nombre y apellido",
                        "Nombre Incompleto",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (nombre.matches(".*\\d.*")) {
                JOptionPane.showMessageDialog(this,
                        "El nombre no puede contener números",
                        "Nombre Inválido",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            int edad = Integer.parseInt(txtEdad.getText().trim());
            String tipoConsulta = (String) cmbTipoConsulta.getSelectedItem();
            String genero = rbMasculino.isSelected() ? "Masculino" : "Femenino";

            StringBuilder prevision = new StringBuilder();
            if (chkFonasa.isSelected())
                prevision.append("Fonasa ");
            if (chkIsapre.isSelected())
                prevision.append("Isapre ");
            if (chkParticular.isSelected())
                prevision.append("Particular ");

            String previsionFinal = prevision.length() > 0 ? prevision.toString().trim() : "Sin Previsión";

            Paciente paciente = new Paciente(rut, nombre, edad, tipoConsulta, genero, previsionFinal);

            gestor.agregarPaciente(paciente);

            JOptionPane.showMessageDialog(this,
                    "Paciente guardado exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

            limpiarCampos();
            actualizarListado();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "La edad debe ser un número válido",
                    "Error de Formato",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarListado() {
        ArrayList<Paciente> pacientes = gestor.listarPacientes();
        txtAreaLista.setText("");

        if (pacientes.isEmpty()) {
            txtAreaLista.append("No hay pacientes registrados.\n");
        } else {
            txtAreaLista.append("═══════════════════════════════════════════════════════════════════════════════\n");
            txtAreaLista.append("                          PACIENTES REGISTRADOS\n");
            txtAreaLista.append("═══════════════════════════════════════════════════════════════════════════════\n\n");

            int contador = 1;
            for (Paciente p : pacientes) {
                txtAreaLista.append(String.format("%d. %s\n", contador++, p.toString()));
            }

            txtAreaLista.append("\n═══════════════════════════════════════════════════════════════════════════════\n");
            txtAreaLista.append(String.format("Total de pacientes: %d\n", pacientes.size()));
        }

        txtAreaLista.setCaretPosition(0);
    }

    private void limpiarCampos() {
        txtRut.setText("");
        txtNombre.setText("");
        txtEdad.setText("");
        cmbTipoConsulta.setSelectedIndex(0);
        rbMasculino.setSelected(true);
        chkFonasa.setSelected(false);
        chkIsapre.setSelected(false);
        chkParticular.setSelected(false);
        txtRut.requestFocus();
    }
}
