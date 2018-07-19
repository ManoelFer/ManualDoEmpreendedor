package com.example.manoel.manualdoempreendedor.Entidades;

import com.example.manoel.manualdoempreendedor.DAO.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Formulario_5w2h {
    private String what;
    private String why;
    private String who;
    private String when;
    private String where;
    private String how;

    public Formulario_5w2h() {
    }

    public void salvar(){
        DatabaseReference referenciaFirebase = ConfiguracaoFirebase.getFirebase();
        referenciaFirebase.setValue(this);
    }

    @Exclude

    public Map<String, Object> toMap(){
        HashMap<String, Object> hashMapUsuario = new HashMap<>();

        hashMapUsuario.put("What", getWhat());
        hashMapUsuario.put("Why", getWhy());
        hashMapUsuario.put("Who", getWho());
        hashMapUsuario.put("When", getWhen());
        hashMapUsuario.put("Where", getWhere());
        hashMapUsuario.put("How", getHow());

        return hashMapUsuario;

    }

    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }

    public String getWhy() {
        return why;
    }

    public void setWhy(String why) {
        this.why = why;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public String getWhen() {
        return when;
    }

    public void setWhen(String when) {
        this.when = when;
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public String getHow() {
        return how;
    }

    public void setHow(String how) {
        this.how = how;
    }
}
