package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.w3c.dom.Text;

public class DetailNeighbourActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_neighbour);

        final ImageButton backButton = findViewById(R.id.item_detail_back_button);
        final ImageView avatar = findViewById(R.id.item_detail_avatar);
        final TextView avatarName = findViewById(R.id.item_avatar_name);
        final TextView profilName = findViewById(R.id.item_profil_name);
        final TextView profilAddress = findViewById(R.id.item_profil_adress);
        final TextView profilPhoneNumber = findViewById(R.id.item_profil_phone_number);
        final TextView profilInternetAddress = findViewById(R.id.item_profil_internet_address);
        final TextView aboutMe = findViewById(R.id.item_aboutme_content);

        String avatarUrl = getIntent().getExtras().getString("AvatarUrl");
        String nameContent = getIntent().getExtras().getString("Name");
        String addressContent = getIntent().getExtras().getString("Address");
        String phoneNumberContent = getIntent().getExtras().getString("PhoneNumber");
        String aboutMeContent = getIntent().getExtras().getString("AboutMe");

        Glide.with(avatar).load(avatarUrl).into(avatar);
        avatarName.setText(nameContent);
        profilName.setText(nameContent);
        profilAddress.setText(addressContent);
        profilPhoneNumber.setText(phoneNumberContent);
        profilInternetAddress.setText("www.facebook.fr/"+nameContent.toLowerCase());
        aboutMe.setText(aboutMeContent);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    private Neighbour neighbourFromAdapter;
    public void getNeighbourFromAdapter(Neighbour neighbour) {
        neighbourFromAdapter = neighbour;
    }
}