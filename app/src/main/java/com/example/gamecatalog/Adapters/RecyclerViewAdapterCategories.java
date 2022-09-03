package com.example.gamecatalog.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gamecatalog.ApiServices.AddDataRecyclerView;
import com.example.gamecatalog.Models.Categories;
import com.example.gamecatalog.R;

import java.util.List;

public class RecyclerViewAdapterCategories extends RecyclerView.Adapter<RecyclerViewAdapterCategories.ViewHolder> {

    private List<Categories> categoriesList;
    private Context context;
    private LayoutInflater inflater;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ImageView imgError;
    private TextView  selectedCategoryText, msgNetworkError, msgFavoriteGames;
    private EditText searchGame;
    private ImageView imgFavoriteGames;


    public RecyclerViewAdapterCategories(List<Categories> categoriesList, Context context, RecyclerView recyclerView,
                                         ProgressBar progressBar, ImageView imgError, TextView msgNetworkError, EditText searchGame,
                                         TextView selectedCategoryText, ImageView imgFavoriteGames, TextView msgFavoriteGames) {
        this.categoriesList = categoriesList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.recyclerView = recyclerView;
        this.progressBar = progressBar;
        this.imgError = imgError;
        this.searchGame = searchGame;
        this.selectedCategoryText = selectedCategoryText;
        this.imgFavoriteGames = imgFavoriteGames;
        this.msgNetworkError = msgNetworkError;
        this.msgFavoriteGames = msgFavoriteGames;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.items_categories_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.print(position);
        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        holder.itemView.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView category;
        private ImageView img_category;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            category = itemView.findViewById(R.id.category);
            img_category = itemView.findViewById(R.id.img_category);

        }

        private void print(int i){
            category.setText(categoriesList.get(i).getCategory());
            img_category.setImageResource(categoriesList.get(i).getImg_category());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedCategoryText.setText(categoriesList.get(i).getCategory());
                    AddDataRecyclerView addDataRecyclerViewCategories = new AddDataRecyclerView(categoriesList.get(i).getCategory(),
                            recyclerView, context, progressBar, imgError, msgNetworkError, searchGame, imgFavoriteGames, msgFavoriteGames);
                    addDataRecyclerViewCategories.addData();}
            });
        }
    }
}
