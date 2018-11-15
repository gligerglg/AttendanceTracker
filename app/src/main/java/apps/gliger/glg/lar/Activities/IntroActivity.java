package apps.gliger.glg.lar.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroFragment;

import apps.gliger.glg.lar.Fragments.SampleSlide;
import apps.gliger.glg.lar.R;

public class IntroActivity extends AppIntro2 {

    private boolean isFirstTime;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences("lar_shared", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        isFirstTime = getIntent().getBooleanExtra("first_time",false);

        addSlide(SampleSlide.newInstance(R.layout.intro_page_1));
        addSlide(SampleSlide.newInstance(R.layout.intro_page_2));
        addSlide(SampleSlide.newInstance(R.layout.intro_page_3));
        addSlide(SampleSlide.newInstance(R.layout.intro_page_4));
        addSlide(SampleSlide.newInstance(R.layout.intro_page_5));
        addSlide(SampleSlide.newInstance(R.layout.intro_page_6));
        addSlide(SampleSlide.newInstance(R.layout.intro_page_7));

        setFadeAnimation();
        setBackButtonVisibilityWithDone(false);
        showSkipButton(false);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        if(isFirstTime){
            editor.putBoolean("first_time",false);
            editor.commit();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            finish();
        }
        else
            finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
    }
}
