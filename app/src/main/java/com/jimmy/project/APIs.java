package com.jimmy.project;

import com.jimmy.project.models.Token;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by jimmy on 4/13/17.
 */

public class APIs {
    public interface TokenApi {
        @POST(Constants.LOGIN_POST_PATH)
        Observable<Token> loginWithEmailAndPassword(@Body Map<String, String> params);
    }
}
