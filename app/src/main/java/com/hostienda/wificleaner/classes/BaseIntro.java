package com.hostienda.wificleaner.classes;

import android.content.Intent;

import com.github.paolorotolo.appintro.AppIntro;
import com.hostienda.wificleaner.activities.HomeActivity;

/**
 * Created by Desarrollo on 3/11/2016.
 */
public class BaseIntro extends AppIntro {

    protected void loadMainActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
