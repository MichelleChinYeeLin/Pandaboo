package com.example.pandaboo;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Shop extends AppCompatActivity{

    private Button backButton;
    private GridView skinsGridView;
    private ArrayList<Item> itemArrayList = new ArrayList<Item>();
    private TextView bambooCurrency;
    private int bambooNum;

    final String firebaseURL = "https://pandaboodcs-default-rtdb.asia-southeast1.firebasedatabase.app";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop);

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        skinsGridView = findViewById(R.id.skinsGridView);
        bambooCurrency = findViewById(R.id.bambooNumber);

        DatabaseReference userReference = FirebaseDatabase.getInstance(firebaseURL).getReference().child("admin").child("User");
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bambooNum = snapshot.child("Bamboo").getValue(int.class);
                bambooCurrency.setText(Integer.toString(bambooNum));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference reference = FirebaseDatabase.getInstance(firebaseURL).getReference().child("admin").child("Item");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                itemArrayList.clear();

                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    String itemID = dataSnapshot.child("ItemID").getValue(String.class);
                    String itemName = dataSnapshot.child("ItemName").getValue(String.class);
                    int itemPrice = dataSnapshot.child("ItemPrice").getValue(int.class);
                    String itemRoomImage = dataSnapshot.child("ItemRoomImage").getValue(String.class);
                    String itemTimerImage = dataSnapshot.child("ItemTimerImage").getValue(String.class);
                    boolean isOwned = dataSnapshot.child("IsOwned").getValue(boolean.class);

                    itemArrayList.add(new Item(itemID + "", itemName + "", itemPrice, itemRoomImage, itemTimerImage, isOwned));
                }

                ItemGVAdapter adapter = new ItemGVAdapter(Shop.this, itemArrayList);
                skinsGridView.setAdapter(adapter);
                Toast.makeText(Shop.this, "Success.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Shop.this, "Error loading image.", Toast.LENGTH_SHORT).show();
            }
        });

        skinsGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Item item = itemArrayList.get(position);

                if (item.getIsOwned() == false){
                    purchaseSkin(item);
                }

                else{
                    equipSkin(item);
                }
            }
        });
    }

    public void purchaseSkin(Item item){
        AlertDialog dialog;
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        dialogBuilder.setView(inflater.inflate(R.layout.shop_purchase, null));
        dialog = dialogBuilder.create();

        dialog.show();

        ImageView previewArea = dialog.findViewById(R.id.previewArea);
        Button purchaseYes = dialog.findViewById(R.id.purchaseYes);
        Button purchaseNo = dialog.findViewById(R.id.purchaseNo);

        Picasso.get().load(item.getItemRoomImage()).into(previewArea);

        purchaseYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bambooNum >= item.getItemPrice()){

                    bambooNum -= item.getItemPrice();
                    DatabaseReference reference = FirebaseDatabase.getInstance(firebaseURL).getReference().child("admin");
                    reference.child("User").child("EquippedItemRoom").setValue(item.getItemRoomImage());
                    reference.child("User").child("EquippedItemTimer").setValue(item.getItemTimerImage());
                    reference.child("User").child("Bamboo").setValue(bambooNum);
                    reference.child("Item").child(item.getItemID()).child("IsOwned").setValue(true);

                    dialog.dismiss();
                }

                else{
                    Toast.makeText(Shop.this, "Insufficient bamboo.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        purchaseNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public void equipSkin(Item item){
        AlertDialog dialog;
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        dialogBuilder.setView(inflater.inflate(R.layout.shop_equip, null));
        dialog = dialogBuilder.create();

        dialog.show();

        ImageView previewArea = dialog.findViewById(R.id.previewArea);
        Button equipYes = dialog.findViewById(R.id.equipYes);
        Button equipNo = dialog.findViewById(R.id.equipNo);

        Picasso.get().load(item.getItemRoomImage()).into(previewArea);

        equipYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference reference = FirebaseDatabase.getInstance(firebaseURL).getReference().child("admin");
                reference.child("User").child("EquippedItemRoom").setValue(item.getItemRoomImage());
                reference.child("User").child("EquippedItemTimer").setValue(item.getItemTimerImage());
                dialog.dismiss();
            }
        });

        equipNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
