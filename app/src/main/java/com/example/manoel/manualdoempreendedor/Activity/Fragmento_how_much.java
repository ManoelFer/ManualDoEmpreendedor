package com.example.manoel.manualdoempreendedor.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manoel.manualdoempreendedor.Entidades.Formulario_ItensNecessarios;
import com.example.manoel.manualdoempreendedor.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.facebook.FacebookSdk.getApplicationContext;

public class Fragmento_how_much extends Fragment {

    private Formulario_ItensNecessarios formulario_itensNecessarios;
    private Button btnSalvarHowMuch;
    private View view;
    private AdapterView.OnItemSelectedListener onItemSelectedListener;
    private Button btnSalvarItensEValores;
    private String uId = "";
    private EditText descricaoItem;
    private EditText valorItem;
    private TextView valorTotal;
    private EditText quantidade;
    private String id;
    private Double capitalGeral = 0.0;

    private int countLoop = 1;

    private int valorDoItem;
    private int quantidadeItem;
    private int valorTotalDoItem;

    private int i = 1;
    private int j = 0;

    private GridLayout tabela;
    private FloatingActionButton fab;


    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragmento_how_much, container, false);

        btnSalvarItensEValores = (Button) view.findViewById(R.id.buttonSalvarHowMuch);
        descricaoItem = (EditText) view.findViewById(R.id.editTextItem);
        valorItem = (EditText) view.findViewById(R.id.editTextValor);
        quantidade = (EditText) view.findViewById(R.id.editTextQuantidade);
        valorTotal = (TextView) view.findViewById(R.id.textViewValorTotal);
        tabela = (GridLayout) view.findViewById(R.id.tabelaDados);
        //fab = (FloatingActionButton) view.findViewById(R.id.btnFlutuante);


        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //fab.setBackgroundDrawable(getResources().getDrawable(R.drawable/.selector_button));
                Toast.makeText(getApplicationContext(), "Botão clicado!", Toast.LENGTH_LONG).show();
            }
        });*/

        btnSalvarItensEValores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
                uId = user1.getUid();

                formulario_itensNecessarios = new Formulario_ItensNecessarios();
                formulario_itensNecessarios.setUid(uId);
                formulario_itensNecessarios.setItem(descricaoItem.getText().toString().trim());
                formulario_itensNecessarios.setValor(Double.valueOf(valorItem.getText().toString().trim()));

                valorDoItem = Integer.parseInt(valorItem.getText().toString());
                quantidadeItem = Integer.parseInt(quantidade.getText().toString());

                valorTotalDoItem = valorDoItem * quantidadeItem;

                formulario_itensNecessarios.setQuantidade(quantidadeItem);
                formulario_itensNecessarios.setValorTotal(Double.valueOf(valorTotalDoItem));

                formulario_itensNecessarios.salvar();

                Toast.makeText(getApplicationContext(), "Item inserido com sucesso!", Toast.LENGTH_LONG).show();

                descricaoItem.setText("");
                valorItem.setText("");
                quantidade.setText("");

                recuperarDados();


            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
        uId = user1.getUid();
        recuperarDados();


    }

    public void recuperarDados() {
        final DatabaseReference reference;
        reference = FirebaseDatabase.getInstance().getReference();


        reference.child("Itens").orderByChild("uid").equalTo(uId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Formulario_ItensNecessarios itensNecessarios = d.getValue(Formulario_ItensNecessarios.class);

                    String itemx = itensNecessarios.getItem();
                    Double valorx = itensNecessarios.getValor();
                    int quantidadex = itensNecessarios.getQuantidade();
                    Double capital = itensNecessarios.getValorTotal();
                    capitalGeral = capital + capitalGeral;
                    valorTotal.setText(Double.valueOf(capitalGeral).toString() + "R$");

// Adicionando ScrollView(BARRINHA DE ROLAGEM) na tabela de apresentação de dados ---------------------------------------------------------------------------------------------
                    //FrameLayout.LayoutParams fllp = new FrameLayout.LayoutParams(GridLayout.LayoutParams.MATCH_PARENT, GridLayout.LayoutParams.MATCH_PARENT);
                    //NestedScrollView nv = new NestedScrollView.OnScrollChangeListener;
                    //sv.setLayoutParams(fllp);
                    //nv.addView(tabela);
/*
                    HorizontalScrollView hsv = new HorizontalScrollView(TelaInicialCadastrados.c);
                    hsv.setLayoutParams(fllp);
                    sv.addView(hsv);


                    //hsv.addView(tabela);*/
//----------------- FINAL SCROLL VIEW ----------------------------------------------------------------------------------------------------------------------------------------

//----------------- COLUNA 0 -------------------------------------------------------------------------------------------------------------------------------------------------
                    TextView item = new TextView(TelaInicialCadastrados.c);

                    GridLayout.Spec linha = GridLayout.spec(i);
                    GridLayout.Spec coluna = GridLayout.spec(0);
                    GridLayout.LayoutParams lp = new GridLayout.LayoutParams(linha, coluna);


                    item.setText(itemx);
                    tabela.addView(item, lp);

//----------------- COLUNA 1 -------------------------------------------------------------------------------------------------------------------------------------------------
                    TextView valor = new TextView(TelaInicialCadastrados.c);

                    coluna = GridLayout.spec(1);
                    lp = new GridLayout.LayoutParams(linha, coluna);

                    valor.setText("" + valorx + " R$");
                    tabela.addView(valor, lp);

//----------------- COLUNA 2 -------------------------------------------------------------------------------------------------------------------------------------------------
                    TextView variavelQuantidade = new TextView(TelaInicialCadastrados.c);

                    coluna = GridLayout.spec(2);
                    lp = new GridLayout.LayoutParams(linha, coluna);

                    variavelQuantidade.setText("" + quantidadex);
                    tabela.addView(variavelQuantidade, lp);

//----------------- COLUNA 3 -------------------------------------------------------------------------------------------------------------------------------------------------
                    TextView variavelTotal = new TextView(TelaInicialCadastrados.c);

                    coluna = GridLayout.spec(3);
                    lp = new GridLayout.LayoutParams(linha, coluna);

                    variavelTotal.setText("" + capital + " R$");
                    tabela.addView(variavelTotal, lp);
//---------------- FINAL DAS COLUNAS DA TABELA --------------------------------------------------------------------------------------------------------------------------------


//----------------- CONTAGEM DAS LINHAS ENQUANTO EXISTIR DADOS NO FOR ---------------------------------------------------------------------------------------------------------
                    i++;
//----------------- FINAL DAS CONTAGENS DE LINHA ------------------------------------------------------------------------------------------------------------------------------
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
