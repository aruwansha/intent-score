package id.putraprima.skorbola;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MatchActivity extends AppCompatActivity {

    private TextView txtHome, txtAway, scoreHome, scoreAway;
    private ImageView homeImage, awayImage;
    private int homeScoreVal, awayScoreVal;
    private static final String TAG = MainActivity.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        //TODO
        //1.Menampilkan detail match sesuai data dari main activity
        txtHome = findViewById(R.id.txt_home);
        txtAway = findViewById(R.id.txt_away);
        homeImage = findViewById(R.id.home_logo);
        awayImage = findViewById(R.id.away_logo);
        scoreHome = findViewById(R.id.score_home);
        scoreAway = findViewById(R.id.score_away);

        Bundle extras = getIntent().getExtras();
        Uri homeLogoUri = Uri.parse(extras.getString("homeUri"));
        Uri awayLogoUri = Uri.parse(extras.getString("awayUri"));
        if (extras != null) {
            txtHome.setText(extras.getString("homeTeam"));
            txtAway.setText(extras.getString("awayTeam"));
            try {
                Bitmap homeBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), homeLogoUri);
                Bitmap awayBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), awayLogoUri);
                homeImage.setImageBitmap(homeBitmap);
                awayImage.setImageBitmap(awayBitmap);
            } catch (IOException e) {
                Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
                Log.e(TAG, e.getMessage());
            }
        }
        //2.Tombol add score menambahkan satu angka dari angka 0, setiap kali di tekan

        //3.Tombol Cek Result menghitung pemenang dari kedua tim dan mengirim nama pemenang ke ResultActivity, jika seri di kirim text "Draw"
    }

    public void HandleAddScoreHome(View view) {
        homeScoreVal = Integer.valueOf(scoreHome.getText().toString());
        homeScoreVal += 1;
        scoreHome.setText(String.valueOf(homeScoreVal));
    }

    public void handleAddScoreAway(View view) {
        awayScoreVal = Integer.valueOf(scoreAway.getText().toString());
        awayScoreVal += 1;
        scoreAway.setText(String.valueOf(awayScoreVal));
    }

    public void handleResult(View view) {
        Intent i = new Intent(MatchActivity.this, ResultActivity.class);
        if (homeScoreVal > awayScoreVal){
            i.putExtra("result", "The winner is " + txtHome.getText().toString());
        }else if(homeScoreVal < awayScoreVal){
            i.putExtra("result", "The winner is " + txtAway.getText().toString());
        }else{
            i.putExtra("result","The result is Draw");
        }
        startActivity(i);
    }
}
