package com.example.pandaboo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Panda extends AppCompatActivity {

    final String firebaseURL = "https://pandaboodcs-default-rtdb.asia-southeast1.firebasedatabase.app";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.panda);

        ImageView roomSkin = findViewById(R.id.roomSkin);
        Button backButton = findViewById(R.id.backButton);

        DatabaseReference reference = FirebaseDatabase.getInstance(firebaseURL).getReference().child("admin");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String roomSkinURL = snapshot.child("User").child("EquippedItemRoom").getValue(String.class);
                Picasso.get().load(roomSkinURL).into(roomSkin);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Panda.this, "Purchase Failed.", Toast.LENGTH_SHORT).show();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
