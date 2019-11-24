package com.example.zipco;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.shape.RoundedCornerTreatment;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<GiftCard> giftCards;

    public RecyclerViewAdapter(Context mContext, List<GiftCard> giftCards) {
        this.mContext = mContext;
        this.giftCards = giftCards;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.giftcard_card_layout, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        // Get the gift card instance
        final GiftCard vhGiftCard = giftCards.get(position);

        holder.giftcard_brand.setText(vhGiftCard.getBrand());
        holder.giftcard_vendor.setText(vhGiftCard.getVendor());

        //Striping the trailing zero of discount value
        BigDecimal stripedVal = new BigDecimal(vhGiftCard.getDiscount()).stripTrailingZeros();
        String discount = stripedVal.toPlainString() + "%";
        holder.giftcard_discount.setText(discount);
        Picasso.get().load(vhGiftCard.getImage())
                .fit()                              // Fit image to imageView
                .centerInside()                     // Crop the image on top, right, left and bottom
                .transform(new RoundedTransformation(20, 0))            // Rounding Image corner
                .into(holder.giftcard_thumbnail);

        //Detail button
        holder.giftcard_detailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Passing GiftCard object to GiftCard Detail Activity
                Intent detailIntent = new Intent(mContext, GiftCardDetailActivity.class);
                detailIntent.putExtra("GiftCard", vhGiftCard);
                mContext.startActivity(detailIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return giftCards.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView giftcard_brand;
        TextView giftcard_vendor;
        TextView giftcard_discount;
        ImageView giftcard_thumbnail;
        MaterialButton giftcard_detailBtn;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            giftcard_brand = (TextView) itemView.findViewById(R.id.giftcard_brand);
            giftcard_vendor = (TextView) itemView.findViewById(R.id.giftcard_vendor);
            giftcard_discount = (TextView) itemView.findViewById(R.id.giftcard_discount);
            giftcard_thumbnail = (ImageView) itemView.findViewById(R.id.giftcard_thumbnail);
            giftcard_detailBtn = (MaterialButton) itemView.findViewById(R.id.giftcard_detailBtn);
        }
    }
}
