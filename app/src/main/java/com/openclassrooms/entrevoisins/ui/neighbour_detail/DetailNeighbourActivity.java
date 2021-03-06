package com.openclassrooms.entrevoisins.ui.neighbour_detail;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailNeighbourActivity extends AppCompatActivity {

    @BindView(R.id.item_detail_avatar)
    ImageView avatar;
    @BindView(R.id.item_detail_back_button)
    ImageButton backButton;
    @BindView(R.id.item_avatar_name)
    TextView avatarName;
    @BindView(R.id.item_profile_name)
    TextView profileName;
    @BindView(R.id.item_profile_address)
    TextView profileAddress;
    @BindView(R.id.item_profile_phone_number)
    TextView profilePhoneNumber;
    @BindView(R.id.item_profile_internet_address)
    TextView profileInternetAddress;
    @BindView(R.id.item_aboutme_content)
    TextView aboutMe;
    @BindView(R.id.add_favorite)
    FloatingActionButton addFavorite;

    private NeighbourApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_neighbour);
        ButterKnife.bind(this);
        mApiService = DI.getNeighbourApiService();

        Neighbour neighbour = getIntent().getParcelableExtra("Neighbour");

        init(neighbour);
        initListener(neighbour);

    }

    private void init(Neighbour neighbour){
        Glide.with(avatar).load(neighbour.getAvatarUrl()).into(avatar);
        avatarName.setText(neighbour.getName());
        profileName.setText(neighbour.getName());
        profileAddress.setText(neighbour.getAddress());
        profilePhoneNumber.setText(neighbour.getPhoneNumber());
        profileInternetAddress.setText("www.facebook.fr/"+neighbour.getName().toLowerCase());
        aboutMe.setText(neighbour.getAboutMe());

        if(neighbour.isFavorite()){
            addFavorite.setImageResource(R.drawable.ic_star_yellow_24dp);
        }
        else{
            addFavorite.setImageResource(R.drawable.ic_star_white_24dp);
        }
    }

    private void initListener(Neighbour neighbour){
        addFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(neighbour.isFavorite()){
                    addFavorite.setImageResource(R.drawable.ic_star_white_24dp);
                }
                else {
                    addFavorite.setImageResource(R.drawable.ic_star_yellow_24dp);
                }
                mApiService.switchNeighbour(neighbour);
                neighbour.setFavorite(!neighbour.isFavorite());
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}