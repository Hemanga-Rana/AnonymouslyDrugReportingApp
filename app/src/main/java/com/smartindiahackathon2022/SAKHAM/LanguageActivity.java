package com.smartindiahackathon2022.SAKHAM;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.smartindiahackathon2022.SAKHAM.English.DeleteActivity;
import com.smartindiahackathon2022.SAKHAM.English.InfoActivity;
import com.smartindiahackathon2022.SAKHAM.English.sendotpActivivity;
import com.smartindiahackathon2022.SAKHAM.Hindi.sendotpHindiActivity;
import com.smartindiahackathon2022.SAKHAM.R;

public class LanguageActivity extends AppCompatActivity {


    private Button englishBtn;
    private Button hindiBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);



        englishBtn = findViewById(R.id.english_btn);
        hindiBtn = findViewById(R.id.hindi_btn);




        englishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), sendotpActivivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });


        hindiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), sendotpHindiActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });
    }
}