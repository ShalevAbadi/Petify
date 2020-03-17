package com.example.petify;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class PetsListActivity extends AppCompatActivity {
    private List<Pet> petsList = new ArrayList<>();
    private FirebaseUser user;
    private DatabaseReference PetsRef;
    private DatabaseReference FriendsRef;
    private RelativeLayout l;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pets_list_main);
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            PetsRef = FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("Pets");
            FriendsRef = FirebaseDatabase.getInstance().getReference().child("Friendships");
            retrievePetsList();
            l = findViewById(R.id.container);
            renderPetsList();
        }
    }

    private void retrievePetsList(){
        PetsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    petsList.add(child.getValue(Pet.class));
                }
                renderPetsList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        FriendsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Friendship temp = child.getValue(Friendship.class);
                    if (temp.isFriend1(user.getUid())) {
                        retrievePetsFromFriendRef(FirebaseDatabase.getInstance().getReference().child(temp.getFriend2().getId().trim()).child("Pets"));
                    } else if (temp.isFriend2(user.getUid())) {
                        retrievePetsFromFriendRef(FirebaseDatabase.getInstance().getReference().child(temp.getFriend1().getId().trim()).child("Pets"));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void retrievePetsFromFriendRef(DatabaseReference friendPetsRef){
        friendPetsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    petsList.add(child.getValue(Pet.class));
                }
                renderPetsList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void renderPetsList() {
        int petMargin = 30;
        int petWidth = 400;
        int petHeight = 400;
        FlexboxLayout flexboxLayout = (FlexboxLayout) findViewById(R.id.flex_box);
        flexboxLayout.setFlexDirection(FlexDirection.ROW);
        flexboxLayout.setForegroundGravity(View.TEXT_ALIGNMENT_CENTER);
        flexboxLayout.removeAllViews();
        for (int i = 0; i < petsList.size(); i++) {
            RelativeLayout pet_layout = new RelativeLayout(this);
            pet_layout.setGravity(Gravity.CENTER_HORIZONTAL);
            RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            rlp.setMargins(petMargin, petMargin, petMargin, petMargin);
            ImageView imageView_pet = new ImageView(this);
            imageView_pet.setId(i+1);
            imageView_pet.setImageResource(R.drawable.pet_icon);
            ViewGroup.LayoutParams vlp = new ViewGroup.LayoutParams(petWidth-100, petHeight-100);
            imageView_pet.setLayoutParams(vlp);
            vlp = new ViewGroup.LayoutParams(petWidth, petHeight);
            imageView_pet.setLayoutParams(vlp);
            pet_layout.addView(imageView_pet);
            TextView name = new TextView(this);
            name.setText(petsList.get(i).getName());
            RelativeLayout.LayoutParams tlp = new RelativeLayout.LayoutParams(
                    petWidth,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            tlp.addRule(RelativeLayout.BELOW, i+1);
            name.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            name.setLayoutParams(tlp);
            pet_layout.addView(name);
            final Pet current_pet = petsList.get(i);
            pet_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), PetActivity.class);
                    intent.putExtra("Pet", current_pet);
                    startActivity(intent);
                }
            });
            flexboxLayout.addView(pet_layout);
        }
        Button newPetBtn = findViewById(R.id.buttonAdd);
        newPetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NewPetActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
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
