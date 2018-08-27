package com.example.manoel.manualdoempreendedor.Entidades;

import android.widget.ArrayAdapter;

import com.example.manoel.manualdoempreendedor.DAO.ConfiguracaoFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Formulario_5w2h {

    private DatabaseReference referenciaFirebase;

    private String uID;
    private String what;
    private String why;
    private String who;
    private String when;
    private String where;
    private String how;

    private String whatTrueOrFalse = null;

    private List<Formulario_5w2h> listFormulario = new ArrayList<Formulario_5w2h>();
    private ArrayAdapter<Formulario_5w2h> arrayAdapterFormulario;

    public Formulario_5w2h() {
    }


        public void salvar () {

            referenciaFirebase = ConfiguracaoFirebase.getFirebase();
            referenciaFirebase.child("5W2H").child(String.valueOf(getuID())).setValue(this);
        }

        public void updateWhat(){
            referenciaFirebase = ConfiguracaoFirebase.getFirebase();
            referenciaFirebase.child("5W2H").child(String.valueOf(getuID())).child("what").setValue(what);
        }

        public void updateWhy () {
            referenciaFirebase = ConfiguracaoFirebase.getFirebase();
            referenciaFirebase.child("5W2H").child(String.valueOf(getuID())).child("why").setValue(why);
        }

        public void updateWho () {
            referenciaFirebase = ConfiguracaoFirebase.getFirebase();
            referenciaFirebase.child("5W2H").child(String.valueOf(getuID())).child("who").setValue(who);
        }
        public void updateWhen () {
            referenciaFirebase = ConfiguracaoFirebase.getFirebase();
            referenciaFirebase.child("5W2H").child(String.valueOf(getuID())).child("when").setValue(when);
        }
        public void updateWhere () {
            referenciaFirebase = ConfiguracaoFirebase.getFirebase();
            referenciaFirebase.child("5W2H").child(String.valueOf(getuID())).child("where").setValue(where);
        }
        public void updateHow () {
            referenciaFirebase = ConfiguracaoFirebase.getFirebase();
            referenciaFirebase.child("5W2H").child(String.valueOf(getuID())).child("how").setValue(how);
        }

    @Exclude

    public Map<String, Object> toMap(){
        HashMap<String, Object> hashMapUsuario = new HashMap<>();

        hashMapUsuario.put("uID",getuID());
        hashMapUsuario.put("What", getWhat());
        hashMapUsuario.put("Why", getWhy());
        hashMapUsuario.put("Who", getWho());
        hashMapUsuario.put("When", getWhen());
        hashMapUsuario.put("Where", getWhere());
        hashMapUsuario.put("How", getHow());

        return hashMapUsuario;

    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
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
