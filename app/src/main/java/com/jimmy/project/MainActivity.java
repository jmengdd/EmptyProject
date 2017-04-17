package com.jimmy.project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();

//        LoginPresenter presenter = new LoginPresenter();
//
//        presenter.setCallback(new LoginPresenter.LoginCallback() {
//            @Override
//            public void onLoginSucceed(boolean success, String str) {
//                if (success) {
//                    Toast.makeText(getApplicationContext(), "Login succeeded!", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }

}
