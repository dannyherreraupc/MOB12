package com.dannyalfredo.user.myactivity;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by User on 12/03/2017.
 */

public class Preferencia {

    private SharedPreferences preferences;


    public Preferencia(Context context){
        this.preferences = context.getSharedPreferences(Constantes.SHARED_PREFERENCES, 0);;
    }

    public boolean estaLogado(){
        return preferences.getBoolean(Constantes.PREF_LOGADO, false);
    }

    public void manterLogado(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(Constantes.PREF_LOGADO, true);
        editor.apply();
    }

    public void deslogar(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(Constantes.PREF_LOGADO, false);
        editor.apply();

    }

}


