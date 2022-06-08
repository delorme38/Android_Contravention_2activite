package com.delorme.exercise11;

public class CalculerAmende {

    public static double calulerMontantAmende(int vitesse, int limite, boolean zoneScolaire) {
        int vitesseAuDessus = 0;
        int valeurMultiplicateur = 0;
        final int FRAISINITIAL = 15;
        final int trancheDeKM = 5;
        int facteurGrandeVitesse;
        int montantAmende = 0;

        if (vitesse > limite) {
            vitesseAuDessus = vitesse - limite;

            if (vitesseAuDessus <= 10) {
                valeurMultiplicateur = 5;
            } else if (vitesseAuDessus <= 20) {
                valeurMultiplicateur = 10;
            } else if (vitesseAuDessus <= 30) {
                valeurMultiplicateur = 15;
            } else if (vitesseAuDessus <= 45) {
                valeurMultiplicateur = 20;
            } else if (vitesseAuDessus <= 60) {
                valeurMultiplicateur = 25;
            } else {
                valeurMultiplicateur = 30;
            }

            if (limite > 1 && limite <= 60 && vitesseAuDessus >= 40) {
                facteurGrandeVitesse = 2;
            } else if (limite >= 61 && limite <= 99 && vitesseAuDessus >= 50) {
                facteurGrandeVitesse = 2;
            } else if (limite >= 100 && vitesseAuDessus >= 60) {
                facteurGrandeVitesse = 2;
            } else {
                facteurGrandeVitesse = 1;
            }
            montantAmende = (int) (facteurGrandeVitesse * (FRAISINITIAL + (valeurMultiplicateur * Math.floor(vitesseAuDessus / trancheDeKM))));
            if (zoneScolaire) {
                montantAmende = montantAmende * 2;
            }
        }
        return montantAmende;
    }
}