package com.jimmy.project.Presenters;

import com.jimmy.project.clients.LoginClient;

public class LoginPresenter implements BasePresenter {
    LoginCallback callback;

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    public void setCallback(LoginCallback callback) {
        this.callback = callback;
    }

    public void login(String email, String password) {
        LoginClient.loginWEmailAndPassword(email, password, callback);
    }

    public interface LoginCallback {
        void onLoginSucceed(boolean success, String str);
    }
}
