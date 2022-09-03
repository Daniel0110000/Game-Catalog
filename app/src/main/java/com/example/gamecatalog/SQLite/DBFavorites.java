package com.example.gamecatalog.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBFavorites extends SQLiteOpenHelper {

    public DBFavorites(@Nullable Context context) {
        super(context, "favorites.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table favorites (id text primary key, name_game text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addFavoriteGame(String id, String nameGame){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues add = new ContentValues();
        add.put("id", id);
        add.put("name_game", nameGame);
        db.insert("favorites", null, add);
    }

    public void deleteFavoriteGame(String nameGame){
        SQLiteDatabase db = getWritableDatabase();
        db.delete("favorites", "name_game='" + nameGame + "'", null);
        db.close();
    }

    public ArrayList<String> getFavoriteGames(String nameGame){
        ArrayList<String> favorites = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        if(!nameGame.contains("-")){
            Cursor row = db.rawQuery("select * from favorites where name_game='" + nameGame + "'", null);
            if(row.moveToNext()){
                favorites.add(row.getString(0));
                favorites.add(row.getString(1));
            }
        }
        db.close();
        return favorites;
    }

    public ArrayList<String> showFavoriteGames(){
        ArrayList<String> favorites = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor row = db.rawQuery("select * from favorites", null);
        while (row.moveToNext()){
            favorites.add(row.getString(0));
        }
        db.close();
        return favorites;
    }

}
