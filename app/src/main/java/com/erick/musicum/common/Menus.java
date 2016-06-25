package com.erick.musicum.common;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.erick.musicum.R;

/**
 * Created by ErickSteven on 6/1/2016.
 */
public class Menus {

    public static SearchView barraBusqueda(final Activity activity, final Menu menu, final MenuInflater inflater, final int vista, final SearchView.OnQueryTextListener listener) {
        inflater.inflate(vista, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        SearchView searchView = null;

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            SearchManager searchManager = (SearchManager) activity.getSystemService(Context.SEARCH_SERVICE);
            searchView.setSearchableInfo(searchManager.getSearchableInfo(activity.getComponentName()));
            SearchView.OnQueryTextListener queryTextListener = listener;
            searchView.setOnQueryTextListener(queryTextListener);
        }
        return searchView;
    }
}
