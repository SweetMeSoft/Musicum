package com.erick.musicum.main;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.erick.musicum.R;
import com.erick.musicum.comun.DispararIntents;
import com.erick.musicum.comun.ModificarVistas;
import com.erick.musicum.comun.Navegar;
import com.erick.musicum.comun.ObtenerDatos;
import com.erick.musicum.comun.ObtenerIds;
import com.erick.musicum.comun.Volumen;
import com.erick.musicum.comun.objetos.Constantes;
import com.erick.musicum.comun.objetos.Datos;
import com.erick.musicum.comun.objetos.Preferencias;
import com.erick.musicum.fragmentos.Controles;
import com.erick.musicum.fragmentos.FragmentMain;
import com.erick.musicum.fragmentos.Portada;
import com.erick.musicum.fragmentos.Reproductor;
import com.erick.musicum.listas.Albumes;
import com.erick.musicum.listas.Artistas;
import com.erick.musicum.listas.CancionesActual;
import com.erick.musicum.listas.Carpetas;
import com.erick.musicum.listas.Generos;
import com.erick.musicum.listas.Listas;
import com.erick.musicum.listas.TodasCanciones;
import com.erick.musicum.servicios.ConexionAudifonosReceiver;
import com.erick.musicum.servicios.MusicService;
import com.erick.musicum.servicios.RemoteControlReceiver;

public class MainActivity extends BaseActivity {

