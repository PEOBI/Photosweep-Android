package com.polybiastudios.photosweep;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class PhotoDetailsFragment extends Fragment {

    private LinearLayout recipientLayout;
    private View recipientView;


    public PhotoDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_photo_details, container, false);

        recipientLayout = (LinearLayout)v.findViewById(R.id.recipient_layout_view);

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View recipientView = layoutInflater.inflate(R.layout.recipient_item, null);

        TextView textView = (TextView)recipientView.findViewById(R.id.name_text_view);
        textView.setText("Recipient name");

        recipientLayout.addView(recipientView);

        return v;
    }

}
