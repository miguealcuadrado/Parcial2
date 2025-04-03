package entidades;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Traduccion {

    @JsonProperty("Idioma")
    private String idioma;

    @JsonProperty("TextoTraducido")
    private String textoTraducido;
    
    public String getIdioma() {
        return idioma;
    }
    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }
    
    public String getTextoTraducido() {
        return textoTraducido;
    }
    public void setTextoTraducido(String textoTraducido) {
        this.textoTraducido = textoTraducido;
    }

}
