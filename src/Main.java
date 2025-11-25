import gui.VentanaClinica;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaClinica ventana = new VentanaClinica();
            ventana.setVisible(true);
        });
    }
}