    private DrawerLayout drawerLayout;
    private AppBarLayout appbar;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;
    private ImageView imgVwHeader;
    private FloatingActionButton fabHeader;
    private FragmentMain fragmentMain;
    private Controles controles;
    private Reproductor reproductor;
    private Portada portada;
    private int indexFragmento;
    private Toolbar toolbar;
    private ConexionAudifonosReceiver receiverAudifonos;
    private ServiceConnection musicConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder) service;
            //Datos.setMusicService(binder.getService());
            Datos.setMusicBound(true);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Datos.setMusicBound(false);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iniciarActividad();
        AudioManager mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mAudioManager.registerMediaButtonEventReceiver(new ComponentName(this, RemoteControlReceiver.class));

        if (savedInstanceState == null) {
            setContentView(R.layout.activity_main);

            drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            navigationView = (NavigationView) findViewById(R.id.navigation_view);
            fabHeader = (FloatingActionButton) navigationView.findViewById(R.id.fabHeader);
            imgVwHeader = (ImageView) navigationView.findViewById(R.id.imgVwHeader);
            appbar = (AppBarLayout) findViewById(R.id.appbar);
            fragmentMain = new FragmentMain();
            reproductor = new Reproductor();
            portada = new Portada();
            controles = new Controles();
            toolbar = (Toolbar) findViewById(R.id.toolbar);

            Datos.setFragmentMain(fragmentMain);
            Datos.setReproductor(reproductor);
            Datos.setPortada(portada);
            Datos.setControles(controles);

            setSupportActionBar(toolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                ModificarVistas.sumarMargenes(appbar, 0, ObtenerDatos.alturaStatusBar(this), 0, 0);
            }

            drawerLayout.setDrawerListener(drawerToggle);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentMain).commit();

            navigationView.setNavigationItemSelectedListener(
                    new NavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(MenuItem menuItem) {

                            menuItem.setChecked(true);
                            String posicion = menuItem.toString();
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            for (int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
                                fragmentManager.popBackStack();
                            }
                            Fragment fragmento = null;

                            if (posicion.equals(getResources().getString(R.string.title_section1))) {
                                fragmentManager.beginTransaction().replace(R.id.container, Datos.getFragmentMain()).commit();
                                indexFragmento = Constantes.FRAG_MAIN;
                            } else {
                                if (posicion.equals(getResources().getString(R.string.title_section2))) {
                                    fragmento = new CancionesActual();
                                    fragmentManager.beginTransaction().replace(R.id.container, fragmento).commit();
                                    indexFragmento = Constantes.FRAG_LISTA_ACTUAL;
                                } else {
                                    if (posicion.equals(getResources().getString(R.string.title_section3))) {
                                        fragmento = new TodasCanciones();
                                        fragmentManager.beginTransaction().replace(R.id.container, fragmento).commit();
                                        indexFragmento = Constantes.FRAG_TODAS_CANCIONES;
                                    } else {
                                        if (posicion.equals(getResources().getString(R.string.title_section4))) {
                                            fragmento = new Artistas();
                                            fragmentManager.beginTransaction().replace(R.id.container, fragmento).commit();
                                            indexFragmento = Constantes.FRAG_ARTISTAS;
                                        } else {
                                            if (posicion.equals(getResources().getString(R.string.title_section5))) {
                                                fragmento = new Albumes();
                                                fragmentManager.beginTransaction().replace(R.id.container, fragmento).commit();
                                                indexFragmento = Constantes.FRAG_ALBUMES;
                                            } else {
                                                if (posicion.equals(getResources().getString(R.string.title_section6))) {
                                                    fragmento = new Generos();
                                                    fragmentManager.beginTransaction().replace(R.id.container, fragmento).commit();
                                                    indexFragmento = Constantes.FRAG_GENEROS;
                                                } else {
                                                    if (posicion.equals(getResources().getString(R.string.title_section7))) {
                                                        fragmento = new Carpetas();
                                                        fragmentManager.beginTransaction().replace(R.id.container, fragmento).commit();
                                                        indexFragmento = Constantes.FRAG_CARPETAS;
                                                    } else {
                                                        if (posicion.equals(getResources().getString(R.string.title_section8))) {
                                                            fragmento = new Listas();
                                                            fragmentManager.beginTransaction().replace(R.id.container, fragmento).commit();
                                                            indexFragmento = Constantes.FRAG_LISTAS;
                                                        } else {
                                                            if (posicion.equals(getResources().getString(R.string.action_salir))) {
                                                                System.exit(0);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            drawerLayout.closeDrawers();
                            return true;
                        }
                    });
            fabHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (Datos.getEstado(getApplicationContext())) {
                        case Constantes.ESTADO_DETENIDO:
                            Datos.getMusicService().start();
                        case Constantes.ESTADO_PAUSA:
                            Datos.getMusicService().start();
                            break;
                        case Constantes.ESTADO_REPRODUCIENDO:
                            Datos.getMusicService().pause();
                            break;
                    }
                }
            });

        }
    }

    private void iniciarActividad() {
        Datos.setMainActivity(this);
        receiverAudifonos = new ConexionAudifonosReceiver();
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        drawerToggle.syncState();
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        drawerToggle.onConfigurationChanged(newConfig);
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        iniciarActividad();
        registerReceiver(receiverAudifonos, new IntentFilter(Intent.ACTION_HEADSET_PLUG));
        Intent intent = new Intent(this, MusicService.class);
        bindService(intent, musicConnection, Context.BIND_AUTO_CREATE);
        super.onResume();
    }

    @Override
    protected void onPause(){
        unregisterReceiver(receiverAudifonos);
        unbindService(musicConnection);
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        Preferencias.guardarDato(getApplicationContext(), Constantes.VALOR_POSICION_ACTUAL, Datos.getMusicService().getPosicionActual());
        Datos.setReproductor(null);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        switch (indexFragmento) {
            case 7:
                if (!Preferencias.obtenerValorRuta(getApplicationContext()).equals(Constantes.ROOT)) {

                } else {
                    Navegar.irAInicio(this, 0);
                }
                break;
            default:
                super.onBackPressed();
        }
    }

    public void play(View view) {
        if(!Volumen.modificando) {
            DispararIntents.reproducir(getApplicationContext());
        }
    }

    public void playNext(View view) {
        if(!Volumen.modificando) {
            Datos.getPortada().getmPager().setCurrentItem(Datos.getMusicService().getIndexSiguiente(), true);
        }
    }

    public void playPrev(View view) {
        if(!Volumen.modificando) {
            Datos.getPortada().getmPager().setCurrentItem(Datos.getMusicService().getIndexAnterior(), true);
        }
    }

    public void setRandom(View view) {
        switch (Preferencias.obtenerRandom(getApplicationContext())) {
            case 0:
                Preferencias.guardarDato(getApplicationContext(), Constantes.VALOR_RANDOM, 1);
                Datos.getControles().getImgBtnRandom().setImageResource(R.drawable.random1);
                break;
            case 1:
            default:
                Preferencias.guardarDato(getApplicationContext(), Constantes.VALOR_RANDOM, 0);
                Datos.getControles().getImgBtnRandom().setImageResource(R.drawable.random0);
                break;
        }
        //ObtenerDatos.randomizarListaActual();
        Datos.getPortada().getmPager().setCurrentItem(ObtenerIds.indexActual(getApplicationContext()), true);
    }


    public ImageView getImgVwHeader() {
        return imgVwHeader;
    }

    public FloatingActionButton getFabHeader() {
        return fabHeader;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }
}
