package com.example.petify;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.sql.Timestamp;

public class NewPetActivity extends AppCompatActivity {


    private EditText editTextName;
    private EditText editTextMeals;
    private EditText editTextWalks;
    private FirebaseUser user;
    private DatabaseReference RootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pet);
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            RootRef = FirebaseDatabase.getInstance().getReference();
            editTextName = findViewById(R.id.editTextName);
            editTextMeals = findViewById(R.id.editTextMeals);
            editTextWalks = findViewById(R.id.editTextWalks);
            Button add = findViewById(R.id.buttonAdd);
            Button cancel = findViewById(R.id.buttonCancel);
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = editTextName.getText().toString().trim();
                    String meals = editTextMeals.getText().toString().trim();
                    String walks = editTextWalks.getText().toString().trim();
                    if (name.isEmpty() || name.length() < 1) {
                        editTextName.setError("Enter a valid name");
                        editTextName.requestFocus();
                        return;
                    } else if (meals.isEmpty() || meals.length() < 1) {
                        editTextMeals.setError("Enter a valid amount of meals");
                        editTextMeals.requestFocus();
                        return;
                    } else if (walks.isEmpty() || walks.length() < 1) {
                        editTextWalks.setError("Enter a valid amount of walks (0 for no walks)");
                        editTextWalks.requestFocus();
                        return;
                    }
                    addPet(name, Integer.parseInt(meals), Integer.parseInt(walks));
                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), PetsListActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            });
        }
    }

    void addPet(String name, int meals, int walks) {
        String currentUserID = user.getUid();
        Pet newPet= new Pet(System.currentTimeMillis(),currentUserID + " ",name, meals, walks);
        RootRef.child(currentUserID).child("Pets").child(newPet.getId().toString()).setValue(newPet);
        Intent intent = new Intent(getApplicationContext(), PetsListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


}