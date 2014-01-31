package mapping;

import java.util.Calendar;
import java.text.SimpleDateFormat;

public class Offre{

    private int offreId, valeur, quantite, marche;
    private String acheteur,acheteurInverse;
    private Calendar offreDate;

    public Offre(){
        offreId = 0;
        valeur = 0;
        quantite = 0;
        marche = 0;
        acheteur = null;
        acheteurInverse = null;
        offreDate = null;
    }

    public int getOffreId(){
	return offreId;
    }

    public void setOffreId(int id){
	offreId = id;
    }

    public int getValeur(){
	return valeur;
    }

    public void setValeur(int val){
        valeur = val;
    }

    public int getQuantite(){
        return quantite;
    }

    public void setQuantite(int qute){
        quantite = qute;
    }

    public int getMarche(){
        return marche;
    }

    public void setMarche(int id){
        marche = id;
    }

    public String getAcheteur(){
        return acheteur;
    }

    public void setAcheteur(String acheteur){
        this.acheteur = acheteur;
        
    }

    public String getAcheteurInverse(){
        return acheteurInverse;
    }

    public void setAcheteurInverse(String acheteur){
        acheteurInverse = acheteur;
    }

    public Calendar getOffreDate(){
        return offreDate;
    }

    public void setOffreDate(Calendar date){
        offreDate = date;
    }

    public String toStringDate(){
        SimpleDateFormat dsf = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        return dsf.format(offreDate.getTime());
    }
}