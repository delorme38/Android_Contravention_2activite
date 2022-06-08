package com.delorme.exercise11;

import java.util.ArrayList;

public class AppService {

    private ArrayList<Contravention> contraventions = new ArrayList<>();
    private rvAdapter rvAdapter;

    private static AppService instance = null;

    public static AppService getInstance(){
        if (instance == null) {
            instance = new AppService();
        }
        return instance;
    }

    private AppService() { initAppService();}


    public ArrayList<Contravention> getContraventions(){
        return contraventions;
    }

    public rvAdapter getRvAdapter() {
        return rvAdapter;
    }

    public AppService setRvAdapter(rvAdapter rvAdapter) {
        this.rvAdapter = rvAdapter;
        return this;
    }

    private void initAppService() {

    }
}
