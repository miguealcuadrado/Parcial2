import javazoom.jl.player.*;
import java.io.*;
import javax.swing.*;

public class ReproductorAudio {

    private static Player reproductor;

    public static void detener() {
        if (reproductor != null) {
            reproductor.close();
            reproductor=null;
        }
    }

    public static void reproducir(String nombreArchivo) {
        detener();
        try {
            FileInputStream fis = new FileInputStream(nombreArchivo);
            BufferedInputStream bis = new BufferedInputStream(fis);
            reproductor = new Player(bis);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(new JFrame(), e);
        }

        new Thread() {

            public void run() {
                try {
                    reproductor.play();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(new JFrame(), e);
                }
            }
        }.start();
    }
}

