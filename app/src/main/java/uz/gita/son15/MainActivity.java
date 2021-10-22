package uz.gita.son15;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import static java.lang.Math.abs;

public class MainActivity extends AppCompatActivity {

    ViewGroup group;
    Button[][] buttons;
    Coordinate empty;
    ArrayList<String> numbers;
    Chronometer chronometer;
    int score = 0;
    TextView scoreText;
    private long pauseTime = 0;
    MediaPlayer mediaPlayer;
    LocalStorage localStorage;
    ImageButton soundButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadData();
        initNumbers();
        initButtons();
        loadNumbers();
    }

    @Override
    protected void onStop() {
        super.onStop();
        localStorage.setScore(String.valueOf(score));
        pauseTime = chronometer.getBase() - SystemClock.elapsedRealtime();
        localStorage.setTime(pauseTime);
        localStorage.setEmptyX(empty.getX());
        localStorage.setEmptyY(empty.getY());
        localStorage.setNumberList(getCurrentNumbers());
        chronometer.stop();
        mediaPlayer.pause();
    }

    public List<String> getCurrentNumbers() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < group.getChildCount(); i++) {
            int x = i / 4;
            int y = i % 4;
            if (!buttons[x][y].getText().equals("")) {
                list.add(buttons[x][y].getText().toString());
            }
        }
        return list;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (localStorage.getSound()) mediaPlayer.start();
    }

    private void loadNumbers() {
        Collections.shuffle(numbers);
        int i = 0;
        int index = 0;
        while (i < group.getChildCount()) {
            int x = i / 4;
            int y = i % 4;
            if (x != localStorage.getEmptyX() || y != localStorage.getEmptyY()) {
                Button button = buttons[x][y];
                button.setText(numbers.get(index));
                button.setBackgroundResource(R.drawable.bg_button);
                index++;
            }
            i++;
        }
        setDefaultValues();
    }

    public void setDefaultValues() {
        int x = localStorage.getEmptyX();
        int y = localStorage.getEmptyY();
        empty.setX(x);
        empty.setY(y);
        buttons[x][y].setBackgroundResource(R.drawable.bg_empty);
        buttons[x][y].setText("");
        score = Integer.parseInt(localStorage.getScore());
        scoreText.setText(localStorage.getScore());
        chronometer.setBase(SystemClock.elapsedRealtime() + localStorage.getTime());
        chronometer.start();
    }

    private void initButtons() {
        for (int i = 0; i < group.getChildCount(); i++) {
            Button button = (Button) group.getChildAt(i);
            int x = i / 4;
            int y = i % 4;
            buttons[x][y] = button;
            Coordinate c = new Coordinate(x, y);
            button.setTag(c);
            button.setOnClickListener(this::onItemClicked);
        }
    }

    private void onItemClicked(View view) {
        Button button = (Button) view;
        Coordinate c = (Coordinate) button.getTag();
        int dx = empty.getX() - c.getX();
        int dy = empty.getY() - c.getY();
        Button emptyBtn = buttons[empty.getX()][empty.getY()];
        if (abs(dx) + abs(dy) == 1) {
            score++;
            emptyBtn.setText(button.getText());
            emptyBtn.setBackgroundResource(R.drawable.bg_button);
            button.setBackgroundResource(R.drawable.bg_empty);
            button.setText("");
            empty.setX(c.getX());
            empty.setY(c.getY());
            scoreText.setText(String.valueOf(score));
        } else if (dx == 0 && abs(dy) == 2) {
            score++;
            Button button1 = null;
            if (dy == 2) button1 = buttons[c.getX()][c.getY() + 1];
            else button1 = buttons[c.getX()][c.getY() - 1];
            emptyBtn.setText(button1.getText());
            button1.setText(button.getText());
            emptyBtn.setBackgroundResource(R.drawable.bg_button);
            button.setBackgroundResource(R.drawable.bg_empty);
            button.setText("");
            empty.setX(c.getX());
            empty.setY(c.getY());
            scoreText.setText(String.valueOf(score));
        } else if (abs(dx) == 2 && abs(dy) == 0) {
            score++;
            Button button1 = null;
            if (dx == 2) button1 = buttons[c.getX() + 1][c.getY()];
            else button1 = buttons[c.getX() - 1][c.getY()];
            emptyBtn.setText(button1.getText());
            button1.setText(button.getText());
            emptyBtn.setBackgroundResource(R.drawable.bg_button);
            button.setBackgroundResource(R.drawable.bg_empty);
            button.setText("");
            empty.setX(c.getX());
            empty.setY(c.getY());
            scoreText.setText(String.valueOf(score));
        } else if (abs(dx) == 3 && abs(dy) == 0) {
            score++;
            Button button1 = null;
            Button button2 = null;
            if (dx == -3) {
                button1 = buttons[empty.getX() + 1][c.getY()];
                button2 = buttons[empty.getX() + 2][c.getY()];
            } else if (dx == 3) {
                button1 = buttons[empty.getX() - 1][c.getY()];
                button2 = buttons[empty.getX() - 2][c.getY()];
            }
            if (button1 != null) {
                emptyBtn.setText(button1.getText());
                button1.setText(button2.getText());
                button2.setText(button.getText());
            }

            emptyBtn.setBackgroundResource(R.drawable.bg_button);
            button.setBackgroundResource(R.drawable.bg_empty);
            button.setText("");
            empty.setX(c.getX());
            empty.setY(c.getY());
            scoreText.setText(String.valueOf(score));
        } else if (abs(dx) == 0 && abs(dy) == 3) {
            score++;
            Button button1 = null;
            Button button2 = null;
            if (dy == -3) {
                button1 = buttons[c.getX()][empty.getY() + 1];
                button2 = buttons[c.getX()][empty.getY() + 2];
            } else if (dy == 3) {
                button1 = buttons[c.getX()][empty.getY() - 1];
                button2 = buttons[c.getX()][empty.getY() - 2];
            }
            if (button1 != null) {
                emptyBtn.setText(button1.getText());
                button1.setText(button2.getText());
                button2.setText(button.getText());
            }

            emptyBtn.setBackgroundResource(R.drawable.bg_button);
            button.setBackgroundResource(R.drawable.bg_empty);
            button.setText("");
            empty.setX(c.getX());
            empty.setY(c.getY());
            scoreText.setText(String.valueOf(score));
        }
        check();
    }


    private void initNumbers() {
        numbers = localStorage.getNumbersList();
    }

    private void loadData() {
        localStorage = new LocalStorage(this);
        group = findViewById(R.id.container);
        empty = new Coordinate(3, 3);
        chronometer = findViewById(R.id.chronometer);
        numbers = new ArrayList<>();
        buttons = new Button[4][4];
        scoreText = findViewById(R.id.score);
        mediaPlayer = MediaPlayer.create(this, R.raw.faded);
        mediaPlayer.setLooping(true);
        soundButton = findViewById(R.id.btnSound);
        if (!localStorage.getSound()) soundButton.setImageResource(R.drawable.ic_mute);
    }


    public void check() {
        boolean won = true;
        for (int i = 0; i < group.getChildCount() - 1; i++) {
            int x = i / 4;
            int y = i % 4;
            if (!buttons[x][y].getText().equals(String.valueOf(i + 1))) {
                won = false;
            }
        }
        if (won) {
            chronometer.stop();
            String time = String.valueOf((SystemClock.elapsedRealtime() - chronometer.getBase()) / 1000);
            Intent intent = new Intent(this, WinnerActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("Score", "Qadamlar: " + score);
            bundle.putString("Time", "Sariflangan vaqt:" + time + " soniya");
            intent.putExtras(bundle);
            startActivity(intent);
            restartGame();
            finish();
        }
    }

    public void restartGame() {
        localStorage.setScore("0");
        localStorage.setTime(0);
        localStorage.setEmptyX(3);
        localStorage.setEmptyX(3);
        loadNumbers();
    }

    public void restart(View view) {
        restartGame();
    }


    public void finish(View view) {
        finish();
    }

    public void sound(View view) {
        setSound();
    }

    public void setSound() {
        Boolean isAllowed = localStorage.getSound();

        if (isAllowed) {
            mediaPlayer.pause();
            soundButton.setImageResource(R.drawable.ic_mute);
            localStorage.setSound(false);
        } else {
            soundButton.setImageResource(R.drawable.ic_volume);
            localStorage.setSound(true);
            mediaPlayer.start();
        }

    }


}