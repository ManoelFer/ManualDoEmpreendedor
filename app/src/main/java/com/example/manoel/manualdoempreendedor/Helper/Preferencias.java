package com.example.manoel.manualdoempreendedor.Helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Manoel on 11/06/2018.
 */

public class Preferencias {

    private Context context;
    private SharedPreferences preferences;
    private String Nome_Arquivo = "projetoFirebase.preferencias";
    private int MODE = 0;
    private SharedPreferences.Editor editor;

    private final String CHAVE_IDENTIFICADOR = "identificarUsuarioLogado";
    private final String CHAVE_NOME = "nomeUsuarioLogado";

    public Preferencias (Context context)  {
        this.context = context;
        preferences = context.getSharedPreferences(Nome_Arquivo, MODE);

        editor = preferences.edit();
    }

    public void salvarUsuario(String idenficadorUsuario, String nomeUsuario){

        editor.putString(CHAVE_IDENTIFICADOR, idenficadorUsuario);
        editor.putString(CHAVE_NOME, nomeUsuario);
        editor.commit();

    }

    public String getIdentificador(){
        return preferences.getString(CHAVE_IDENTIFICADOR, null);
    }

    public String getNome(){
        return preferences.getString(CHAVE_NOME, null);
    }
}
