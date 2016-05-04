package com.polybiastudios.photosweep;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;


/**
 * A simple {@link Fragment} subclass.
 */
public class PricingFragment extends Fragment {


    public PricingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_pricing, container, false);

        WebView webView = (WebView)v.findViewById(R.id.web_view);

        webView.loadUrl(Constants.kServerHostHTTPS + Constants.kServerPathPrice);

        return v;
    }

}
