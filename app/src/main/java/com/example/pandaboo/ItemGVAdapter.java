package com.example.pandaboo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ItemGVAdapter extends ArrayAdapter<Item> {
    public ItemGVAdapter(@NonNull Context context, ArrayList<Item> itemArrayList){
        super(context, 0, itemArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View listitemView = convertView;
        if (listitemView == null){
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.card_item, parent, false);
        }

        Item item = getItem(position);
        TextView skinName = listitemView.findViewById(R.id.skinName);
        TextView skinPrice = listitemView.findViewById(R.id.skinPrice);
        ImageView skinImage = listitemView.findViewById(R.id.skinImage);
        ImageView bambooIcon = listitemView.findViewById(R.id.bambooIcon);
        RelativeLayout skinPriceArea = listitemView.findViewById(R.id.skinPriceArea);
        skinName.setText(item.getItemName());

        if (item.getIsOwned() == true){
            skinPrice.setText("Owned");
            bambooIcon.setVisibility(View.GONE);
            skinPriceArea.setBackgroundResource(R.drawable.card_item_price_owned);
        }
        else {
            skinPrice.setText(item.getItemPrice() + "");
            bambooIcon.setVisibility(View.VISIBLE);
            skinPriceArea.setBackgroundResource(R.drawable.card_item_price_not_owned);
        }

        Picasso.get().load(item.getItemRoomImage()).into(skinImage);
        return listitemView;
    }
}
