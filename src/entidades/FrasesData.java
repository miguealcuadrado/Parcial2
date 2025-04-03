package entidades;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;


public class FrasesData {

    @JsonProperty("Frases")
    private List<Frase> frases;

    public List<Frase> getFrases() {
        return frases;
    }

    public void setFrases(List<Frase> Frases) {
        this.frases = Frases;
    }
}
