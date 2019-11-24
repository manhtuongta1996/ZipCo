package com.example.zipco;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiHandler {
    @GET("giftcards")
    Call<List<GiftCard>> getGiftCard();
}
