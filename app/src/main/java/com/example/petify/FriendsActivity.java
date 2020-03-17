package com.example.petify;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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

public class FriendsActivity extends AppCompatActivity {
    private List<Friend> friends = new ArrayList<>();
    RelativeLayout l;
    private FirebaseUser user;
    private DatabaseReference FriendsRef;
    private DatabaseReference UsersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_main);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            l = findViewById(R.id.container);
            FriendsRef = FirebaseDatabase.getInstance().getReference().child("Friendships");
            UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");
            retrieveFriends();
            renderFriendsList();
            Button btn = findViewById(R.id.buttonAdd);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(FriendsActivity.this);
                    builder.setTitle("Add friend");
                    final EditText friendPhoneField = new EditText(FriendsActivity.this);
                    friendPhoneField.setHint("Enter friend phone without +");
                    builder.setView(friendPhoneField);
                    builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            final String phoneNumber = "+" + friendPhoneField.getText().toString().trim();
                            UsersRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                                        if (child.getKey().equals(phoneNumber)) {
                                            FriendsRef.child(user.getUid() + child.getValue(Friend.class).id.trim()).setValue(new Friendship(child.getValue(Friend.class), new Friend(user.getUid() + " ", user.getDisplayName(), user.getPhoneNumber())));
                                        }
                                    }
                                    renderFriendsList();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    });
                    builder.show();
                }
            });
        }
    }

    private void renderFriendsList() {
        int friendMargin = 10;
        int friendWidth = 400;
        int friendHeight = 400;
        FlexboxLayout flexboxLayout = (FlexboxLayout) findViewById(R.id.flex_box);
        flexboxLayout.setFlexDirection(FlexDirection.ROW);
        flexboxLayout.setForegroundGravity(View.TEXT_ALIGNMENT_CENTER);
        flexboxLayout.removeAllViews();
        for (int i = 0; i < friends.size(); i++) {
            RelativeLayout friend_layout = new RelativeLayout(this);
            friend_layout.setGravity(Gravity.CENTER_HORIZONTAL);
            RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            rlp.setMargins(friendMargin, friendMargin, friendMargin, friendMargin);
            ImageView imageView_friend = new ImageView(this);
            imageView_friend.setId(i + 1);
            imageView_friend.setImageResource(R.drawable.user);
            ViewGroup.LayoutParams vlp = new ViewGroup.LayoutParams(friendWidth, friendHeight);
            imageView_friend.setLayoutParams(vlp);
            friend_layout.addView(imageView_friend);
            TextView name = new TextView(this);
            name.setText(friends.get(i).getName());
            RelativeLayout.LayoutParams tlp = new RelativeLayout.LayoutParams(
                    friendWidth,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            tlp.addRule(RelativeLayout.BELOW, i + 1);
            name.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            name.setLayoutParams(tlp);
            friend_layout.addView(name);
            flexboxLayout.addView(friend_layout);
        }

    }

    private void retrieveFriends() {
        FriendsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Friendship temp = child.getValue(Friendship.class);
                    if (temp.isFriend1(user.getUid())) {
                        friends.add(temp.getFriend2());
                    } else if (temp.isFriend2(user.getUid())) {
                        friends.add(temp.getFriend1());
                    }
                }
                renderFriendsList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
            case R.id.friends_menu_btn:
                startActivity(new Intent(this, FriendsActivity.class));
                return true;
            case R.id.pets_menu_btn:
                startActivity(new Intent(this, PetsListActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}