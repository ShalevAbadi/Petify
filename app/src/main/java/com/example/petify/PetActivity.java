package com.example.petify;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

public class PetActivity extends AppCompatActivity {
    private Pet pet;
    private RelativeLayout flexboxesContainer;
    private FirebaseUser user;
    private DatabaseReference RootRef;
    private  EditText aboutPet;
    private ImageView aboutBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_main);
        pet = (Pet) getIntent().getSerializableExtra("Pet");
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            RootRef = FirebaseDatabase.getInstance().getReference();
            resetDailyDataIfNeeded();
            flexboxesContainer = findViewById(R.id.pet_container);
            aboutPet = (EditText) findViewById(R.id.about_pet);
            aboutPet.setText(pet.getAbout());
            aboutPet.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    ImageView about_btn = findViewById(R.id.about_btn);
                    about_btn.setVisibility(View.VISIBLE);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            aboutBtn = findViewById(R.id.about_btn);
            aboutBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pet.setAbout(aboutPet.getText().toString());
                    updateDB();
                    aboutBtn.setVisibility(View.INVISIBLE);
                }
            });
            renderMeals();
            renderWalks();
            renderName();
        }
    }

    private void resetDailyDataIfNeeded(){
        Calendar c1 = Calendar.getInstance();

        Calendar c2 = Calendar.getInstance();
        c2.setTime(new Date(pet.getLastReset()));

        if ((c1.get(Calendar.YEAR) > c2.get(Calendar.YEAR)) || (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                && c1.get(Calendar.DAY_OF_YEAR) > c2.get(Calendar.DAY_OF_YEAR))){
            pet.setWalksDone(0);
            pet.setMealsGiven(0);
            pet.setAbout("");
            pet.setLastReset(System.currentTimeMillis());
            updateDB();
        }
    }

    private void feed() {
        if (pet.getMealsGiven() < pet.getDailyMeals()) {
            pet.setMealsGiven(pet.getMealsGiven() + 1);
            renderMeals();
            updateDB();
        }
    }

    private void unFeed() {
        if (pet.getMealsGiven() > 0) {
            pet.setMealsGiven(pet.getMealsGiven() - 1);
            renderMeals();
            updateDB();
        }
    }

    private void walk() {
        if (pet.getWalksDone() < pet.getDailyWalks()) {
            pet.setWalksDone(pet.getWalksDone() + 1);
            renderWalks();
            updateDB();
        }
    }

    private void unWalk() {
        if (pet.getWalksDone() > 0) {
            pet.setWalksDone(pet.getWalksDone() - 1);
            renderWalks();
            updateDB();
        }
    }

    private void updateDB() {
        RootRef.child(pet.getOwner().trim()).child("Pets").child(pet.getId().toString()).setValue(pet);
    }

    private void renderName() {
        TextView nameView = findViewById(R.id.pet_name_text_view);
        nameView.setText(pet.getName());
    }

    private void renderMeals() {
        int mealWidth = 250;
        int mealHeight = 250;
        FlexboxLayout flexboxLayout = (FlexboxLayout) findViewById(R.id.flex_box_meals);
        flexboxLayout.setFlexDirection(FlexDirection.ROW);
        flexboxLayout.setForegroundGravity(View.TEXT_ALIGNMENT_CENTER);
        flexboxLayout.removeAllViews();
        for (int i = 0; i < pet.getMealsGiven(); i++) {
            ImageView imageView_pet = new ImageView(this);
            imageView_pet.setImageResource(R.drawable.food_positive);
            ViewGroup.LayoutParams vlp = new ViewGroup.LayoutParams(mealWidth, mealHeight);
            imageView_pet.setLayoutParams(vlp);
            imageView_pet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    unFeed();
                }
            });
            flexboxLayout.addView(imageView_pet);
        }
        for (int i = 0; i < pet.getDailyMeals() - pet.getMealsGiven(); i++) {
            ImageView imageView_pet = new ImageView(this);
            imageView_pet.setImageResource(R.drawable.food_negative);
            ViewGroup.LayoutParams vlp = new ViewGroup.LayoutParams(mealWidth, mealHeight);
            imageView_pet.setLayoutParams(vlp);
            imageView_pet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    feed();
                }
            });
            flexboxLayout.addView(imageView_pet);
        }
    }

    private void renderWalks() {
        int mealWidth = 250;
        int mealHeight = 250;
        FlexboxLayout flexboxLayout = (FlexboxLayout) findViewById(R.id.flex_box_walks);
        flexboxLayout.setFlexDirection(FlexDirection.ROW);
        flexboxLayout.setForegroundGravity(View.TEXT_ALIGNMENT_CENTER);
        flexboxLayout.removeAllViews();
        for (int i = 0; i < pet.getWalksDone(); i++) {
            ImageView imageView_pet = new ImageView(this);
            imageView_pet.setImageResource(R.drawable.walk_positive);
            ViewGroup.LayoutParams vlp = new ViewGroup.LayoutParams(mealWidth, mealHeight);
            imageView_pet.setLayoutParams(vlp);
            imageView_pet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    unWalk();
                }
            });
            flexboxLayout.addView(imageView_pet);
        }
        for (int i = 0; i < pet.getDailyWalks() - pet.getWalksDone(); i++) {
            ImageView imageView_pet = new ImageView(this);
            imageView_pet.setImageResource(R.drawable.walk_negative);
            ViewGroup.LayoutParams vlp = new ViewGroup.LayoutParams(mealWidth, mealHeight);
            imageView_pet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    walk();
                }
            });
            imageView_pet.setLayoutParams(vlp);
            flexboxLayout.addView(imageView_pet);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.family_menu_btn:
                startActivity(new Intent(this, com.example.petify.FamilyActivity.class));
                return true;
            case R.id.pets_menu_btn:
                startActivity(new Intent(this, PetsListActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

