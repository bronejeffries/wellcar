package com.tambula.code.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onesignal.OneSignal;
import com.tambula.code.Customer.CustomerMapActivity;
import com.tambula.code.Driver.DriverMapActivity;
import com.tambula.code.R;
import com.stripe.android.PaymentConfiguration;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * First activity of the app.
 * <p>
 * Responsible for checking if the user is logged in or not and call
 * the AuthenticationActivity or MainActivity depending on that.
 */
public class LauncherActivity extends AppCompatActivity{

    int triedTypes = 0;
    int service_post = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcer);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {

            startApis("");
            checkUserAccType();



        } else {

            Intent intent = new Intent(LauncherActivity.this, AuthenticationActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }


    /**
     * Check user account type, either customer or driver.
     * If it doesn't have a type then start the DetailsActivity for the
     * user to be able to pick one.
     *
     */
    private void checkUserAccType() {
        String userID;

        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference mCustomerDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(userID);
        mCustomerDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                    startApis("Customers");
                    Intent intent = new Intent(getApplication(), CustomerMapActivity.class);
                    intent.putExtra("POSITION_ID", service_post);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    startActivity(intent);
                    finish();
                } else {
                    checkNoType();
                }
            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {
            }
        });

        DatabaseReference mDriverDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child(userID);
        mDriverDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                    startApis("Drivers");
                    Intent intent = new Intent(getApplication(), DriverMapActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    checkNoType();
                }
            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {
            }
        });
    }

    /**
     * checks if both types have not been fetched meaning the DetailsActivity must be called
     */
    void checkNoType() {
        triedTypes++;
        if (triedTypes == 2) {
            Intent intent = new Intent(getApplication(), DetailsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }

    /**
     * starts up onesignal and stripe apis
     * @param type - type of the user (customer, driver)
     */
    void startApis(String type) {

        String userId;

        userId =  FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference str_customer = FirebaseDatabase.getInstance().getReference();

        Map<String, Object> newUserMap = new HashMap<>();

        newUserMap.put("card", "default");

        FirebaseDatabase.getInstance().getReference().child("stripe_customers").child(userId).updateChildren(newUserMap).addOnCompleteListener((OnCompleteListener<Void>) task->{

//            Toast.makeText(this, "stripe_customers", Toast.LENGTH_SHORT).show();

            FirebaseDatabase.getInstance().getReference().child("Users").child(type).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("notificationKey").setValue(userId);

            finish();


        });



    }


}

