package com.jimmy.project.clients;

import android.util.Log;

import com.jimmy.project.APIs;
import com.jimmy.project.Presenters.LoginPresenter.LoginCallback;
import com.jimmy.project.SessionManager;
import com.jimmy.project.models.Token;
import com.jimmy.retrofit.RetrofitFactory;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginClient {

    private static final String PARAM_EMAIL = "email";
    private static final String PARAM_PASSWORD = "password";
    private static final String TAG = LoginClient.class.getName();

    private static APIs.TokenApi client = RetrofitFactory.createRetrofit().create(APIs.TokenApi.class);

    public static void loginWEmailAndPassword(
            String email,
            String password,
            final LoginCallback callback) {
        HashMap<String, String> params = new HashMap<>();
        params.put(PARAM_EMAIL, email);
        params.put(PARAM_PASSWORD, password);

        client.loginWithEmailAndPassword(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Token>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Token value) {
                        SessionManager.getInstance().setToken(value.token);
                        if (callback != null) {
                            callback.onLoginSucceed(true, "");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.getMessage());
                        if (callback != null) {
                            callback.onLoginSucceed(false, "Incorrect email/password!");
                        }
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}
