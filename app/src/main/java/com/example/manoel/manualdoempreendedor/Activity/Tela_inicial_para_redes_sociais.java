package com.example.manoel.manualdoempreendedor.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.manoel.manualdoempreendedor.R;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Tela_inicial_para_redes_sociais extends AppCompatActivity {

    private Button button_logout;
    private TextView emailUser;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseUser mFirebaseUser;

    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial_para_redes_sociais);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        inicializarComponentes();
        inicializarFirebase();
        clickButton();


    }

    private void clickButton() {
        button_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
            }
        });
    }

    private void logOut() {

        //---Sai do Facebook--//
        mFirebaseAuth.signOut();
        LoginManager.getInstance().logOut();
        //---Sai do Facebook--//

        //-----------Sai da conta do Google----------//
        Auth.GoogleSignInApi.signOut(mGoogleApiClient);
        //-----------Sai da conta do Google----------//

        //-------------------Redireciona de volta para tela de login--------------------------------------//
        Intent intent = new Intent(Tela_inicial_para_redes_sociais.this, MainActivity.class);
        startActivity(intent);
        finish();
        //-------------------Redireciona de volta para tela de login--------------------------------------//
    }

    private void inicializarFirebase() {
        mFirebaseAuth = FirebaseAuth.getInstance();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mFirebaseUser = firebaseAuth.getCurrentUser();
                if(mFirebaseUser != null){
                    exibirDados(mFirebaseUser);
                }else{
                    //finish();
                }
            }
        };
    }

    private void exibirDados(FirebaseUser mFirebaseUser) {
        emailUser.setText(mFirebaseUser.getEmail());
    }

    private void inicializarComponentes(){
        button_logout = (Button) findViewById(R.id.btnLogout);
        emailUser = (TextView) findViewById(R.id.e_mailUser);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }
}
