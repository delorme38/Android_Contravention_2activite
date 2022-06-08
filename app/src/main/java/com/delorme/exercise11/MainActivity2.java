package com.delorme.exercise11;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.delorme.exercise11.databinding.ActivityMain2Binding;

public class MainActivity2 extends AppCompatActivity {
    final private String ERREUR = "erreur";
    final private String CONTRAVENTION = "contravention";
    final private String UPDATE = "update";
    final private String FIELDSCOLOR = "fieldscolor";

    private ActivityMain2Binding _binding;
    private Contravention contravention;
    private AppService appService;
    private boolean erreurEntrer = false;
    private boolean update = false;
    private FieldsColor fieldsColor = new FieldsColor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appService = AppService.getInstance();
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_main2);

        Contravention lookIfUpdate = getIntent().getParcelableExtra("Contravention");
        if (lookIfUpdate != null) {
            contravention = lookIfUpdate;
            update = true;
        }else if (savedInstanceState != null){
            erreurEntrer = savedInstanceState.getBoolean(ERREUR);
            update = savedInstanceState.getBoolean(UPDATE);
            contravention = savedInstanceState.getParcelable(CONTRAVENTION);
            fieldsColor = savedInstanceState.getParcelable(FIELDSCOLOR);

        }
        else {
            contravention = new Contravention();
        }
        _binding.setContravention(contravention);
        _binding.setColor(fieldsColor);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {

        savedInstanceState.putParcelable(CONTRAVENTION, _binding.getContravention());
        savedInstanceState.putBoolean(ERREUR, erreurEntrer);
        savedInstanceState.putBoolean(UPDATE, update);
        savedInstanceState.putParcelable(FIELDSCOLOR, fieldsColor);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu, menu);
        if (update) {
            menu.getItem(0).setTitle("UPDATE");
        } else {
            menu.getItem(0).setTitle("ADD");
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.idMenuAdd:
                if (update) {
                    updateContravention();
                }else {
                    addContravention();
                }
                return true;
            case R.id.idMenuClear:
                deleteEntries();
                return true;
            case R.id.idMenuDelete:
                deleteContravention();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateContravention() {
        int pos = getIntent().getIntExtra("POS", -1);
        if (pos != -1 && update && validate())  {
            appService.getRvAdapter().updateContravention(contravention, pos);
            update = false;
            invalidateOptionsMenu();
            onBackPressed();
        }
    }

    private void deleteContravention() {
        if (update) {
            int pos = getIntent().getIntExtra("POS", -1);
            if (pos != -1) {
                appService.getRvAdapter().deleteContravention(pos);
                invalidateOptionsMenu();
                onBackPressed();
            }
        }
    }

    private void addContravention() {
        if (validate()){
            if (!calulerAmende()) {return;}
            appService.getRvAdapter().addContravention(contravention);
            onBackPressed();
        } else {
            erreurEntrer = true;
            showErrorMessage(R.id.idErreurChamp, erreurEntrer);
        }
    }

    private boolean calulerAmende() {

        int iVitesse = Integer.parseInt(contravention.getVitesse());
        int iLimiteVitesse = Integer.parseInt(contravention.getLimiteDeVitesse());
        boolean scolaire = contravention.isScolaireConstruction();
        if (iVitesse < iLimiteVitesse) {
            fieldsColor.limiteVitesse = R.color.red;
            fieldsColor.vitesse = R.color.red;
            ErreurDialog erreurDialog = new ErreurDialog();
            erreurDialog.show(getSupportFragmentManager(), "erreur");
            return false;
        }
        double dAmende = CalculerAmende.calulerMontantAmende(iVitesse, iLimiteVitesse, scolaire);
        contravention.setMontantDeAmende(String.valueOf(dAmende));
        return true;
    }


    private Boolean validate() {
        fieldsColor = new FieldsColor();
        Boolean valid = true;
        String lastName = contravention.getNom();
        String firstName = contravention.getPrenom();
        String vitesse = contravention.getVitesse();
        String limiteVitesse = contravention.getLimiteDeVitesse();


        if(lastName.isEmpty()) {
            fieldsColor.lastName = R.color.red;
            valid = false;
        }
        if(firstName.isEmpty()) {
            fieldsColor.firstName = R.color.red;
            valid = false;
        }
        if(vitesse.isEmpty()) {
            fieldsColor.vitesse = R.color.red;
            valid = false;
        }
        if(limiteVitesse.equals("0")){
            fieldsColor.limiteVitesse = R.color.red;
            valid = false;
        }
        _binding.setColor(fieldsColor);

        return valid;
    }

    public void deleteEntries() {
        contravention = new Contravention();
        _binding.setContravention(contravention);
        erreurEntrer = false;
        showErrorMessage(R.id.idErreurChamp, erreurEntrer);
        update = false;
        invalidateOptionsMenu();
        fieldsColor = new FieldsColor();
        _binding.setColor(fieldsColor);
    }

    private void showErrorMessage(int id, boolean show) {
        View view = findViewById(id);
        if (show) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }
}