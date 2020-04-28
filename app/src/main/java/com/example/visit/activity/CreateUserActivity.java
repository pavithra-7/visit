package com.example.visit.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.visit.BuildConfig;
import com.example.visit.R;
import com.example.visit.model.CityData;
import com.example.visit.model.DistrictData;
import com.example.visit.model.StateData;
import com.example.visit.model.UsersModel;
import com.example.visit.utils.FileCompressor;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateUserActivity extends AppCompatActivity {

    public static final int REQUEST_TAKE_PHOTO = 1;
    public static final int REQUEST_GALLERY_PHOTO = 2;
    File mPhotoFile;
    FileCompressor mCompressor;
    @BindView(R.id.imgProfile)
    ImageView imgProfile;
    @BindView(R.id.etName)
    TextInputEditText etName;
    @BindView(R.id.etEmail)
    TextInputEditText etEmail;
    @BindView(R.id.etPhone)
    TextInputEditText etPhone;
    @BindView(R.id.etWhomtomeet)
    TextInputEditText etWhomtomeet;
    @BindView(R.id.etPurposetomeet)
    TextInputEditText etPurposetomeet;
    @BindView(R.id.etAddress)
    TextInputEditText etAddress;
    @BindView(R.id.spinstate)
    Spinner spinstate;
    @BindView(R.id.spinCity)
    Spinner spinCity;
    @BindView(R.id.spinDistrict)
    Spinner spinDistrict;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    DatabaseReference databaseReference;
    DatabaseReference databaseReferenceState;
    DatabaseReference databaseReferenceDistrict;
    DatabaseReference databaseReferenceCity;

    String name, email, phone, whomToMeet, purposeToMeet, address, state, city, district;
    ArrayList<String> stateList, districtList, cityList;

    ProgressDialog progressDialog, regProgress;
    StorageReference storageReference;
    Uri selectedImage;
    Bitmap photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Create User");
        mCompressor = new FileCompressor(CreateUserActivity.this);

        storageReference = FirebaseStorage.getInstance().getReference();

        stateList = new ArrayList<String>();
        stateList.add("Select State Name");

        districtList = new ArrayList<>();
        districtList.add("Select District Name");

        cityList = new ArrayList<>();
        cityList.add("Select City Name");

        databaseReference = FirebaseDatabase.getInstance().getReference("UserDetails");
        databaseReferenceState = FirebaseDatabase.getInstance().getReference("State_Details");
        databaseReferenceDistrict = FirebaseDatabase.getInstance().getReference("District_Details");
        databaseReferenceCity = FirebaseDatabase.getInstance().getReference().child("City_Details");

        regProgress = new ProgressDialog(CreateUserActivity.this);
        // Retrieving State Name
        databaseReferenceState.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    stateList.clear();
                    stateList.add("Select State Name");
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        String stateName = Objects.requireNonNull(dataSnapshot1.getValue(StateData.class)).getState();
                        stateList.add(stateName);
                    }

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(CreateUserActivity.this, R.layout.support_simple_spinner_dropdown_item, stateList);
                    arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    spinstate.setAdapter(arrayAdapter);

                    //Retrieving State Code as per State Name
                    spinstate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            String selectedState = spinstate.getSelectedItem().toString();


                            //Retrieving District Names based on State Selected
                            Query query1 = databaseReferenceDistrict.orderByChild("state").equalTo(selectedState);
                            query1.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        districtList.clear();
                                        districtList.add("Select District Name");
                                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                            String districtName = Objects.requireNonNull(dataSnapshot1.getValue(DistrictData.class)).getDistrictname();
                                            districtList.add(districtName);
                                        }

                                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(CreateUserActivity.this, R.layout.support_simple_spinner_dropdown_item, districtList);
                                        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                                        spinDistrict.setAdapter(arrayAdapter);


                                        spinDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                            @Override
                                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                String selectedDistrict = spinDistrict.getSelectedItem().toString();
                                                Query query = databaseReferenceCity.orderByChild("district").equalTo(selectedDistrict);
                                                query.addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        if (dataSnapshot.exists()) {
                                                            cityList.clear();
                                                            cityList.add("Select City Name");
                                                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                                String cityName = Objects.requireNonNull(dataSnapshot1.getValue(CityData.class)).getCity();
                                                                cityList.add(cityName);
                                                            }

                                                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(CreateUserActivity.this, R.layout.support_simple_spinner_dropdown_item, cityList);
                                                            arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                                                            spinCity.setAdapter(arrayAdapter);
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });

                                            }

                                            @Override
                                            public void onNothingSelected(AdapterView<?> parent) {

                                            }
                                        });

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    public void next() {

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        String imageId = databaseReference.push().getKey();

        name = Objects.requireNonNull(etName.getText()).toString().trim();
        email = Objects.requireNonNull(etEmail.getText()).toString().trim();
        phone = Objects.requireNonNull(etPhone.getText()).toString().trim();
        whomToMeet = Objects.requireNonNull(etWhomtomeet.getText()).toString().trim();
        purposeToMeet = Objects.requireNonNull(etPurposetomeet.getText()).toString().trim();
        address = Objects.requireNonNull(etAddress.getText()).toString().trim();
        state = spinstate.getSelectedItem().toString().trim();
        city = spinCity.getSelectedItem().toString().trim();
        district = spinDistrict.getSelectedItem().toString().trim();


        if (name.isEmpty()) {
            etName.setError("Please enter Name");
        } else if (email.isEmpty()) {
            etEmail.setError("Please enter Email ID");
        } else if (emailPattern.matches(email)) {
            etEmail.setError("Please enter Valid Email ID");
        } else if (phone.length() != 10) {
            etPhone.setError("Please enter Phone");
        } else if (whomToMeet.isEmpty()) {
            etWhomtomeet.setError("Please enter Whom To Meet");
        } else if (purposeToMeet.isEmpty()) {
            etPurposetomeet.setError("Please enter Purpose to Meet");
        } else if (address.isEmpty()) {
            etAddress.setError("Please enter Address");
        } else {

            StorageReference ref = storageReference.child("Images/" + imageId);
            ref.putFile(Uri.fromFile(mPhotoFile)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isSuccessful()) ;
                    Uri downloadUrl = uriTask.getResult();
                    assert downloadUrl != null;
                    UsersModel usersModel = new UsersModel(downloadUrl.toString(), imageId, name, email, phone, whomToMeet, purposeToMeet, address, state, city, district);
                    databaseReference.child(name).setValue(usersModel);
                    Toast.makeText(CreateUserActivity.this, "Added Successfully", Toast.LENGTH_SHORT).show();

                    regProgress.dismiss();


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    regProgress.dismiss();
                    Toast.makeText(CreateUserActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    regProgress.show();
                    regProgress.setCanceledOnTouchOutside(false);
                    regProgress.setCancelable(false);

                    double progress
                            = (100.0
                            * taskSnapshot.getBytesTransferred()
                            / taskSnapshot.getTotalByteCount());
                    regProgress.setMessage(
                            "Uploaded "
                                    + (int) progress + "%");
                }
            });


        }


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    @OnClick({R.id.imgProfile, R.id.btnSubmit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgProfile:
                selectImage();
                break;
            case R.id.btnSubmit:
                if (mPhotoFile == null) {
                    Toast.makeText(CreateUserActivity.this, "Please select Image", Toast.LENGTH_SHORT).show();
                } else {
                    next();
                }
                break;
        }
    }

    /**
     * Alert dialog for capture or select from galley
     */
    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(CreateUserActivity.this);
        builder.setItems(items, (dialog, item) -> {
            if (items[item].equals("Take Photo")) {
                requestStoragePermission(true);
            } else if (items[item].equals("Choose from Library")) {
                if(Build.VERSION.SDK_INT>22){
                    requestStoragePermission(false);
                }
            } else if (items[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    /**
     * Capture image from camera
     */
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
                // Error occurred while creating the File
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(CreateUserActivity.this,
                        BuildConfig.APPLICATION_ID + ".provider",
                        photoFile);

                mPhotoFile = photoFile;
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);

            }
        }
    }


    /**
     * Select image fro gallery
     */
    private void dispatchGalleryIntent() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(pickPhoto, REQUEST_GALLERY_PHOTO);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO) {
                try {
                    mPhotoFile = mCompressor.compressToFile(mPhotoFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Glide.with(CreateUserActivity.this).load(mPhotoFile).apply(new RequestOptions().centerCrop().circleCrop().placeholder(R.drawable.ic_add_a_photo_black_24dp)).into(imgProfile);
            } else if (requestCode == REQUEST_GALLERY_PHOTO) {
                Uri selectedImage = data.getData();
                try {
                    mPhotoFile = mCompressor.compressToFile(new File(getRealPathFromUri(selectedImage)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Glide.with(CreateUserActivity.this).load(mPhotoFile).apply(new RequestOptions().centerCrop().circleCrop().placeholder(R.drawable.ic_add_a_photo_black_24dp)).into(imgProfile);

            }
        }
    }

    /**
     * Requesting multiple permissions (storage and camera) at once
     * This uses multiple permission model from dexter
     * On permanent denial opens settings dialog
     */
    private void requestStoragePermission(boolean isCamera) {
        Dexter.withActivity(this).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            if (isCamera) {
                                dispatchTakePictureIntent();
                            } else {
                                dispatchGalleryIntent();
                            }
                        }
                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).withErrorListener(error -> Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show())
                .onSameThread()
                .check();
    }


    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    private void showSettingsDialog() {
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    /**
     * Create file with current timestamp name
     *
     * @return
     * @throws IOException
     */
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String mFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File mFile = File.createTempFile(mFileName, ".jpg", storageDir);
        return mFile;
    }

    /**
     * Get real file path from URI
     *
     * @param contentUri
     * @return
     */
    public String getRealPathFromUri(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = getContentResolver().query(contentUri, proj, null, null, null);
            assert cursor != null;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}

