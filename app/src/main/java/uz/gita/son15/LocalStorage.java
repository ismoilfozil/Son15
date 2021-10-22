package uz.gita.son15;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class LocalStorage {
    private SharedPreferences preferences;

    public LocalStorage(Context context) {
        preferences = context.getSharedPreferences("LocalStorage", Context.MODE_PRIVATE);
    }

    public void setNumberList(List<String> numbers) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("LIST_STRING_COUNT", numbers.size());
        Log.d("TTT", "setNumberList: " + numbers.toString());
        for (int i = 0; i < numbers.size(); i++) {
            editor.putString("LIST_STRING_" + i, numbers.get(i));
        }
        editor.apply();
    }


    public void setScore(String score) {
        preferences.edit()
                .putString("Score", score)
                .apply();
    }

    public void setSound(Boolean sound) {
        preferences.edit()
                .putBoolean("SOUND", sound)
                .apply();
    }

    public Boolean getSound() {
        return preferences.getBoolean("SOUND", true);
    }

    public String getScore() {
        return preferences.getString("Score", "0");
    }

    public void setTime(long time) {
        preferences.edit()
                .putLong("Time", time)
                .apply();
    }

    public long getTime() {
        return preferences.getLong("Time", 0);
    }

    public void setEmptyX(int x) {
        preferences.edit()
                .putInt("EmptyX", x)
                .apply();
    }

    public int getEmptyX() {
        return preferences.getInt("EmptyX", 3);
    }

    public void setEmptyY(int y) {
        preferences.edit()
                .putInt("EmptyY", y)
                .apply();
    }

    public int getEmptyY() {
        return preferences.getInt("EmptyY", 3);
    }

    public ArrayList<String> getNumbersList() {
        ArrayList<String> list = new ArrayList<>();
        int size = preferences.getInt("LIST_STRING_COUNT", 15);
        for (int i = 0; i < size; i++) {
            String n = preferences.getString("LIST_STRING_" + String.valueOf(i), String.valueOf(i + 1));
            list.add(n);
        }
        Log.d("TTT", "getNumbersList: " + list.toString());
        return list;
    }
}
