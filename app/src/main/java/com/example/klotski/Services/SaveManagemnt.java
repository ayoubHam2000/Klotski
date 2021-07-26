package com.example.klotski.Services;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import static com.example.klotski.Utiliies.Const.S_MOVES;
import static com.example.klotski.Utiliies.Const.S_Positions_LIST;

public class SaveManagemnt {


    public static void saveList(Context context, float[][] oldPositions, String typePos){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(oldPositions);
        editor.putString(typePos, json);
        editor.apply();
        System.out.println("PositionsSaved");
    }

    public static float[][] loadList(Context context, String typePos){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = pref.getString(typePos, null);
        Type type = new TypeToken<float[][]>(){}.getType();
        final float[][] loadedList = gson.fromJson(json, type);

        if(loadedList != null){
            System.out.println("list restored");
        }else{
            System.out.println("error list not restored");
        }
        return loadedList;
    }

    public static void saveMoves(Context context, int moves, String typeSave){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(typeSave, moves);
        editor.apply();
        System.out.println("saveMoves");
    }

    public static int loadMoves(Context context, String typeSave){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        System.out.println("loadMoves");
        return pref.getInt(typeSave, 0);

    }

    //saveFloat

    public static void saveFloat(Context context, float theFloat, String location){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putFloat(location, theFloat);
        editor.apply();
        System.out.println("saveFloat");
    }

    public static float loadFloat(Context context, String location){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        System.out.println("loadFloat");
        return pref.getFloat(location, -1);
    }

}
