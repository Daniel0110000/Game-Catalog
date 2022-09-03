package com.example.gamecatalog.ApiServices;

import com.example.gamecatalog.Models.GameData;
import com.example.gamecatalog.Models.Information;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiGame {

    @GET("/api/games")
    Call<List<GameData>> getAllDataGame();

    @GET("/api/games")
    Call<List<GameData>> getCategoriesDataGame(@Query("category") String category);

    @GET("/api/game")
    Call<Information> getDataGameInformation(@Query("id") String id);

}
