package com.delorme.exercise11;


import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.delorme.exercise11.databinding.ActivityMainBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding _binding;
    private AppService appService;
    private rvAdapter rvAdapter;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        Gson gson = new Gson();
        List<Contravention> arrayList = rvAdapter.getLocalDataSet();
        String json = gson.toJson(arrayList);
        editor.putString("TAG", json);
        editor.commit();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Shared Data
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPrefs.getString("TAG", "");
        Type type = new TypeToken<List<Contravention>>() {}.getType();
        List<Contravention> arrayList = gson.fromJson(json, type);


        appService = AppService.getInstance();

        // Code pour le Binding
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // Code pour l'adapter ContraventionList
        if(appService.getRvAdapter()==null){
            appService.setRvAdapter(new rvAdapter(this));
        }

        rvAdapter = appService.getRvAdapter();
        if(arrayList != null){
            rvAdapter.setLocalDataSet(arrayList);
        } else {
            rvAdapter.setLocalDataSet(new ArrayList<>());
        }
        if (savedInstanceState != null) {

        }

        _binding.rvContravention.setAdapter(rvAdapter);
        _binding.rvContravention.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.rv_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.idMenuAjouter:
                changerPage();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void changerPage() {
        Log.d(TAG, "changerPage: bonjour");
        Intent intent = new Intent(this, MainActivity2.class);

        startActivity(intent);
    }

    public void loadContravention(int pos, Contravention contravention) {
        Intent intent = new Intent (this,  MainActivity2.class);
        intent.putExtra("POS", pos);
        intent.putExtra("Contravention", contravention);
        startActivity(intent);
    }
}