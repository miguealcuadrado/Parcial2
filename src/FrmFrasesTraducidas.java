import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.WindowConstants;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


import entidades.Frase;
import entidades.FrasesData;
import entidades.Traduccion;

public class FrmFrasesTraducidas extends JFrame {

    private JComboBox<String> comboFrases;
    private JComboBox<String> comboIdiomas;
    private JTextField txtTraduccion;
    private FrasesData data;

    public FrmFrasesTraducidas(){
        setSize(600, 300);
        setTitle("Traductor de Frases");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel lblFrase = new JLabel("Frase");
        lblFrase.setBounds(20, 10, 100, 50);
        getContentPane().add(lblFrase);

        comboFrases = new JComboBox<>();
        comboFrases.setBounds(90, 20, 400, 25);
        add(comboFrases);

        JLabel lblIdioma = new JLabel("Idioma");
        lblIdioma.setBounds(20, 50, 100, 50);
        getContentPane().add(lblIdioma);

        comboIdiomas = new JComboBox<>();
        comboIdiomas.setBounds(90, 60, 200, 25);
        add(comboIdiomas);

        JButton btnTraducir = new JButton("Traducir");
        btnTraducir.setBounds(20, 110, 85, 25);
        getContentPane().add(btnTraducir);

        String ArchivoImagen = "src/iconos/reproducir.png";
        

        JButton btnReproducirSonido = new JButton("");
        btnReproducirSonido.setBounds(450, 160, 60, 60);
        getContentPane().add(btnReproducirSonido);

        txtTraduccion = new JTextField();
        txtTraduccion.setBounds(20, 160, 400, 60);
        txtTraduccion.setEnabled(true);
        getContentPane().add(txtTraduccion);

        txtTraduccion.setText("");

        cargarDatos();

        btnReproducirSonido.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ReproducirVoz();
            }
        });

        btnTraducir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                traducirFrase();
            }
        });
    } 

    private Icon ImageIcon(URL resource) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'ImageIcon'");
    }

    private void cargarDatos() {
        ObjectMapper objectMapper = new ObjectMapper();
    try{
        String nombreArchivo = System.getProperty("user.dir") 
        + "/src/datos/FrasesTraducidas.json";
        data = objectMapper.readValue(new File(nombreArchivo), FrasesData.class);

        for (Frase frase : data.getFrases()) {
            comboFrases.addItem(frase.getTexto());
        }

        List<String> idiomasUnicos = data.getFrases().stream()
                    .flatMap(frase -> frase.getTraducciones().stream())
                    .map(Traduccion::getIdioma)
                    .distinct()
                    .sorted()
                    .collect(Collectors.toList());

            for (String idioma : idiomasUnicos) {
                comboIdiomas.addItem(idioma);
    }

} catch (Exception e) {
    JOptionPane.showMessageDialog(this, "Error al cargar el archivo JSON: " + e.getMessage());
    }
}

private void traducirFrase() {
    String fraseSeleccionada = (String) comboFrases.getSelectedItem();
    String idiomaSeleccionado = (String) comboIdiomas.getSelectedItem();

    if (fraseSeleccionada == null || idiomaSeleccionado == null) {
        JOptionPane.showMessageDialog(this, "Por favor, selecciona una frase y un idioma.");
        return;
    }

    for (Frase frase : data.getFrases()) {
        if (frase.getTexto().equals(fraseSeleccionada)) {
            for (Traduccion traduccion : frase.getTraducciones()) {
                if (traduccion.getIdioma().equalsIgnoreCase(idiomaSeleccionado)) {
                    txtTraduccion.setText(traduccion.getTextoTraducido());
                    return;
                }
            }
        }
    }

    txtTraduccion.setText("Traducción no disponible");
}



private void ReproducirVoz() {
    if (txtTraduccion.getText().equals("")) {
        JOptionPane.showMessageDialog(this, "Por favor oprima primero el botón traducir");
        

    }else {
        
        String fraseSeleccionada = (String) comboFrases.getSelectedItem();
        String idiomaSeleccionado = (String) comboIdiomas.getSelectedItem();

        if (fraseSeleccionada.equals("Hola")) {
            
            String ArchivoSonido = "src/audiosfrases/" + fraseSeleccionada + "-" + idiomaSeleccionado + ".mp3";
            File archivo = new File(ArchivoSonido);

            if (! archivo.exists()) {
                JOptionPane.showMessageDialog(null, "Lo sentimos, el audio no se encuentra disponible");

            }else {
                ReproductorAudio.reproducir(ArchivoSonido);
            }

            
        }else if (fraseSeleccionada.equals("Buenos días")) {
            String Correcion = "BuenosDias";

            String ArchivoSonido = "src/audiosfrases/" + Correcion + "-" + idiomaSeleccionado + ".mp3";
            File archivo = new File(ArchivoSonido);

            if(! archivo.exists()) {
                JOptionPane.showMessageDialog(null, "Lo sentimos, el audio no se encuentra disponible");

            }else {
                ReproductorAudio.reproducir(ArchivoSonido);
            }


        }else if (fraseSeleccionada.equals("Buenas Tardes")) {
            String Correcion = "BuenasTardes";

            String ArchivoSonido = "src/audiosfrases/" + Correcion + "-" + idiomaSeleccionado + ".mp3";
            File archivo = new File(ArchivoSonido);

            if(! archivo.exists()) {
                JOptionPane.showMessageDialog(null, "Lo sentimos, el audio no se encuentra disponible");

            }else {
                ReproductorAudio.reproducir(ArchivoSonido);
            }


        }else if (fraseSeleccionada.equals("Buenas Noches")) {
            String Correcion = "BuenasNoches";

            String ArchivoSonido = "src/audiosfrases/" + Correcion + "-" + idiomaSeleccionado + ".mp3";
            File archivo = new File(ArchivoSonido);

            if(! archivo.exists()) {
                JOptionPane.showMessageDialog(null, "Lo sentimos, el audio no se encuentra disponible");

            }else {
                ReproductorAudio.reproducir(ArchivoSonido);
            }
            
        }else if(fraseSeleccionada.equals("Cómo estas?")) {
            String Correcion = "ComoEstas";

            String ArchivoSonido = "src/audiosfrases/" + Correcion + "-" + idiomaSeleccionado + ".mp3";
            File archivo = new File(ArchivoSonido);

            if (! archivo.exists()) {
                JOptionPane.showMessageDialog(null, "Lo sentimos, el audio no se encuentra disponible.");

            }else {
                ReproductorAudio.reproducir(ArchivoSonido);
            }

            
        }else if (fraseSeleccionada.equals("Cómo te llamas?")) {
            String Correcion = "ComoTeLlamas";

            String ArchivoSonido = "src/audiosfrases/" + Correcion + "-" + idiomaSeleccionado + ".mp3";
            File archivo = new File(ArchivoSonido);

            if (! archivo.exists()) {
                JOptionPane.showMessageDialog(null, "Lo sentimos, el audio no se encuentra disponible");

            }else {
                ReproductorAudio.reproducir(ArchivoSonido);
            }

            
        }else if (fraseSeleccionada.equals("De dónde vienes?")) {
            String Correcion = "DeDondeVienes";

            String ArchivoSonido = "src/audiosfrases/" + Correcion + "-" + idiomaSeleccionado + ".mp3";
            File archivo = new File(ArchivoSonido);

            if(! archivo.exists()) {
                JOptionPane.showMessageDialog(null, "Lo sentimos. el audio no se encuentra disponible.");

            }else {
                ReproductorAudio.reproducir(ArchivoSonido);
            }


        }    
    }

}


}

