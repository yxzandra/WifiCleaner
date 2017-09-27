package com.hostienda.wificleaner.activities;

import android.os.Bundle;
import android.view.View;

import com.hostienda.wificleaner.R;
import com.hostienda.wificleaner.classes.BaseIntro;


/**
 * Created by Desarrollo on 3/11/2016.
 */

public class IntroActivity extends BaseIntro {
    @Override
    public void init(Bundle savedInstanceState) {
        addSlide(SampleSlideFragment.newInstance(R.layout.intro));
        addSlide(SampleSlideFragment.newInstance(R.layout.intro2));
        addSlide(SampleSlideFragment.newInstance(R.layout.intro3));
        setFlowAnimation();
    }

    @Override
    public void onSkipPressed() {
        loadMainActivity();
    }

    @Override
    public void onNextPressed() {

    }

    @Override
    public void onDonePressed() {
        loadMainActivity();
    }

    @Override
    public void onSlideChanged() {

    }

    public void getStarted(View v) {
        loadMainActivity();
    }

}