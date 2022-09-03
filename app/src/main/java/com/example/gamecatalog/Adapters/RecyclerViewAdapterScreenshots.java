package com.example.gamecatalog.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gamecatalog.Models.Screenshots;
import com.example.gamecatalog.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapterScreenshots extends RecyclerView.Adapter<RecyclerViewAdapterScreenshots.ViewHolder> {

    private List<Screenshots> screenshotsList;
    private Context context;
    private LayoutInflater inflater;
    private FrameLayout modal;
    private ImageView imageScreenshot;

    public RecyclerViewAdapterScreenshots(List<Screenshots> screenshotsList, Context context, FrameLayout modal, ImageView imageScreenshot) {
        this.screenshotsList = screenshotsList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.modal = modal;
        this.imageScreenshot = imageScreenshot;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.items_screenshots_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.print(position);
        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        holder.itemView.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return screenshotsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView image_screenshot;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image_screenshot = itemView.findViewById(R.id.image_screenshot);

        }

        private void print(int i){
            Picasso.get()
                    .load(screenshotsList.get(i).getImage())
                    .into(image_screenshot);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Picasso.get()
                            .load(screenshotsList.get(i).getImage())
                            .into(imageScreenshot);
                    modal.setVisibility(View.VISIBLE);
                }
            });
        }
    }
}
