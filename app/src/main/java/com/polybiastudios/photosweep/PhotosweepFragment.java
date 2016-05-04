package com.polybiastudios.photosweep;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

/**
 * Created by dallascharter on 21/03/16.
 */
public class PhotosweepFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        // Enabling the 'Home button as back' functionality

        setHasOptionsMenu(hasNavigationBackButton());
    }

    @Override
    public void onResume() {
        super.onResume();

        // Enable/Disable the drawer toggle button
        MainActivity.getInstance().toggleBackButtonEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        getActivity().onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    public boolean hasNavigationBackButton(){
        return false;
    }
}
