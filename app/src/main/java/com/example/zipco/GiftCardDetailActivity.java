package com.example.zipco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class GiftCardDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.giftcard_details_layout);

        //Retrieve Data from MainActivity
        Intent intent = getIntent();
        final GiftCard giftCard = (GiftCard) intent.getSerializableExtra("GiftCard");

        MaterialButton giftcard_detail_checkoutBtn = (MaterialButton) findViewById(R.id.giftcard_detail_checkoutBtn);
        TextView giftcard_detail_brand = (TextView) findViewById(R.id.giftcard_detail_brand);
        final TextView giftcard_detail_discount = (TextView) findViewById(R.id.giftcard_detail_discount);
        final TextView giftcard_detail_terms = (TextView) findViewById(R.id.giftcard_detail_terms);
        final TextView giftcard_detail_fullterms = (TextView) findViewById(R.id.giftcard_detail_fullterms);

        // Using Picasso to cast the image to ImageView and rounding the corner
        ImageView giftcard_detail_image = (ImageView) findViewById(R.id.giftcard_detail_image) ;
        Picasso.get().load(giftCard.getImage())
                .fit()
                .centerInside()
                .transform(new RoundedTransformation(25, 0))
                .into(giftcard_detail_image);

        giftcard_detail_brand .setText(giftCard.getBrand());

        //Striping the trailing zero of discount value
        BigDecimal stripedVal = new BigDecimal(giftCard.getDiscount()).stripTrailingZeros();
        String discount = stripedVal.toPlainString();
        giftcard_detail_discount.setText(discount+"%");

        // Converting denomination list to String list and add Currency at the beginning
        List<GiftCard.Denominations> denominations = giftCard.getDenominations();
        ArrayList<String> denominationArrStr = new ArrayList<String>();
        for(GiftCard.Denominations denomination : denominations){
            if(denomination.getCurrency().equals("AUD")){
                denominationArrStr.add("$"+denomination.getPrice());
            } else {
                denominationArrStr.add(denomination.getCurrency()+denomination.getPrice());
            }
        }
        ArrayAdapter<String> denominationsArrayAdapter = new ArrayAdapter<>(getApplicationContext()
                , R.layout.giftcard_denominations_dropdown_menu
                , denominationArrStr);

        // Dropdown menu for denominations
        final AutoCompleteTextView denominationExposed = findViewById(R.id.filled_exposed_dropdown);
        denominationExposed.setAdapter(denominationsArrayAdapter);

        /*Toggle expandable terms and condition*/
        giftcard_detail_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final boolean visibility = giftcard_detail_fullterms.getVisibility() == View.VISIBLE;
                if(!visibility){
                    giftcard_detail_terms.setActivated(true);
                    giftcard_detail_fullterms.setVisibility(View.VISIBLE);
                    giftcard_detail_fullterms.animate().alpha(1).setStartDelay(500);
                } else {
                    giftcard_detail_terms.setActivated(false);
                    giftcard_detail_fullterms.setVisibility(View.GONE);
                    giftcard_detail_fullterms.setAlpha(0);
                }
                giftcard_detail_fullterms.setText(giftCard.getTerms());
            }
        });

        giftcard_detail_fullterms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final boolean visibility = giftcard_detail_fullterms.getVisibility() == View.VISIBLE;
                if(!visibility){
                    giftcard_detail_terms.setActivated(true);
                    giftcard_detail_fullterms.setVisibility(View.VISIBLE);
                    giftcard_detail_fullterms.animate().alpha(1).setStartDelay(500);
                } else {
                    giftcard_detail_terms.setActivated(false);
                    giftcard_detail_fullterms.setVisibility(View.GONE);
                    giftcard_detail_fullterms.setAlpha(0);
                }
            }
        });
        /*End of toggling expandable terms and condition*/

        // Checkout Button
        giftcard_detail_checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String denominationPrice = "";

                //Eliminate the currency at the beginning of the string, however, the code under here only works for currency == "AUD"
                if (denominationExposed.getText().toString().startsWith("$")){
                    denominationPrice += denominationExposed.getText().toString().substring(1);
                }

                //Constructing the URL
                String url = "https://zip.co/giftcards/checkout/"+giftCard.getId()+"?denomination="+denominationPrice;
                if(!denominationPrice.isEmpty()){
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(browserIntent);
                } else {
                    Toast.makeText(GiftCardDetailActivity.this,"Choose a value for gift card",Toast.LENGTH_LONG).show();
                }

            }
        });


    }


}
