package com.example.petify;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayout;


public class PetsListActivity extends AppCompatActivity {

    private Pet[] petsList = {new Pet(1, "Oliver", "",3, 4), new Pet(2, "Melissa", "", 3, 5), new Pet(3, "Kai", "", 2, 4)};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pets_list_main);

        try {
            RelativeLayout container = findViewById(R.id.container);
            renderPetsList(container);
        } catch (Exception e) {
            Log.e("asdf", e.getMessage());
        }
    }

    private void renderPetsList(RelativeLayout l) {
        int petMargin = 10;
        int petWidth = 400;
        int petHeight = 400;
        FlexboxLayout flexboxLayout = (FlexboxLayout) findViewById(R.id.flex_box);
        flexboxLayout.setFlexDirection(FlexDirection.ROW);
        flexboxLayout.setForegroundGravity(View.TEXT_ALIGNMENT_CENTER);
        //FlexboxLayout.LayoutParams lp = new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //lp.setMargins(petMargin,petMargin,petMargin,petMargin);
        //flexboxLayout.setLayoutParams(lp);
        for (int i = 0; i < petsList.length; i++) {
            RelativeLayout pet_layout = new RelativeLayout(this);
            pet_layout.setGravity(Gravity.CENTER_HORIZONTAL);
            RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            rlp.setMargins(petMargin, petMargin, petMargin, petMargin);
            ImageView imageView_pet = new ImageView(this);
            imageView_pet.setId(petsList[i].getId());
            imageView_pet.setImageResource(R.drawable.profile_img);
            ViewGroup.LayoutParams vlp = new ViewGroup.LayoutParams(petWidth, petHeight);
            imageView_pet.setLayoutParams(vlp);
            pet_layout.addView(imageView_pet);
            TextView name = new TextView(this);
            name.setText(petsList[i].getName());
            RelativeLayout.LayoutParams tlp = new RelativeLayout.LayoutParams(
                    petWidth,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            tlp.addRule(RelativeLayout.BELOW, imageView_pet.getId());
            name.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            name.setLayoutParams(tlp);
            pet_layout.addView(name);
            flexboxLayout.addView(pet_layout);
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
                startActivity(new Intent(this, FamilyActivity.class));
                return true;
            case R.id.pets_menu_btn:
                startActivity(new Intent(this, PetsListActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
