package com.example.petify;

import android.content.Intent;
import android.os.Bundle;
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

public class PetActivity extends AppCompatActivity {
    private Pet pet = new Pet(1, "Oliver", "The cutest dog in the world", 5, 6);
    private int walksDone = 2;
    private int mealsGiven = 1;
    private RelativeLayout flexboxesContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_main);
        flexboxesContainer = findViewById(R.id.pet_container);
        EditText etCentimeters = (EditText) findViewById(R.id.pet_edit_text);
        renderMeals();
        renderWalks();
        renderName();
    }

    private void feed() {
        if (mealsGiven < pet.getDailyMeals()) {
            mealsGiven++;
            renderMeals();
        }
    }

    private void unFeed() {
        if (mealsGiven > 0) {
            mealsGiven--;
            renderMeals();
        }
    }

    private void walk() {
        if (walksDone < pet.getDailyWalks()) {
            walksDone++;
            renderWalks();
        }
    }

    private void unWalk() {
        if (walksDone > 0) {
            walksDone--;
            renderWalks();
        }
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
        for (int i = 0; i < mealsGiven; i++) {
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
        for (int i = 0; i < pet.getDailyMeals() - mealsGiven; i++) {
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
        for (int i = 0; i < walksDone; i++) {
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
        for (int i = 0; i < pet.getDailyWalks() - walksDone; i++) {
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

