package com.example.lucas.appsmartwager;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class TelaLogin extends AppCompatActivity {

    EditText editEmail1,editSenha1;
    Button btnLogar;
    TextView txtCadastro;
    String url = "";
    String parametros = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_login);

        editEmail1 = (EditText)findViewById(R.id.editEmail1);
        editSenha1 = (EditText)findViewById(R.id.editSenha1);
        btnLogar = (Button)findViewById(R.id.btnLogar);
        txtCadastro = (TextView)findViewById(R.id.txtCadastro);

        txtCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abreCadastro = new Intent(TelaLogin.this, TelaCadastro.class);
                startActivity(abreCadastro);


            }
        });

        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isConnected()){

                    String email = editEmail1.getText().toString();
                    String senha = editSenha1.getText().toString();

                    if(email.isEmpty() || senha.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Nenhum campo pode estar vazio", Toast.LENGTH_LONG).show();
                    } else {

                        url = "http://smartwager.atwebpages.com/logar.php";

                        parametros = "email=" + email + "&senha=" + senha;

                        new SolicitaDados().execute(url);
                    }

                }else{
                    Toast.makeText(getApplicationContext(), "Nenhuma conexão foi detectada", Toast.LENGTH_LONG).show();
                }




            }
        });


    }

    private class SolicitaDados extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... urls)
         {
            return Conexao.postDados(urls[0], parametros);

    }
    @Override
    protected void onPostExecute(String resultado){

            if(resultado.contains("login_OK")){
                Intent abreInicio = new Intent(TelaLogin.this, TelaInicial.class);
                startActivity(abreInicio);
            }else {
                Toast.makeText(getApplicationContext(), "Usuário ou senha estão incorretos", Toast.LENGTH_LONG).show();

            }


    }


    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}

