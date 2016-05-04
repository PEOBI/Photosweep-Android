package com.polybiastudios.photosweep;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {

    private ImageButton galleryButton;
    private ImageButton cameraButton;

    public String savedImage;


    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_dashboard, container, false);

        galleryButton = (ImageButton)v.findViewById(R.id.gallery_button);
        cameraButton = (ImageButton)v.findViewById(R.id.camera_button);

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayCamera();
            }
        });

        return v;
    }

    public void displayCamera() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        savedImage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + "photosweep_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".jpg";
        File image = new File(savedImage);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(image));

        // start the image capture Intent
        startActivityForResult(intent, 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("request code: "+requestCode);
        System.out.println("result code: "+resultCode);
        if(requestCode == 100 && resultCode == 0){
            return;
        }
        if(requestCode == 100 && resultCode < 0){
            //MainActivity.getInstance().openImageUpload(savedImage);
        }
    }

}
