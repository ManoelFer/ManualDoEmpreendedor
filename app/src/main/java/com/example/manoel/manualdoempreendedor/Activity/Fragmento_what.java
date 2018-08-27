package com.example.manoel.manualdoempreendedor.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.RestrictionEntry;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manoel.manualdoempreendedor.Entidades.Formulario_5w2h;
import com.example.manoel.manualdoempreendedor.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.SQLOutput;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.facebook.share.internal.DeviceShareDialogFragment.TAG;

public class Fragmento_what extends Fragment {

    //private CreateEmailListener mListener;
    private Formulario_5w2h formulario_5w2h;
    private View view;
    private AdapterView.OnItemSelectedListener onItemSelectedListener;
    private Button btnSalvarWhat;
    private String uId = "";
    private EditText texto_What;
    private String whatTrueOrFalse = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragmento_what, container, false);
        view = inflater.inflate(R.layout.fragmento_what, container, false);

         btnSalvarWhat = (Button) view.findViewById(R.id.btn_salvar_o_que);
         texto_What = (EditText) view.findViewById(R.id.campo_o_que);

         btnSalvarWhat.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 confereSeExisteCadastro();


             }
         });
        return view;
    }

    public void confereSeExisteCadastro() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        final String idUser = user.getUid();// Função para recuperar o e-mail do usuário logado.

        FirebaseDatabase database = FirebaseDatabase.getInstance();//Função do Firebase para acesssar o banco de dados
        DatabaseReference ref = database.getReferenceFromUrl("https://manual-do-empreendedor.firebaseio.com/")
                .child("5W2H");//Referência
        //pela Url, para declarar, por qual caminho o sistema deve seguir para encontrar os dados desejados

        ref.orderByChild("uID").equalTo(idUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {

                    Formulario_5w2h u = d.getValue(Formulario_5w2h.class);//Seta todos os valores na minha entidade Usuário, onde se localiza
                    //Meus geters e seters;

                    //Popula as variáveis criadas, para receber os valores, setados em minha classe.
                    whatTrueOrFalse = u.getWhat();

                        updateWhat();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
}

    public void salvar(){
        FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
        uId = user1.getUid();

        formulario_5w2h = new Formulario_5w2h();
        formulario_5w2h.setuID(uId);
        formulario_5w2h.setWhat(texto_What.getText().toString());
        formulario_5w2h.setWhy("");
        formulario_5w2h.setWho("");
        formulario_5w2h.setWhen("");
        formulario_5w2h.setWhere("");
        formulario_5w2h.setHow("");

        formulario_5w2h.salvar();

        Toast.makeText(getApplicationContext(), "Formulário cadastrado com sucesso!", Toast.LENGTH_LONG).show();
    }

    public void updateWhat(){
        FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
        uId = user1.getUid();


        formulario_5w2h = new Formulario_5w2h();
        formulario_5w2h.setuID(uId);
        formulario_5w2h.setWhat(texto_What.getText().toString());


        formulario_5w2h.updateWhat();

        Toast.makeText(getApplicationContext(), "Formulário cadastrado com sucesso!", Toast.LENGTH_LONG).show();
    }
}
