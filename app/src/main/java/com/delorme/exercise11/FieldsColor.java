package com.delorme.exercise11;

import android.os.Parcel;
import android.os.Parcelable;

public class FieldsColor implements Parcelable {
    public int firstName = R.color.black;
    public int lastName = R.color.black;
    public int vitesse = R.color.black;
    public int limiteVitesse = R.color.black;

    public FieldsColor(){}

    protected FieldsColor(Parcel in) {
        firstName = in.readInt();
        lastName = in.readInt();
        vitesse = in.readInt();
        limiteVitesse = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(firstName);
        dest.writeInt(lastName);
        dest.writeInt(vitesse);
        dest.writeInt(limiteVitesse);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FieldsColor> CREATOR = new Creator<FieldsColor>() {
        @Override
        public FieldsColor createFromParcel(Parcel in) {
            return new FieldsColor(in);
        }

        @Override
        public FieldsColor[] newArray(int size) {
            return new FieldsColor[size];
        }
    };
}
