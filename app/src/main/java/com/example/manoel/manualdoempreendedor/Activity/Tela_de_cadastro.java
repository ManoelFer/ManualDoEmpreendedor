package com.example.manoel.manualdoempreendedor.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.manoel.manualdoempreendedor.DAO.ConfiguracaoFirebase;
import com.example.manoel.manualdoempreendedor.Entidades.Usuarios;
import com.example.manoel.manualdoempreendedor.Helper.Base64Custom;
import com.example.manoel.manualdoempreendedor.Helper.Preferencias;
import com.example.manoel.manualdoempreendedor.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class Tela_de_cadastro extends AppCompatActivity {

    private EditText email;
    private EditText confirma_email;
    private EditText senha;
    private EditText confirma_senha;
    private EditText nome;
    private RelativeLayout btnRegistrar;

    private Usuarios usuarios;

    private FirebaseAuth autenticacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_de_cadastro);

        email = (EditText) findViewById(R.id.cad_email);
        confirma_email = (EditText) findViewById(R.id.confirma_cad_email);
        senha = (EditText) findViewById(R.id.cad_senha);
        confirma_senha = (EditText) findViewById(R.id.confirma_cad_senha);
        nome = (EditText) findViewById(R.id.cad_nome);
        btnRegistrar = (RelativeLayout) findViewById(R.id.btn_registrar);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().equals(confirma_email.getText().toString()))
                {
                    if (senha.getText().toString().equals(confirma_senha.getText().toString()))
                    {

                        usuarios = new Usuarios();
                        usuarios.setEmail(email.getText().toString());
                        usuarios.setSenha(senha.getText().toString());
                        usuarios.setNome(nome.getText().toString());

                        cadastrarUsuario();

                    }
                    else
                    {
                        Toast.makeText(Tela_de_cadastro.this,"Erro ao registrar usuário! Campos de senha não conferem!",
                                Toast.LENGTH_LONG).show();
                        Toast.makeText(Tela_de_cadastro.this,"Por favor, certifique que os campos de senha estejam idênticos" +
                                        "e tente novamente!",
                                Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(Tela_de_cadastro.this,"Erro ao registrar usuário! Campos de e-mail não conferem!",
                            Toast.LENGTH_LONG).show();
                    Toast.makeText(Tela_de_cadastro.this,"Por favor, certifique que os campos de e-mail estejam idênticos" +
                                    "e tente novamente!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void cadastrarUsuario(){

            autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
            autenticacao.getInstance().createUserWithEmailAndPassword(
                    usuarios.getEmail(),
                    usuarios.getSenha()
            ).addOnCompleteListener(Tela_de_cadastro.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                    {
                        Toast.makeText(Tela_de_cadastro.this, "Usuário cadastrado com sucesso!", Toast.LENGTH_LONG).show();

                        String identificadorUsuario = Base64Custom.codificarBase64(usuarios.getEmail());
                        FirebaseUser usuarioFirebase = task.getResult().getUser();
                        usuarios.setId(identificadorUsuario);
                        usuarios.salvar();

                        Preferencias preferencias = new Preferencias(Tela_de_cadastro.this);
                        preferencias.salvarUsuario(identificadorUsuario, usuarios.getNome());

                        abrirLoginUsuario();
                    }
                    else
                    {
                        String erroExcecao = "";

                        try
                        {
                            throw task.getException();
                        }
                        catch (FirebaseAuthWeakPasswordException e)
                        {
                            erroExcecao = "Digite uma senha mais forte, contendo no mínimo 8 characteres de letras e números.";
                        }
                        catch (FirebaseAuthInvalidCredentialsException e)
                        {
                            erroExcecao = "O e-mail digitado é inválido, digite um novo e-mail.";
                        }
                        catch (FirebaseAuthUserCollisionException e)
                        {
                            erroExcecao = "Esse e-mail já está cadastrado no sistema.";
                        }
                        catch (Exception e)
                        {
                            erroExcecao = "Erro ao efetuar o cadastrado.";
                        }

                        Toast.makeText(Tela_de_cadastro.this,"Erro! "+erroExcecao,
                                Toast.LENGTH_LONG).show();
                    }
                }
            });

    }

    public void abrirLoginUsuario(){
        Intent intent = new Intent(Tela_de_cadastro.this, TelaInicialCadastrados.class);
        startActivity(intent);
        finish();
    }

}
