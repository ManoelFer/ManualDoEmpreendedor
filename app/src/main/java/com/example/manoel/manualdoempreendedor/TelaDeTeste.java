package com.example.manoel.manualdoempreendedor;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class TelaDeTeste extends AppCompatActivity {

    private GridLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_de_teste);


     GridLayout gl =  new GridLayout(this);

                int i= 0;
                int j = 0;
                        GridLayout.Spec linha = GridLayout.spec(i);
                        GridLayout.Spec coluna = GridLayout.spec(j);
                        GridLayout.LayoutParams lp = new GridLayout.LayoutParams(linha, coluna);

                        ImageView iv = new ImageView(this);
                        iv.setImageResource(R.drawable.ic_action_sifrao);

                        gl.addView(iv, lp);

                setContentView(gl);

            }

}
