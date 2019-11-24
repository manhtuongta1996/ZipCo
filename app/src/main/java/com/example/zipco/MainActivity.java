package com.example.zipco;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RecyclerView giftCardRecyclerView = (RecyclerView) findViewById(R.id.giftcard_recyclerview);

        //Using retrofit to fetch json file via the api
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://zip.co/giftcards/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiHandler apiHandler = retrofit.create(ApiHandler.class);
        Call<List<GiftCard>> call = apiHandler.getGiftCard();

        call.enqueue(new Callback<List<GiftCard>>() {
            @Override
            public void onResponse(Call<List<GiftCard>> call, Response<List<GiftCard>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(MainActivity.this,"Code: "+ response.code() , Toast.LENGTH_LONG).show();
                    return;
                }
                List<GiftCard> giftCards = response.body();

                //Sorting based on alphabet
                Collections.sort(giftCards, new Comparator<GiftCard>() {
                    @Override
                    public int compare(GiftCard o1, GiftCard o2) {
                        return o1.getBrand().compareTo(o2.getBrand());
                    }
                });

                RecyclerViewAdapter giftCardRecyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, giftCards);
                giftCardRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
                ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(MainActivity.this, R.dimen.item_offset);
                giftCardRecyclerView.addItemDecoration(itemDecoration);
                giftCardRecyclerView.setAdapter(giftCardRecyclerViewAdapter);
            }

            @Override
            public void onFailure(Call<List<GiftCard>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error Failure", Toast.LENGTH_LONG).show();
            }
        });

    }
}
