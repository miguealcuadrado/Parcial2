package entidades;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Frase {

    @JsonProperty("Texto")
    private String texto;

    @JsonProperty("Traducciones")
    private List <Traduccion> traducciones;

    public List<Traduccion> getTraducciones() {
        return traducciones;
    }

    public String getTexto(){
        return texto;
    }

    public void setTexto(String texto){
        this.texto = texto;
    }

    public void setTraducciones(List <Traduccion>traducciones){
        this.traducciones = traducciones;
    }
}
