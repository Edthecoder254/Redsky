package com.marvedie.redskyshop.take2;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.marvedie.redskyshop.R;

public  class AddPostActivity extends AppCompatActivity {

    EditText mTitleEt, mDescrEt;
    ImageView mPostIv;
    Button mUploadBtn;

    //Folder path for Firebase Storage
    String mStoragePath = "All_Image_Upload/";

    //Root database name
    String mDatabasePath = "Data";

    //Creating URi
    Uri mFilePathUri;

    //Creating Storage Reference and Database Reference
    StorageReference mStorageReference;
    DatabaseReference mDatabaseReference;

    //Progress Dialog
    ProgressDialog mProgressDialog;

    //Image Request code for choosing Image
    int IMAGE_REQUEST_CODE = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        //Action Bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add New Post");

        mTitleEt = findViewById(R.id.pTitleEt);
        mDescrEt = findViewById(R.id.pDescrEt);
        mPostIv = findViewById(R.id.pImageIv);
        mUploadBtn = findViewById(R.id.pUploadBtn);


        //Image click to choose Image
        mPostIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating Intent
                Intent intent = new Intent();
                //setting intent type as image to select image from phone storage
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"),IMAGE_REQUEST_CODE);

            }
        });

        //button click to upload data to firebase storage
        mUploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadDataToFirebase();
            }
        });
        //Assign firebase storage instance to firebase storage reference object
        mStorageReference = FirebaseStorage.getInstance().getReference();

        //assign firebaseDatabase instance with root database name
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(mDatabasePath);

        //Progress Dialog
        mProgressDialog = new ProgressDialog(AddPostActivity.this);

    }
    private String getFileExtension(Uri  uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        //return file
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST_CODE
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null){
            mFilePathUri = data.getData();

            try{
                //GETTING selected inage into bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mFilePathUri);
                //setting bitmap into Imageview
                mPostIv.setImageBitmap(bitmap);
            }
            catch (Exception e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }


        }
    }


    private void UploadDataToFirebase() {
        //Check whether filepath uri is empty
        if (mFilePathUri != null) {
            //setting progress bar title
            mProgressDialog.setTitle("Uploading...");
            mProgressDialog.show();

            //create second Storage reference
            StorageReference storageReference2nd = mStorageReference.child(mStoragePath + System.currentTimeMillis()+ "." + getFileExtension(mFilePathUri));

            //adding add on click listener to storagereference2nd
            storageReference2nd.putFile(mFilePathUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    //get title
                    String mPostTitle = mTitleEt.getText().toString().trim();
                    //get description
                    String mPostDescr = mDescrEt.getText().toString().trim();
                    //hide progress dialog
                    mProgressDialog.dismiss();
                    //show toast image has been uploaded
                    Toast.makeText(AddPostActivity.this, "Uploaded successfully", Toast.LENGTH_SHORT).show();

                    ImageUploadInfo imageUploadInfo = new ImageUploadInfo( mPostTitle, mPostDescr,taskSnapshot.getDownloadUrl().toString(), mPostTitle.toLowerCase());
                    //getting image upload id
                    String imageUploadId = mDatabaseReference.push().getKey();
                    //adding image upload id's child element into database reference

                    mDatabaseReference.child(imageUploadId).setValue(imageUploadInfo);
                }
            })
                    //if sth goes wrong like a network failure etc
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //hide progress dialog
                            mProgressDialog.dismiss();
                            //show error toast
                            Toast.makeText(AddPostActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            mProgressDialog.setTitle("Uploading.....");
                        }
                    });
        } else {
            Toast.makeText(this, "Please select image or add image name", Toast.LENGTH_SHORT).show();
        }
    }

    //method to get selected image file extension from the file path uri

}
