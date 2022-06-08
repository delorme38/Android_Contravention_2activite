package com.delorme.exercise11;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Contravention extends BaseObservable implements Parcelable {
    static final String _STRFORMAT = "yyyy-MM-dd";
    private String nom = "";
    private String prenom = "";
    private double montantDeAmende = 0;
    private int vitesse = 0;
    private int limiteDeVitesse = 0;
    private boolean scolaireConstruction = false;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat(_STRFORMAT);
    private Date date = new Date();

    public Contravention() {}

    protected Contravention(Parcel in) {
        nom = in.readString();
        prenom = in.readString();
        montantDeAmende = in.readDouble();
        vitesse = in.readInt();
        limiteDeVitesse = in.readInt();
        scolaireConstruction = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nom);
        dest.writeString(prenom);
        dest.writeDouble(montantDeAmende);
        dest.writeInt(vitesse);
        dest.writeInt(limiteDeVitesse);
        dest.writeByte((byte) (scolaireConstruction ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Contravention> CREATOR = new Creator<Contravention>() {
        @Override
        public Contravention createFromParcel(Parcel in) {
            return new Contravention(in);
        }

        @Override
        public Contravention[] newArray(int size) {
            return new Contravention[size];
        }
    };

    @Bindable
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
        notifyPropertyChanged(BR.nom);
    }

    @Bindable
    public String getPrenom() {
        return prenom;
    }


    public void setPrenom(String prenom) {
        this.prenom = prenom;

        notifyPropertyChanged(BR.prenom);
    }

    @Bindable
    public String getMontantDeAmende() {
        if (montantDeAmende == 0) {
            return "";
        }
        return String.valueOf(montantDeAmende);
    }


    public void setMontantDeAmende(String montantDeAmende) {
        if (montantDeAmende.equals("")){
            this.montantDeAmende = 0;
        } else {
            this.montantDeAmende = Double.parseDouble(montantDeAmende);
        }
        notifyPropertyChanged(BR.montantDeAmende);
    }

    @Bindable
    public String getVitesse() {
        if (vitesse == 0) {
            return "";
        }
        return String.valueOf(vitesse);
    }

    public void setVitesse(String vitesse) {
        try {
            this.vitesse = Integer.parseInt(vitesse);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        notifyPropertyChanged(BR.vitesse);
    }

    @Bindable
    public String getLimiteDeVitesse() {
        return String.valueOf(limiteDeVitesse);
    }

    public void setLimiteDeVitesse(String limiteDeVitesse) {
        if (limiteDeVitesse.equalsIgnoreCase("select")) {
            return;
        }
        this.limiteDeVitesse = Integer.parseInt(limiteDeVitesse);
        notifyPropertyChanged(BR.limiteDeVitesse);
    }

    @Bindable
    public boolean isScolaireConstruction() {
        return scolaireConstruction;
    }

    public void setScolaireConstruction(boolean scolaireConstruction) {
        this.scolaireConstruction = scolaireConstruction;
        notifyPropertyChanged(BR.scolaireConstruction);
    }


    public String getDateFormat() {
        return dateFormat.format(date);
    }


}
