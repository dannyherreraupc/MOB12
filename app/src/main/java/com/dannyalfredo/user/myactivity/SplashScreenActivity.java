package com.dannyalfredo.user.myactivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreenActivity extends AppCompatActivity {



    private ImageView logo;
    private final Context context = this;
    private DBhelper helper;
    private Preferencia preferencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        preferencias = new Preferencia(this);
        iniciaAnimacao();

        iniciaBD();
        buscaUsuario();
    }

    private void iniciaAnimacao(){
        logo = (ImageView) findViewById(R.id.logotipo);

        Animation animacao = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animacao);
        logo.startAnimation(animacao);



    }

    private void iniciaBD(){        helper = new DBhelper(context);}

    private void buscaUsuario(){
        UsuarioInterface ui = UsuarioInterface.retrofit.create(UsuarioInterface.class);
        Call<Usuario> chamada = ui.getUsuario();

        chamada.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if( preferencias.estaLogado() ){
                    Intent dashboard = new Intent(context, DashboardActivity.class);
                    startActivity(dashboard);
                    finish();
                }else{
                    Usuario usuario = response.body();

                    helper.insereUsuario(usuario);

                    Intent login = new Intent(context, LoginActivity.class);
                    login.putExtra(Constantes.TAG_USUARIO, usuario);
                    startActivity(login);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(context, R.string.erro_ui, Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}
