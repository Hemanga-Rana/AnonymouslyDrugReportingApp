package com.smartindiahackathon2022.SAKHAM.English;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.smartindiahackathon2022.SAKHAM.R;

public class MainActivity extends AppCompatActivity {
    //Variables
    private TextInputLayout drugPlace, drugReport;
    private Button sendBtn;
    private ImageView imageView;
    private ImageView imageView3;
    private ImageView imageView5;
    private FirebaseDatabase rootNode;
    private DatabaseReference roots;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("users/*");
    private StorageReference reference = FirebaseStorage.getInstance().getReference();
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Hooks to all xml elements in activity_sign_up.xml
        drugPlace = findViewById(R.id.drug_place);
        drugReport = findViewById(R.id.drug_report);
        sendBtn = findViewById(R.id.send_btn);
        imageView = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        imageView5 = findViewById(R.id.imageView5);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent , 2);
            }
        });
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Location can be added.", Toast.LENGTH_SHORT).show();

            }
        });
        imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });
        //Save data in FireBase on button click
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageUri != null){
                    uploadToFirebase(imageUri);
                    Toast.makeText(MainActivity.this, "Your report send successfully", Toast.LENGTH_SHORT).show();

                    //Get all the values
                    String place = drugPlace.getEditText().getText().toString();
                    String report = drugReport.getEditText().getText().toString();
                    UserHelperClass helperClass = new UserHelperClass(place, report);
                    root.child(place).setValue(helperClass);
                    Intent intent = new Intent(getApplicationContext(), DeleteActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                else{
//                    Toast.makeText(SignUp.this, "Please Select Image", Toast.LENGTH_SHORT).show();
                    Toast.makeText(MainActivity.this, "Information Sent Successfully", Toast.LENGTH_SHORT).show();
                    rootNode = FirebaseDatabase.getInstance();
                    roots = rootNode.getReference("users");
                    //Get all the values
                    String place = drugPlace.getEditText().getText().toString();
                    String report = drugReport.getEditText().getText().toString();
                    UserHelperClass helperClass = new UserHelperClass(place, report);
                    roots.child(place).setValue(helperClass);
                    Intent intent = new Intent(getApplicationContext(), DeleteActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }

            }
        });//Register Button method end
    }//onCreate Method End


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode ==2 && resultCode == RESULT_OK && data != null){

            imageUri = data.getData();
            imageView.setImageURI(imageUri);

        }
    }

    private void uploadToFirebase(Uri uri){

        final StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        Model model = new Model(uri.toString());
                        String modelId = root.push().getKey();
                        root.child(modelId).setValue(model);
                        Toast.makeText(MainActivity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                        imageView.setImageResource(R.drawable.ic_baseline_add_circle_24);
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(MainActivity.this, "Uploading Failed !!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFileExtension(Uri mUri){

        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));

    }
}
