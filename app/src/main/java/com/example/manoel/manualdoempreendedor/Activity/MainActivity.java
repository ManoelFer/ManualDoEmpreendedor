package com.example.manoel.manualdoempreendedor.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.manoel.manualdoempreendedor.DAO.ConfiguracaoFirebase;
import com.example.manoel.manualdoempreendedor.Entidades.Usuarios;
import com.example.manoel.manualdoempreendedor.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class MainActivity extends AppCompatActivity {

    private EditText edtemail;
    private EditText edtsenha;
    private RelativeLayout btnEntrar;
    private RelativeLayout btnEsqueciMinhaSenha;
    private RelativeLayout btn_Registrar;

    private Usuarios usuarios;

    private CallbackManager callbackManager;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private SignInButton buttonLogGoogle;

    private ProgressDialog loadingBar;

    private final static int RC_SIGN_IN = 7;

    private GoogleApiClient mGoogleSignInClient;
    FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtemail = (EditText) findViewById(R.id.edt_email);
        edtsenha = (EditText) findViewById(R.id.edt_senha);
        btnEntrar = (RelativeLayout) findViewById(R.id.btn_entrar);
        btnEsqueciMinhaSenha = (RelativeLayout) findViewById(R.id.btn_lembrarsenha);
        btn_Registrar = (RelativeLayout) findViewById(R.id.btn_registrar);


        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtemail.getText().toString().equals("") && !edtsenha.getText().toString().equals("")){

                    usuarios = new Usuarios();
                    usuarios.setEmail(edtemail.getText().toString());
                    usuarios.setSenha(edtsenha.getText().toString());

                    ValidarLogin();

                }else{
                    Toast.makeText(MainActivity.this, "Preencha os campos de e-mail e senha!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_Registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendUserToTela_de_cadastro();
            }
        });

        /*FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            SendUserToTela_inicial_para_redes_sociais();
        }*/

        firebaseAuth = FirebaseAuth.getInstance();
        loadingBar = new ProgressDialog(this);
        //______________________________________________________Ativando o botão de login com o Facebook___________________________________________
        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.LoginButtonFacebook);
        loginButton.setReadPermissions("email", "public_profile");


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(null, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());

                SendUserToTela_inicial_para_redes_sociais();
            }

            @Override
            public void onCancel() {
                Log.d(null, "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(null, "facebook:onError", error);
                // ...
            }
        });
        //____________________________________________________________Finalizando botão de login com facebook______________________________________

        buttonLogGoogle = (SignInButton) findViewById(R.id.LoginButtonGoogle);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(MainActivity.this,"A conexão com o Google falhou!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        buttonLogGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    private void signIn(){

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleSignInClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    //--------------------------------------------- Segunda etapa de Login com o Google -------------------------------------------------------

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN)
        {

            loadingBar.setTitle("Vinculando a conta do Google...");
            loadingBar.setMessage("Aguarde enquanto o manual do empreendedor faz login com sua conta do Google...");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();

            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if (result.isSuccess()) {

                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
                loadingBar.dismiss();

            } else {
                Toast.makeText(MainActivity.this, "Algo deu errado!", Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }

        callbackManager.onActivityResult(requestCode, resultCode, data);



    }

    //Segue abaixo o método de conexão do Google
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("TAG", "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                                Log.d("TAG", "signInWithCredential:success");
                                SendUserToTela_inicial_para_redes_sociais();
                                loadingBar.dismiss();


                        } else {

                                Log.w("TAG", "signInWithCredential:failure", task.getException());
                                Toast.makeText(MainActivity.this, "Falha na autenticação com conta do Google!", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                        }

                    }
                });
    }


    //Segue abaixo o método de conexão com o facebook!
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(null, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Cadastro realizado com sucesso!
                            Log.d(null, "signInWithCredential:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            SendUserToTela_inicial_para_redes_sociais();

                        } else {
                            // Falha no cadastro
                            Log.w(null, "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Falha na autenticação com conta do Facebook!", Toast.LENGTH_SHORT).show();
                            SendUserToMainActivity();
                        }

                        // ...
                    }
                });
    }

    private void SendUserToMainActivity() {

        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void SendUserToTela_inicial_para_redes_sociais() {

        Intent intent = new Intent(MainActivity.this, Tela_inicial_redes_sociais.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }

    private void SendUserToTela_de_cadastro(){
        Intent intent = new Intent(MainActivity.this, Tela_de_cadastro.class);
        startActivity(intent);
        finish();
    }

    private void SendUserToTela_inicial_para_cadastrados(){
        Intent intent = new Intent(MainActivity.this, TelaInicialCadastrados.class);
        startActivity(intent);
        finish();
    }

    private void ValidarLogin(){
        firebaseAuth = ConfiguracaoFirebase.getFirebaseAutenticacao();


        firebaseAuth.getInstance().signInWithEmailAndPassword(usuarios.getEmail(), usuarios.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful())
                {
                    SendUserToTela_inicial_para_cadastrados();
                    Toast.makeText(MainActivity.this,"Login efetuado com sucesso!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Usuário ou senha inválidos!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
