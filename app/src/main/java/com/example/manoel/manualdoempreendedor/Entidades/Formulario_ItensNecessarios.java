package com.example.manoel.manualdoempreendedor.Entidades;

import android.view.View;

import com.example.manoel.manualdoempreendedor.DAO.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Formulario_ItensNecessarios {

    private String id;
    private String uid;
    private String item;
    private Double valor;
    private int Quantidade;
    private Double valorTotal;

    public Formulario_ItensNecessarios() {
    }

    public void salvar(){
        DatabaseReference referenciaFirebase = ConfiguracaoFirebase.getFirebase();
        referenciaFirebase.child("Itens").push().setValue(this);
    }

    @Exclude

    public Map<String, Object> toMap(){
        HashMap<String, Object> hashMapUsuario = new HashMap<>();



        hashMapUsuario.put("id", getId());
        hashMapUsuario.put("Uid", getUid());
        hashMapUsuario.put("senha", getItem());
        hashMapUsuario.put("nome", getValor());
        hashMapUsuario.put("quantidade", getQuantidade());
        hashMapUsuario.put("valorTotal", getValorTotal());

        return hashMapUsuario;

    }




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public int getQuantidade() {
        return Quantidade;
    }

    public void setQuantidade(int quantidade) {
        Quantidade = quantidade;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }
}
