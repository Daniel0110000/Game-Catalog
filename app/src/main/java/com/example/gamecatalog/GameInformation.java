package com.example.gamecatalog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.gamecatalog.Adapters.RecyclerViewAdapterScreenshots;
import com.example.gamecatalog.ApiServices.ApiGame;
import com.example.gamecatalog.Models.Information;
import com.example.gamecatalog.Models.Screenshots;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GameInformation extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Screenshots> screenshotsList;
    private RecyclerViewAdapterScreenshots adapterScreenshots;

    private String id;
    private TextView titleGame, descriptionGame, genre, platform, publisher, developer;
    private ImageView imageGame, imageScreenshot;
    private Button actionGame;

    private FrameLayout modalScreenshots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_information);

        findViewById(R.id.back_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GameInformation.this, MainActivity.class));
                Animatoo.INSTANCE.animateSlideRight(GameInformation.this);
                finish();
            }
        });
        findViewById(R.id.closed_modal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modalScreenshots.setVisibility(View.GONE);
            }
        });

        statements();
        showData();
    }

    private void statements(){
        id = getIntent().getStringExtra("id");
        titleGame = findViewById(R.id.name_game_information);
        imageGame = findViewById(R.id.image_game_information);
        descriptionGame = findViewById(R.id.description_game);
        genre = findViewById(R.id.genre);
        platform = findViewById(R.id.platform);
        publisher = findViewById(R.id.publisher);
        developer = findViewById(R.id.developer);
        actionGame = findViewById(R.id.action_game);
        modalScreenshots = findViewById(R.id.modal);
        imageScreenshot = findViewById(R.id.image_screenshot);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_image_game_information);
        imageGame.startAnimation(animation);

    }


    private void showData(){

        recyclerView = findViewById(R.id.screenshots_game);
        screenshotsList = new ArrayList<>();
        adapterScreenshots = new RecyclerViewAdapterScreenshots(screenshotsList, GameInformation.this, modalScreenshots, imageScreenshot);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(GameInformation.this, RecyclerView.HORIZONTAL, false));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.freetogame.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiGame apiGame = retrofit.create(ApiGame.class);
        Call<Information> call = apiGame.getDataGameInformation(id);

        call.enqueue(new Callback<Information>() {
            @Override
            public void onResponse(Call<Information> call, Response<Information> response) {
                if(response.isSuccessful()){
                    titleGame.setText(response.body().getTitle());
                    Picasso.get()
                            .load(response.body().getThumbnail())
                            .into(imageGame);
                    descriptionGame.setText(response.body().getDescription());
                    for(Screenshots screenshots : response.body().getScreenshots()){
                        screenshotsList.add(new Screenshots(screenshots.getImage()));
                        recyclerView.setAdapter(adapterScreenshots);
                    }
                    if(response.body().getPlatform().equals("Windows")){
                        actionGame.setText(R.string.download);
                    }else{
                        actionGame.setText(R.string.play);
                    }
                    genre.setText(response.body().getGenre());
                    platform.setText(response.body().getPlatform());
                    publisher.setText(response.body().getPublisher());
                    developer.setText(response.body().getDeveloper());
                    actionGame.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse(response.body().getGame_url()));
                            startActivity(browser);
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<Information> call, Throwable t) {
                // Error . . .
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(modalScreenshots.getVisibility() == View.VISIBLE){
            modalScreenshots.setVisibility(View.GONE);
        }else{
            super.onBackPressed();
            startActivity(new Intent(GameInformation.this, MainActivity.class));
            Animatoo.INSTANCE.animateSlideRight(GameInformation.this);
            finish();
        }
    }
}