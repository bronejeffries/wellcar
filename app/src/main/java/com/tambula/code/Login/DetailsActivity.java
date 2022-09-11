package com.tambula.code.Login;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;

import com.addisonelliott.segmentedbutton.SegmentedButton;
import com.addisonelliott.segmentedbutton.SegmentedButtonGroup;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceDetection;
import com.google.mlkit.vision.face.FaceDetector;
import com.google.mlkit.vision.face.FaceDetectorOptions;
import com.tambula.code.Objects.CustomerObject;
import com.tambula.code.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;


/**
 * Fragment Responsible for registering a new user
 */
public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int MY_CAMERA_PERMISSION_CODE = 101;
    private EditText mName;

    private Uri resultUri;
    private Uri resultUr2;

    private FaceImageView mProfileImage;

    private ImageView mNinImage;

    private SegmentedButtonGroup mRadioGroup;

    CustomerObject mCustomer;

    private DatabaseReference mDatabase;

    private  int request;
    private SegmentedButton providerButton;
    private SegmentedButton customerButton;

    private  int imgcode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        initializeObjects();

    }


    /**
     * Register the user, but before that check if every field is correct.
     * After that registers the user and creates an entry for it oin the database
     */
    private void register() {

        if (mName.getText().length() == 0) {
            mName.setError("please fill this field");
            return;
        }

        if(mProfileImage.getDrawable() == null){
            Toast.makeText(this, "Profile Image Is Empty. " , Toast.LENGTH_SHORT).show();
            return;
        }

        final String name = mName.getText().toString();

        final String accountType;
        int selectId = mRadioGroup.getPosition();

        String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        if (selectId == 1) {
            accountType = "Drivers";

        } else {
            accountType = "Customers";
        }

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(accountType).child(user_id);

        Map<String, Object> newUserMap = new HashMap<>();

        newUserMap.put("name", name);
        newUserMap.put("profileImageUrl", "default");
        newUserMap.put("ninImageUrl", "default");

        if (accountType.equals("Drivers")) {
            newUserMap.put("service", "type_1");
            newUserMap.put("activated", true);
        }
        FirebaseDatabase.getInstance().getReference().child("Users").child(accountType).child(user_id).updateChildren(newUserMap).addOnCompleteListener((OnCompleteListener<Void>) task -> {

            if(resultUri != null) {

                final StorageReference filePath = FirebaseStorage.getInstance().getReference().child("profile_images").child(user_id);
                UploadTask uploadTask = filePath.putFile(resultUri);

                uploadTask.addOnFailureListener(e -> {
                    finish();
                });
                uploadTask.addOnSuccessListener(taskSnapshot -> filePath.getDownloadUrl().addOnSuccessListener(uri -> {
                    Map newImage = new HashMap();

                    newImage.put("profileImageUrl", uri.toString());

                    mDatabase.updateChildren(newImage);

                    finish();
                }).addOnFailureListener(exception -> {
                    finish();
                }));
            }else{
                finish();
            }

            if(resultUr2 != null) {

                final StorageReference filePath = FirebaseStorage.getInstance().getReference().child("profile_images").child(user_id);
                UploadTask uploadTask = filePath.putFile(resultUr2);

                uploadTask.addOnFailureListener(e -> {
                    finish();
                });
                uploadTask.addOnSuccessListener(taskSnapshot -> filePath.getDownloadUrl().addOnSuccessListener(uri -> {
                    Map newImage = new HashMap();
                    newImage.put("ninImageUrl", uri.toString());

                    mDatabase.updateChildren(newImage);

                    finish();
                }).addOnFailureListener(exception -> {
                    finish();
                }));
            }else{
                finish();
            }

            Intent intent = new Intent(DetailsActivity.this, LauncherActivity.class);
            startActivity(intent);
            finish();
        });
    }


    /**
     * Initializes the design Elements and calls clickListeners for them
     */
    private void initializeObjects() {

        mName = findViewById(R.id.name);
        Button mRegister = findViewById(R.id.register);
        mRadioGroup = findViewById(R.id.radioRealButtonGroup);

        mRadioGroup.setPosition(0, false);

        mProfileImage = findViewById(R.id.profileImage);

        mNinImage = findViewById(R.id.ninImage);

        mNinImage.setOnClickListener(v -> {

            if (ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            {
                imgcode = 2;
                ActivityCompat.requestPermissions(DetailsActivity.this,new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_CAMERA_PERMISSION_CODE);

            }
            else
            {

                getImage(2);

            }

        });

        mProfileImage.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            {
                imgcode = 1;
                ActivityCompat.requestPermissions(DetailsActivity.this,new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_CAMERA_PERMISSION_CODE);
            }
            else
            {

                getImage(1);


            }
        });

       providerButton = findViewById(R.id.radioProviderButton);
       customerButton = findViewById(R.id.radioCustomerButton);

        providerButton.setOnClickListener(this);

        customerButton.setOnClickListener(this);

        // mRegister.setOnClickListener(this);
    }

    private void getImage(int i) {

        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
       // intent.putExtra("android.intent.extras.CAMERA_FACING", 1);
        //intent.setType("image/*");
        startActivityForResult(intent, i);
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.register) {

            register();
        }

        else if(view.getId() == R.id.radioCustomerButton){

            register();

        }
        else if(view.getId() == R.id.radioProviderButton){

            register();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        request = requestCode;
        if(requestCode == 1 && resultCode == Activity.RESULT_OK){
            //resultUri = data.getData();
            try {

                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                int rotationDegree = 0;
                // [START image_from_bitmap]

                InputImage image = InputImage.fromBitmap(bitmap, rotationDegree);

                Bitmap cropped =  mProfileImage.detectFace(image,bitmap);

               if(cropped != null) {
                   String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), cropped, "passport", null);
                   resultUri = Uri.parse(path);

                   Glide.with(getApplication())
                           .load(cropped) // Uri of the picture
                           .apply(RequestOptions.circleCropTransform())
                           .into(mProfileImage);
               }
               else{

                   Toast.makeText(this, "Face Not Detected", Toast.LENGTH_LONG).show();

               }


            } catch (Exception e) {

                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();

            }
        }

        if(requestCode == 2 && resultCode == Activity.RESULT_OK){
            resultUr2 = data.getData();
            try {
                //Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUr2);
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                //String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), bitmap, "", null);
                //resultUr2 =  Uri.parse(path) ;

                Glide.with(getApplication())
                        .load(bitmap) // Uri of the picture
                        .apply(RequestOptions.circleCropTransform())
                        .into(mNinImage);


            } catch (Exception e) {

                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                //e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getImage(imgcode);

            } else {

                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }




}