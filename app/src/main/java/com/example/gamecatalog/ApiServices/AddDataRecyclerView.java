package com.example.gamecatalog.ApiServices;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gamecatalog.Adapters.RecyclerViewAdapterGames;
import com.example.gamecatalog.Models.MainGameData;
import com.example.gamecatalog.Models.GameData;
import com.example.gamecatalog.R;
import com.example.gamecatalog.SQLite.DBFavorites;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddDataRecyclerView {

   private String category;
   private RecyclerView recyclerView;
   private RecyclerViewAdapterGames adapterAll;
   private List<MainGameData> mainGameDataList;
   private Context context;
   private ProgressBar progressBar;
   private ImageView imgError;
   private EditText searchGame;
   private List<MainGameData> filterList;
   private List<MainGameData> favoriteList;
   private ImageView imgFavoriteGames;
   private TextView msgNetworkError, msgFavoriteGames;

   private static final String URL = "https://www.freetogame.com";

    public AddDataRecyclerView(String category, RecyclerView recyclerView, Context context, ProgressBar progressBar,
                               ImageView imgError, TextView msgNetworkError, EditText searchGame, ImageView imgFavoriteGames, TextView msgFavoriteGames) {
        this.category = category;
        this.recyclerView = recyclerView;
        this.context = context;
        this.progressBar = progressBar;
        this.imgError = imgError;
        this.searchGame = searchGame;
        this.imgFavoriteGames = imgFavoriteGames;
        this.msgNetworkError = msgNetworkError;
        this.msgFavoriteGames = msgFavoriteGames;
    }


    public void addData(){
        mainGameDataList = new ArrayList<>();
        adapterAll = new RecyclerViewAdapterGames(mainGameDataList, context);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiGame apiGame = retrofit.create(ApiGame.class);
        Call<List<GameData>> call = apiGame.getAllDataGame();;

        switch (category){
            case "All":
                call = apiGame.getAllDataGame();
                break;
            case "Shooter":
                call = apiGame.getCategoriesDataGame("shooter");
                break;
            case "MMORPG":
                call = apiGame.getCategoriesDataGame("mmorpg");
                break;
            case "Anime":
                call = apiGame.getCategoriesDataGame("anime");
                break;
            case "Battle Royale":
                call = apiGame.getCategoriesDataGame("battle-royale");
                break;
            case "Strategy":
                call = apiGame.getCategoriesDataGame("strategy");
                break;
            case "Fantasy":
                call = apiGame.getCategoriesDataGame("fantasy");
                break;
            case "Sci-Fi":
                call = apiGame.getCategoriesDataGame("sci-fi");
                break;
            case "Card Games":
                call = apiGame.getCategoriesDataGame("card");
                break;
            case "Racing":
                call = apiGame.getCategoriesDataGame("racing");
                break;
            case "Fighting":
                call = apiGame.getCategoriesDataGame("fighting");
                break;
            case "Social":
                call = apiGame.getCategoriesDataGame("social");
                break;
            case "Sports":
                call = apiGame.getCategoriesDataGame("sports");
                break;
            case "Favorites":
                favorites();
                break;

        }
        call.enqueue(new Callback<List<GameData>>() {
            @Override
            public void onResponse(Call<List<GameData>> call, Response<List<GameData>> response) {
                if(response.isSuccessful()) {
                    recyclerView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    imgError.setVisibility(View.GONE);
                    msgNetworkError.setVisibility(View.GONE);
                    for (GameData gameData : response.body()) {
                        mainGameDataList.add(new MainGameData(gameData.getId(), gameData.getTitle(), gameData.getShort_description(), gameData.getThumbnail()));
                        recyclerView.setAdapter(adapterAll);
                    }
                }
            }
            @Override
            public void onFailure(Call<List<GameData>> call, Throwable t) {
                recyclerView.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.GONE);
                imgError.setVisibility(View.VISIBLE);
                msgNetworkError.setVisibility(View.VISIBLE);
            }
        });
        imgFavoriteGames.setVisibility(View.GONE);
        msgFavoriteGames.setVisibility(View.GONE);

    }

    public void favorites(){
        favoriteList = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiGame apiGame = retrofit.create(ApiGame.class);
        Call<List<GameData>> call = apiGame.getAllDataGame();

        call.enqueue(new Callback<List<GameData>>() {
            @Override
            public void onResponse(Call<List<GameData>> call, Response<List<GameData>> response) {
                DBFavorites sqLiteOpenHelperFavorites = new DBFavorites(context);
                ArrayList<String> IDList = sqLiteOpenHelperFavorites.showFavoriteGames();
                for(GameData gameData : response.body()){
                    for (int i=0; i<IDList.size(); i++){
                        if(gameData.getId().equals(IDList.get(i))){
                            favoriteList.add(new MainGameData(gameData.getId(), gameData.getTitle(), gameData.getShort_description(), gameData.getThumbnail()));
                            adapterAll.setFavoriteList(favoriteList);
                        }
                    }

                    if(IDList.isEmpty()){
                        adapterAll.setFavoriteList(favoriteList);
                        recyclerView.setVisibility(View.INVISIBLE);
                        imgFavoriteGames.setVisibility(View.VISIBLE);
                        msgFavoriteGames.setVisibility(View.VISIBLE);
                    }
                }

            }

            @Override
            public void onFailure(Call<List<GameData>> call, Throwable t) {
                // Error . . .
            }
        });
    }


    public void searchGame(){
        searchGame.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().isEmpty()){
                    recyclerView.setAdapter(adapterAll);
                }else{
                    filterLIst(editable.toString());
                }
            }
        });
    }

    private void filterLIst(String text) {
        filterList = new ArrayList<>();
        for(MainGameData item : mainGameDataList){
            if(item.getName().toLowerCase().contains(text.toLowerCase())){
                filterList.add(item);
            }
        }
        if(filterList.isEmpty()){
            Snackbar msgNotFound = Snackbar.make(context, searchGame, "Game not found", Snackbar.LENGTH_SHORT);
            msgNotFound.setBackgroundTint(Color.BLACK);
            msgNotFound.setTextColor(Color.WHITE);
            msgNotFound.show();
        }else{
            adapterAll.setFilteredList(filterList);

        }
    }



}
