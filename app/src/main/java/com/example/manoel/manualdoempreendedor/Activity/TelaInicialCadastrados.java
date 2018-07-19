package com.example.manoel.manualdoempreendedor.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.manoel.manualdoempreendedor.Entidades.Formulario_5w2h;
import com.example.manoel.manualdoempreendedor.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TelaInicialCadastrados extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar = null;

    private EditText campo_o_que;
    private Button salvar_o_que;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial_cadastrados);

       /* campo_o_que = (EditText) findViewById(R.id.campo_o_que);
        salvar_o_que = (Button) findViewById(R.id.btn_salvar_o_que);

        salvar_o_que.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Formulario_5w2h formulario_5w2h = new Formulario_5w2h();
                formulario_5w2h.setWhat(campo_o_que.getText().toString());

                cadastrar5w2h();
            }
        });*/


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

       if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new Fragmento_what()).commit();
            navigationView.setCheckedItem(R.id.what);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
    }

    private void cadastrar5w2h() {
        DatabaseReference reference;
        reference = FirebaseDatabase.getInstance().getReference();

        FirebaseAuth autenticacao;
        autenticacao = FirebaseAuth.getInstance();
        String email = autenticacao.getCurrentUser().getEmail();

        reference.child("usuarios").orderByChild("email").equalTo(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Formulario_5w2h formulario_5w2h = new Formulario_5w2h();
                formulario_5w2h.salvar();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tela_inicial_cadastrados, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        switch (item.getItemId()) {
            case R.id.what:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Fragmento_what()).commit();
                break;
            case R.id.why:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Fragmento_why()).commit();
                break;
            case R.id.who:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Fragmento_who()).commit();
                break;
            case R.id.when:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Fragmento_when()).commit();
                break;
            case R.id.where:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Fragmento_where()).commit();
                break;
            case R.id.how:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Fragmento_how()).commit();
                break;
            case R.id.how_much:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Fragmento_how_much()).commit();
                break;
        }
        int id = item.getItemId();

        if (id == R.id.buttonLogout){
            FirebaseAuth.getInstance().signOut();
            mover_para_tela_inicial();
        }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }

        private void mover_para_tela_inicial () {
            Intent intent = new Intent(TelaInicialCadastrados.this, MainActivity.class);
            startActivity(intent);
            finish();

        }
    }
