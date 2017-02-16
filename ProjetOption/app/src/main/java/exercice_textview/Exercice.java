package exercice_textview;

/**
 * Created by aurelienzakowic on 16/02/2017.
 */

public class Exercice {

    private int id;
    private String enonce;
    private String reponse;
    private float difficulte;

    public Exercice() {}

    public Exercice(String enonce, String reponse, float difficulte,int id) {
        this.enonce=enonce;
        this.reponse=reponse;
        this.difficulte=difficulte;
        this.id = id;
    }

    public int getId() {
        return  this.id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getEnonce() {
        return this.enonce;
    }
    public void setEnonce(String enonce) {
        this.enonce=enonce;
    }
    public String getReponse(){
        return this.reponse;
    }
    public void setReponse(String reponse) {
        this.reponse=reponse;
    }
    public float getDifficulte() {
        return this.difficulte;
    }
    public void setDifficulte(float difficulte){
        this.difficulte=difficulte;
    }

    public String toString() {

        return this.enonce +" , Difficulte :  "+ this.difficulte;

    }





}
