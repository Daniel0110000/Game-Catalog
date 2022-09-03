package com.example.gamecatalog.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.gamecatalog.GameInformation;
import com.example.gamecatalog.Models.MainGameData;
import com.example.gamecatalog.R;
import com.example.gamecatalog.SQLite.DBFavorites;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapterGames extends RecyclerView.Adapter<RecyclerViewAdapterGames.ViewHolder> {

    private List<MainGameData> mainGameDataList;
    private Context context;
    private LayoutInflater inflater;
    private DBFavorites sqLiteOpenHelperFavorites;

    public RecyclerViewAdapterGames(List<MainGameData> mainGameDataList, Context context) {
        this.mainGameDataList = mainGameDataList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.sqLiteOpenHelperFavorites = new DBFavorites(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.items_games_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.print(position);
        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        holder.itemView.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return mainGameDataList.size();
    }

    public void setFilteredList(List<MainGameData> filteredList){
        this.mainGameDataList = filteredList;
        notifyDataSetChanged();
    }

    public void setFavoriteList(List<MainGameData> favoriteList){
        this.mainGameDataList = favoriteList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name_game, description_name;
        private ImageView image_game;
        private FloatingActionButton floating_favorite;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name_game = itemView.findViewById(R.id.name_game_all);
            description_name = itemView.findViewById(R.id.description_game_all);
            image_game = itemView.findViewById(R.id.img_game_all);
            floating_favorite = itemView.findViewById(R.id.floating_favorite);

        }
        private void print(int i){
            name_game.setText(mainGameDataList.get(i).getName());
            description_name.setText(mainGameDataList.get(i).getDescription());
            Picasso.get()
                    .load(mainGameDataList.get(i).getImage_all())
                    .into(image_game);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent gameInformation = new Intent(context, GameInformation.class);
                    gameInformation.putExtra("id", mainGameDataList.get(i).getId());
                    context.startActivity(gameInformation);
                    Animatoo.INSTANCE.animateSlideLeft(context);
                }
            });

            floating_favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ArrayList<String> favorites = sqLiteOpenHelperFavorites.getFavoriteGames(mainGameDataList.get(i).getName());
                    if(favorites.size() > 1){
                        sqLiteOpenHelperFavorites.deleteFavoriteGame(mainGameDataList.get(i).getName());
                        floating_favorite.setImageResource(R.drawable.ic_favorite_border);
                    }else{
                        sqLiteOpenHelperFavorites.addFavoriteGame(mainGameDataList.get(i).getId(), mainGameDataList.get(i).getName());
                        floating_favorite.setImageResource(R.drawable.ic_favorite);
                    }
                    notifyDataSetChanged();
                }
            });

            ArrayList<String> favorites = sqLiteOpenHelperFavorites.getFavoriteGames(mainGameDataList.get(i).getName());
            if(favorites.size() > 1){
                floating_favorite.setImageResource(R.drawable.ic_favorite);
            }else {
                floating_favorite.setImageResource(R.drawable.ic_favorite_border);
            }

        }

    }
}
