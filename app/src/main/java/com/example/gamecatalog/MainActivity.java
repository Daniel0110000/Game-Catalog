package com.example.gamecatalog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.gamecatalog.Adapters.RecyclerViewAdapterCategories;
import com.example.gamecatalog.ApiServices.AddDataRecyclerView;
import com.example.gamecatalog.Models.Categories;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerViewAdapterCategories adapterCategories;
    private List<Categories> categoriesList;
    private RecyclerView recyclerViewCategories;
    private RecyclerView recyclerViewAll;
    private ProgressBar progressBarRecycler;
    private ImageView imgError, imgFavoriteGames;
    private TextView selectedCategoryText, msgNetworkError, msgFavoritesGame;
    private EditText searchGame;

    private String[] nameCategoriesList = {"All","Shooter", "MMORPG", "Anime", "Battle Royale", "Strategy",
            "Fantasy", "Sci-Fi", "Card Games", "Racing", "Fighting", "Social", "Sports", "Favorites"};
    private int[] imageCategoriesList = {R.drawable.ic_all, R.drawable.ic_shooter, R.drawable.ic_mmorpg, R.drawable.ic_anime,
            R.drawable.ic_battle, R.drawable.ic_strategy, R.drawable.ic_fantasy, R.drawable.ic_sci_fi, R.drawable.ic_card,
            R.drawable.ic_racing, R.drawable.ic_fight, R.drawable.ic_social, R.drawable.ic_sports, R.drawable.ic_fav};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_GameCatalog);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statements();
        categories();
    }

    private void statements(){
        recyclerViewAll = findViewById(R.id.recycler_all_games);
        progressBarRecycler = findViewById(R.id.progressRecyclerGames);
        imgError = findViewById(R.id.img_network_error);
        msgNetworkError = findViewById(R.id.msg_network_error);
        msgFavoritesGame = findViewById(R.id.msg_favorite_games);
        searchGame = findViewById(R.id.search_game);
        selectedCategoryText = findViewById(R.id.selected_category);
        imgFavoriteGames = findViewById(R.id.img_favorite_games);


    }


    public void categories(){
        recyclerViewCategories = findViewById(R.id.recycler_categories);
        categoriesList = new ArrayList<>();
        for(int i=0; i< imageCategoriesList.length; i++){
            categoriesList.add(new Categories(nameCategoriesList[i], imageCategoriesList[i]));
        }
        adapterCategories = new RecyclerViewAdapterCategories(categoriesList, this, recyclerViewAll,
                progressBarRecycler, imgError, msgNetworkError, searchGame, selectedCategoryText, imgFavoriteGames, msgFavoritesGame);
        recyclerViewCategories.setHasFixedSize(true);
        recyclerViewCategories.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.HORIZONTAL, false));
        recyclerViewCategories.setAdapter(adapterCategories);
        AddDataRecyclerView addDataRecyclerViewCategories = new AddDataRecyclerView("All",
                recyclerViewAll, MainActivity.this, progressBarRecycler, imgError, msgNetworkError, searchGame, imgFavoriteGames, msgFavoritesGame);
        addDataRecyclerViewCategories.addData();
        addDataRecyclerViewCategories.searchGame();
    }
}