package uz.gita.son15;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class WinnerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner);

        Bundle bundle = getIntent().getExtras();
        TextView moves = findViewById(R.id.moves);
        TextView time = findViewById(R.id.timeText);

        moves.setText(bundle.getString("Score"));
        time.setText(bundle.getString("Time"));


    }

    public void replay(View view) {
        Intent intent = new Intent(WinnerActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    public void home(View view) {
        finish();
    }
}