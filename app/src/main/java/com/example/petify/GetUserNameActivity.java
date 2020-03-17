package com.example.petify;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class GetUserNameActivity extends AppCompatActivity {


    private EditText editTextName;
    private DatabaseReference RootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_user_name);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            RootRef = FirebaseDatabase.getInstance().getReference();
            editTextName = findViewById(R.id.editTextName);
            Button btn = findViewById(R.id.buttonContinue);
            btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                public void onClick(View v) {

                    String name = editTextName.getText().toString().trim();

                    if (name.isEmpty() || name.length() < 1) {
                        editTextName.setError("Enter a valid name");
                        editTextName.requestFocus();
                        return;
                    }
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(name)
                            .build();

                    user.updateProfile(profileUpdates)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        String currentUserID = user.getUid();
                                        Friend newFriend= new Friend(user.getUid() + " ",user.getDisplayName(), user.getPhoneNumber());
                                        RootRef.child("Users").child(user.getPhoneNumber()).setValue(newFriend);
                                        Intent intent = new Intent(GetUserNameActivity.this, ProfileActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }
                                }
                            });

                }
            });
        }

    }


}