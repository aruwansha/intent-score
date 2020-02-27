package id.putraprima.skorbola;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getCanonicalName();
    private static final int HOME_IMAGE_KEY = 1;
    private static final int AWAY_IMAGE_KEY = 2;
    private ImageView homeImage, awayImage;

    private EditText homeTeam, awayTeam;
    private Button btnNext;
    private Uri homeUri, awayUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        homeTeam = findViewById(R.id.home_team);
        awayTeam = findViewById(R.id.away_team);
        homeImage = findViewById(R.id.home_logo);
        awayImage = findViewById(R.id.away_logo);
        btnNext = findViewById(R.id.btn_team);

        //TODO
        //Fitur Main Activity
        //1. Validasi Input Home Team
        //2. Validasi Input Away Team
        //3. Ganti Logo Home Team
        //4. Ganti Logo Away Team
        //5. Next Button Pindah Ke MatchActivity

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }

        if (requestCode == HOME_IMAGE_KEY) {
            if (data != null) {
                try {
                    homeUri = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), homeUri);
                    homeImage.setImageBitmap(bitmap);
                } catch (IOException e) {
                    Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, e.getMessage());
                }
            }
        }else if (requestCode == AWAY_IMAGE_KEY){
            if (data != null)  {
                try{
                    awayUri = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), awayUri);
                    awayImage.setImageBitmap(bitmap);
                }catch (IOException e){
                    Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();;
                    Log.e(TAG, e.getMessage());
                }
            }
        }
    }

    public void handleChangeHome(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, HOME_IMAGE_KEY);
    }

    public void handleChangeAway(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, AWAY_IMAGE_KEY);
    }

    public void handleNext(View view) {
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(homeTeam.getText().toString().length() == 0){
                    homeTeam.setError("Home field must be filled");
                }else if(awayTeam.getText().toString().equals(homeTeam.getText().toString())){
                    awayTeam.setError("Team name cannot be same");
                }else{
                    String homeTxt = homeTeam.getText().toString();
                    String awayTxt = awayTeam.getText().toString();
                    String homeUriString = homeUri.toString();
                    String awayUriString = awayUri.toString();
                    Intent i = new Intent(MainActivity.this, MatchActivity.class);
                    i.putExtra("homeTeam", homeTxt);
                    i.putExtra("awayTeam", awayTxt);
                    i.putExtra("homeUri", homeUriString);
                    i.putExtra("awayUri", awayUriString);
                    startActivity(i);
                }
            }
        });
    }
}
