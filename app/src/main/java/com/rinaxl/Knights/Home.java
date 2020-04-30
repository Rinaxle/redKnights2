package com.rinaxl.Knights;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.rinaxl.Knights.Model.Upload;
import com.squareup.picasso.Picasso;


public class Home extends AppCompatActivity {
    private static final int CHOOSE_IMAGE = 1;

    private Button chooseImage, btnUploadImage;
    private TextView viewGallery;
    private ImageView imgPreview;
    private EditText imgDescription;
    private ProgressBar uploadProgress;

    private Uri imgUrl;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    private StorageTask mUploadTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        final String transactionKey = intent.getStringExtra("Transaction Key");

        uploadProgress = findViewById(R.id.uploadProgress);
        chooseImage = findViewById(R.id.chooseImage);
        btnUploadImage = findViewById(R.id.btnUploadImage);

        viewGallery = findViewById(R.id.viewGallery);

        imgDescription = findViewById(R.id.imgDescription);
        imgPreview = findViewById(R.id.imgPreview);

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Transactions");

        viewGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, ViewImageActivity.class);
                startActivity(intent);
            }
        });
        btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(Home.this, "Upload in progress", Toast.LENGTH_LONG).show();
                } else {
                    uploadImage(transactionKey);
                }
            }
        });


        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChoose();
            }
        });
    }

    private void showFileChoose() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, CHOOSE_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgUrl = data.getData();

            Picasso.get().load(imgUrl).into(imgPreview);
        }

    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }


    private void uploadImage(final String transactionKey) {
        if (imgUrl != null) {
            final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(imgUrl));

            mUploadTask = fileReference.putFile(imgUrl)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    uploadProgress.setProgress(0);
                                }
                            }, 500);
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Upload upload = new Upload(imgDescription.getText().toString().trim(), uri.toString());
                                   // String uploadID = mDatabaseRef.push().getKey();
                                    mDatabaseRef.child(transactionKey).child("imgUrl").setValue(uri.toString());
                                    mDatabaseRef.child(transactionKey).child("imgName").setValue(imgDescription.getText().toString().trim());
                                    Toast.makeText(Home.this, "Upload successfully", Toast.LENGTH_LONG).show();
                                //    imgPreview.setImageResource(R.drawable.imagepreview);
                                    imgDescription.setText("");
                                }
                            });


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Home.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            uploadProgress.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(Home.this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }
}