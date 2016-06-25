package com.erick.musicum.fragments;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.erick.musicum.R;
import com.erick.musicum.common.DispararIntents;
import com.erick.musicum.common.ModificarVistas;
import com.erick.musicum.common.ObtenerDatos;
import com.erick.musicum.common.ObtenerIds;
import com.erick.musicum.common.ObtenerPortadas;
import com.erick.musicum.common.objetos.Constantes;
import com.erick.musicum.common.objetos.Datos;

import java.util.ArrayList;

/**
 * Created by ErickSteven on 31/07/2015.
 */
public class Portada extends Fragment {

    private ViewPager mPager;
    private ScreenSlidePagerAdapter mPagerAdapter;
    private ImageView imgVwBackground;
    private ImageView imgVwPortada;
    private LayoutInflater inflater;
    private int posicion = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        View rootView = inflater.inflate(R.layout.fragment_view_pager, container, false);

        Datos.setPortada(this);

        mPager = (ViewPager) rootView.findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter();

        mPager.setAdapter(mPagerAdapter);
        mPager.setOffscreenPageLimit(3);
        mPager.setPageTransformer(true, new ParallaxPageTransformer());

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {
                if (state == 0) {
                    if(posicion != ObtenerIds.indexActual(getActivity())) {
                        DispararIntents.irACancion(getActivity(), posicion);
                    }
                }
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(int position) {
                posicion = position;
            }
        });

        for (int i = 0; i < Datos.getMusicService().getCursor().getCount(); i++) {
            mPagerAdapter.addView(null);
        }

        mPagerAdapter.notifyDataSetChanged();
        mPager.setCurrentItem(ObtenerIds.indexActual(getActivity()));

        return rootView;
    }

    public ViewPager getmPager() {
        return mPager;
    }

    public ScreenSlidePagerAdapter getmPagerAdapter() {
        return mPagerAdapter;
    }


    public class ScreenSlidePagerAdapter extends PagerAdapter {
        private ArrayList<View> views = new ArrayList<>();

        @Override
        public int getItemPosition(Object object) {
            int index = views.indexOf(object);
            if (index == -1)
                return POSITION_NONE;
            else
                return index;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View portadaActual = views.get(position);
            if (portadaActual == null) {
                portadaActual = inflater.inflate(R.layout.fragment_main_portada, container, false);
                views.set(position, portadaActual);
            }
            try {
                imgVwBackground = (ImageView) portadaActual.findViewById(R.id.imgVwBackground);
                imgVwPortada = (ImageView) portadaActual.findViewById(R.id.imgVwPortada);

                imgVwPortada.setImageBitmap(Datos.getPortadaDefault());
                imgVwBackground.setImageBitmap(Datos.getPortadaDefaultBlur());

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    ModificarVistas.sumarMargenes(imgVwPortada, 0, ObtenerDatos.alturaStatusBar(getActivity()) + ObtenerDatos.alturaToolbar(getActivity()),
                            0, ObtenerDatos.alturaNavigationBar(getActivity()));
                }
                Datos.getMusicService().getCursor().moveToPosition(position);
                final long idAlbum = ObtenerIds.idAlbum(getActivity(), Datos.getMusicService().getCursor().getLong(
                        Datos.getMusicService().getCursor().getColumnIndex(Constantes.CAN_ID)));
                imgVwPortada.setTag(idAlbum);
                imgVwBackground.setTag(idAlbum);
                new ObtenerPortadas(idAlbum, imgVwPortada, 500, false).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, getActivity());
                new ObtenerPortadas(idAlbum, imgVwBackground, 500, true).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, getActivity());
            } catch (Exception e) {
            }
            container.addView(portadaActual);
            return portadaActual;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
            views.set(position, null);
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        public int addView(View v) {
            return addView(v, views.size());
        }

        public int addView(View v, int position) {
            views.add(position, v);
            return position;
        }

        public int removeView(ViewPager pager, int position) {
            pager.setAdapter(null);
            views.remove(position);
            pager.setAdapter(this);

            return position;
        }

        public View getView(int position) {
            return views.get(position);
        }

        public void replace(int position, View view) {
            views.set(position, view);
        }

        public void update(int posicion, Bitmap portada, Bitmap blur) {

        }


        public View prev(View view) {
            imgVwBackground = (ImageView) view.findViewById(R.id.imgVwBackground);
            imgVwPortada = (ImageView) view.findViewById(R.id.imgVwPortada);
            imgVwBackground.setImageBitmap(Datos.getPortadaDefaultBlur());
            imgVwPortada.setImageBitmap(Datos.getPortadaDefault());
            return view;
        }

        public View next(View view) {
            imgVwBackground = (ImageView) view.findViewById(R.id.imgVwBackground);
            imgVwPortada = (ImageView) view.findViewById(R.id.imgVwPortada);
            imgVwBackground.setImageBitmap(Datos.getPortadaDefaultBlur());
            imgVwPortada.setImageBitmap(Datos.getPortadaDefault());
            return view;
        }

    }


    public class ParallaxPageTransformer implements ViewPager.PageTransformer {

        private float MIN_SCALE = 0.50f;

        public void transformPage(View view, float position) {

            int pageWidth = view.getWidth();
            ImageView imgVwBackground = (ImageView) view.findViewById(R.id.imgVwBackground);
            ImageView imgVwPortada = (ImageView) view.findViewById(R.id.imgVwPortada);

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                //imgVwPortada.setAlpha(0);

            } else if (position <= 0) { // [-1,0]
                imgVwBackground.setTranslationX(-position * (pageWidth / 2));
                // Use the default slide transition when moving to the left page
                //imgVwPortada.setAlpha(1);
                imgVwPortada.setAlpha(1 + position);
                imgVwPortada.setTranslationX(0);
                imgVwPortada.setScaleX(1);
                imgVwPortada.setScaleY(1);

            } else if (position <= 1) { // (0,1]
                imgVwBackground.setTranslationX(-position * (pageWidth / 2)); //Half the normal speed
                // Fade the page out.
                imgVwPortada.setAlpha(1 - position);

                // Counteract the default slide transition
                imgVwPortada.setTranslationX(pageWidth * -position);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                imgVwPortada.setScaleX(scaleFactor);
                imgVwPortada.setScaleY(scaleFactor);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                //imgVwPortada.setAlpha(0);
            }

        }
    }

}
