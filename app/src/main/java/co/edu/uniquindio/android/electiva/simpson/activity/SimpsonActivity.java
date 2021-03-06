package co.edu.uniquindio.android.electiva.simpson.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

import java.util.ArrayList;
import java.util.Date;

import co.edu.uniquindio.android.electiva.simpson.R;
import co.edu.uniquindio.android.electiva.simpson.fragments.AgregarPersonajeFragment;
import co.edu.uniquindio.android.electiva.simpson.fragments.DetalleDePersonajeFragment;
import co.edu.uniquindio.android.electiva.simpson.fragments.ListaDePersonajesFragment;
import co.edu.uniquindio.android.electiva.simpson.util.Utilidades;
import co.edu.uniquindio.android.electiva.simpson.vo.Personaje;

public class SimpsonActivity extends AppCompatActivity implements ListaDePersonajesFragment.OnPersonajeSeleccionadoListener{

    private ArrayList<Personaje> personajes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utilidades.obtenerLenguaje(this);
        setContentView(R.layout.activity_simpson);

        inicializarTwitter();

        CallbackManager callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                    }
                    @Override
                    public void onCancel() {

                    }
                    @Override
                    public void onError(FacebookException exception) {

                    }
                });

        personajes = new ArrayList();
        personajes.add(new Personaje("Ronaldinho", new Date()));
        personajes.add(new Personaje("Albert Einstein", new Date()));
        personajes.add(new Personaje("Leonardo da Vinci", new Date()));
        personajes.add(new Personaje("Goku", new Date()));
        personajes.add(new Personaje("Alejandro Magno", new Date()));
        personajes.add(new Personaje("Ronaldinho", new Date()));
        personajes.add(new Personaje("Albert Einstein", new Date()));
        personajes.add(new Personaje("Leonardo da Vinci", new Date()));
        personajes.add(new Personaje("Goku", new Date()));
        personajes.add(new Personaje("Alejandro Magno", new Date()));

        ListaDePersonajesFragment listaDePersonajesFragment = (ListaDePersonajesFragment) getSupportFragmentManager().findFragmentById(R.id.fragmento_lista_personajes);
        listaDePersonajesFragment.setPersonajes(personajes);

    }

    public void inicializarTwitter(){
        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new
                        TwitterAuthConfig(getResources().getString(R.string.com_twitter_sdk_android_CONSUMER_KEY),
                        getResources().getString(R.string.com_twitter_sdk_android_CONSUMER_SECRET)))
                .debug(true)
                .build();
        Twitter.initialize(config);
    }

    /**
     * Permite determinar cual elemento del menu fue seleccionado
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return  super.onOptionsItemSelected(item);
    }


    /**
     *
     * @param position cambiar a personaje
     */
    @Override
    public void onPersonajeSeleccionado(int position) {

        boolean esFragmento = getSupportFragmentManager().findFragmentById(R.id.fragmento_detalle_personaje) != null;
        if (esFragmento) {
            ((DetalleDePersonajeFragment) getSupportFragmentManager().findFragmentById(R.id.fragmento_detalle_personaje)).mostrarPersonaje(personajes.get(position));
        } else {
            Intent i = new Intent(this, DetalleDePersonajeActivity.class);
            i.putExtra("per", personajes.get(position));
            startActivity(i);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * Permite mostrar dialogo
     * @param nombreClase
     */
    public void mostrarDialigoAgregarPersonaje(String nombreClase) {
        AgregarPersonajeFragment dialogAgrePersonaje = new AgregarPersonajeFragment();
        dialogAgrePersonaje.setStyle(dialogAgrePersonaje.STYLE_NORMAL, R.style.DialogoTitulo);
        dialogAgrePersonaje.show(getSupportFragmentManager(), nombreClase);
    }

}
