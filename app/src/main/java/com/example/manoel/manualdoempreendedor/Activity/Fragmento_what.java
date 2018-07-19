package com.example.manoel.manualdoempreendedor.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.RestrictionEntry;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manoel.manualdoempreendedor.R;

import java.sql.SQLOutput;

import static com.facebook.FacebookSdk.getApplicationContext;

public class Fragmento_what extends Fragment {

    //private CreateEmailListener mListener;
    private View view;
    private AdapterView.OnItemSelectedListener onItemSelectedListener;
    private Button btnSalvarWhat;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragmento_what, container, false);
        view = inflater.inflate(R.layout.fragmento_what, container, false);

         btnSalvarWhat = (Button) view.findViewById(R.id.btn_salvar_o_que);

         btnSalvarWhat.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Toast.makeText(getApplicationContext(),"Botão CLICADO!",
                         Toast.LENGTH_LONG).show();
             }
         });
        return view;
    }
}
