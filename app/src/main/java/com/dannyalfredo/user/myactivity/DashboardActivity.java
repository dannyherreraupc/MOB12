package com.dannyalfredo.user.myactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{
    private Preferencia prefs;

    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private DBhelper databasehelper;
    private TextView listaVazia;
    private List<AgregarMiembro> miembros;
    private AgregarMiembro miembro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        iniciaElementos();
        trataToolbar();
        validaNavegacao();
        trataFAB();
        trataNavigationDrawer();
    }
    @Override
    protected void onStop() {

        super.onStop();

    }
    @Override
    protected void onResume() {
        super.onResume();
     //   preencherTela();
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.navAdicionaMiembro:
                vaiParaFormulario();
                break;

            case R.id.navSobre:
                Intent sobre = new Intent(this, SobreActivity.class);
                startActivity(sobre);
                break;

            case R.id.navSair:
                prefs.deslogar();
                validaNavegacao();

                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void validaNavegacao() {
        if (!prefs.estaLogado()) {
            Intent login = new Intent(this, LoginActivity.class);
            startActivity(login);
            finish();
        }
    }

    private void iniciaElementos() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        prefs = new Preferencia(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        databasehelper = new DBhelper(this);
       listaVazia = (TextView) findViewById(R.id.msgEmploeeVazia);
    }

    private void trataToolbar() {
        setSupportActionBar(toolbar);
    }

    private void trataFAB() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vaiParaFormulario();
            }
        });
    }

    private void trataNavigationDrawer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }



    private void mostraRecyclerView(int tamanhoLista) {
        if (tamanhoLista > 0) {
            listaVazia.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            RecyclerView.LayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layout);
        } else {
            listaVazia.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    private void vaiParaFormulario() {
        Intent form = new Intent(this, AgregarMiembro.class);
        startActivity(form);
    }



    @Override
    public void onClick(View view) {

    }


}
