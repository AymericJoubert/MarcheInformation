package mapping;

import java.util.Date;
import java.util.ArrayList;
public class Market {

    private int marcheId, createur, inverse;
    private String question;
    private Date ouverture, fermeture;
    private ArrayList<Offre> offres;

    public Market(){
        marcheId = 0;
        createur = 0;
        inverse = 0;
        question = null;
        ouverture = null;
        fermeture = null;
        offres = new ArrayList<Offre>();
    }

    public ArrayList<Offre> getOffres(){
        return offres;
    }

    public void setOffres(ArrayList<Offre> offres){
        this.offres = offres;
    }

    public int getMarcheId(){
    	return marcheId;
    }

    public void setMarcheId(int m){
    	marcheId = m;
    }

    public int getCreateur(){
    	return createur;
    }

    public void setCreateur(int c){
    	createur = c;
    }

    public int getInverse(){
    	return inverse;
    }

    public void setInverse(int i){
    	inverse = i;
    }

    public String getQuestion(){
	return question;
    }

    public void setQuestion(String q){
	question = q;
    }

    public Date getOuverture(){
    	return ouverture;
    }

    public void setOuverture(Date o){
    	ouverture = o;
    }

    public Date getFermeture(){
    	return fermeture;
    }

    public void setFermeture(Date f){
    	fermeture = f;
    }
}