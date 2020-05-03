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
import android.text.TextUtils;
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
import com.example.visit.model.DepartmentModel;
import com.example.visit.model.DistrictData;
import com.example.visit.model.StateData;
import com.example.visit.model.UsersModel;
import com.example.visit.utils.FileCompressor;
import com.example.visit.utils.MyAppPrefsManager;
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
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    String name, email, phone, whomToMeet, purposeToMeet, address, state, city, district;

    ProgressDialog progressDialog, regProgress;
    StorageReference storageReference;

    MyAppPrefsManager myAppPrefsManager;
    String departmentName;

    int deptUsersCount;


    String currentDate;
    String currentTime;

    DatabaseReference myRefStates, myRefDistricts, myRefCities, myRefDepartmentDetails;
    ArrayList<String> stateNameList, districtNameList, cityNameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Create User");

        myAppPrefsManager = new MyAppPrefsManager(this);

        departmentName = myAppPrefsManager.getDepartmentName();

        mCompressor = new FileCompressor(CreateUserActivity.this);

        storageReference = FirebaseStorage.getInstance().getReference();

        currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        currentTime = new SimpleDateFormat("hh:mm:sss", Locale.getDefault()).format(new Date());

        stateNameList = new ArrayList<String>();

        districtNameList = new ArrayList<String>();
        cityNameList = new ArrayList<String>();

        regProgress = new ProgressDialog(CreateUserActivity.this);
        progressDialog = new ProgressDialog(CreateUserActivity.this);

        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait....");

        myRefStates = FirebaseDatabase.getInstance().getReference("State_Details");
        myRefDistricts = FirebaseDatabase.getInstance().getReference("District_Details");
        myRefCities = FirebaseDatabase.getInstance().getReference().child("City_Details");
        databaseReference = FirebaseDatabase.getInstance().getReference("UserDetails");
        myRefDepartmentDetails = FirebaseDatabase.getInstance().getReference("DepartmentDetails");

        myRefDepartmentDetails.child(departmentName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    deptUsersCount = Integer.parseInt(Objects.requireNonNull(dataSnapshot.getValue(DepartmentModel.class)).getUserCount());
                    deptUsersCount = deptUsersCount + 1;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(CreateUserActivity.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        getStates();
        getDistrict("");
        getCity("");


    }


    public void getStates() {


        myRefStates.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    stateNameList.clear();
                    stateNameList.add("Select State");
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        String stateName = Objects.requireNonNull(dataSnapshot1.getValue(StateData.class)).getState();
                        stateNameList.add(stateName);
                    }

                    progressDialog.dismiss();
                } else {
                    progressDialog.dismiss();
                    stateNameList.clear();
                    stateNameList.add("Select State");
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(CreateUserActivity.this, R.layout.support_simple_spinner_dropdown_item, stateNameList);
                arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                spinstate.setAdapter(arrayAdapter);


                spinstate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        state = spinstate.getSelectedItem().toString();

                        getDistrict(state);


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                        getDistrict("");
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getDistrict(String selectedState) {


        progressDialog.show();

        //Retrieving District Names based on State Selected
        Query query1 = myRefDistricts.orderByChild("state").equalTo(selectedState);
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    districtNameList.clear();
                    districtNameList.add("Select District");
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        String districtName = Objects.requireNonNull(dataSnapshot1.getValue(DistrictData.class)).getDistrictname();
                        districtNameList.add(districtName);
                    }
                    progressDialog.dismiss();

                } else {
                    progressDialog.dismiss();
                    districtNameList.clear();
                    districtNameList.add("Select District");
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(CreateUserActivity.this, R.layout.support_simple_spinner_dropdown_item, districtNameList);
                arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                spinDistrict.setAdapter(arrayAdapter);

                //Retrieving Mandal Name as per District Name
                spinDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        district = spinDistrict.getSelectedItem().toString();

                        getCity(district);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                        getCity("");
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void getCity(String selectedDistrict) {

        progressDialog.show();
        Query query = myRefCities.orderByChild("district").equalTo(selectedDistrict);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    cityNameList.clear();
                    cityNameList.add("Select City");
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        String city = Objects.requireNonNull(dataSnapshot1.getValue(CityData.class)).getCity();
                        cityNameList.add(city);
                    }

                    progressDialog.dismiss();
                } else {
                    progressDialog.dismiss();
                    cityNameList.clear();
                    cityNameList.add("Select City");
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(CreateUserActivity.this, R.layout.support_simple_spinner_dropdown_item, cityNameList);
                arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                spinCity.setAdapter(arrayAdapter);

                spinCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        city = spinCity.getSelectedItem().toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void next() {


        String imageId = databaseReference.push().getKey();

        name = Objects.requireNonNull(etName.getText()).toString().trim();
        email = Objects.requireNonNull(etEmail.getText()).toString().trim();
        phone = Objects.requireNonNull(etPhone.getText()).toString().trim();
        whomToMeet = Objects.requireNonNull(etWhomtomeet.getText()).toString().trim();
        purposeToMeet = Objects.requireNonNull(etPurposetomeet.getText()).toString().trim();
        address = Objects.requireNonNull(etAddress.getText()).toString().trim();

        String checkInTime = currentDate + " " + currentTime;
        String checkOutTime = "";

        if (name.isEmpty()) {
            Toast.makeText(this, "Please enter Name", Toast.LENGTH_SHORT).show();
        } else if (email.isEmpty()) {
            Toast.makeText(this, "Please enter Email ID", Toast.LENGTH_SHORT).show();
        } else if (isValidEmail(email)) {
            Toast.makeText(this, "Please enter Valid Email ID", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Please enter  Phone", Toast.LENGTH_SHORT).show();
        } else if (isValidMoblie(phone)) {
            Toast.makeText(this, "Please enter Valid Phone", Toast.LENGTH_SHORT).show();
        } else if (whomToMeet.isEmpty()) {
            Toast.makeText(this, "Please enter Whom To Meet", Toast.LENGTH_SHORT).show();
        } else if (purposeToMeet.isEmpty()) {
            Toast.makeText(this, "Please enter Purpose to Meet", Toast.LENGTH_SHORT).show();
        } else if (address.isEmpty()) {
            Toast.makeText(this, "Please enter Address", Toast.LENGTH_SHORT).show();

        } else {

            StorageReference ref = storageReference.child("Images/" + imageId);
            ref.putFile(Uri.fromFile(mPhotoFile)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isSuccessful()) ;
                    Uri downloadUrl = uriTask.getResult();
                    assert downloadUrl != null;


                    UsersModel usersModel = new UsersModel(downloadUrl.toString(), imageId, name, email, phone, whomToMeet, purposeToMeet, address, state, city, district, checkInTime, checkOutTime, "Check-In", departmentName);
                    assert imageId != null;
                    databaseReference.child(departmentName).child(imageId).setValue(usersModel);


                    Toast.makeText(CreateUserActivity.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                    myRefDepartmentDetails.child(departmentName).child("userCount").setValue(String.valueOf(deptUsersCount));

                    regProgress.dismiss();
                    Intent intent = new Intent(CreateUserActivity.this, DepartmentHomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);


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
                if (Build.VERSION.SDK_INT > 22) {
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


    /*   Validating Fields */
    // Validating email id
    public static boolean isValidEmail(String email1) {

        String EMAIL_PATTERN = "^([_A-Za-z0-9-+].{2,})+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email1);
        return !matcher.matches();

    }

    //Validating Mobile
    public static boolean isValidMoblie(String pass1) {

        return pass1 == null || pass1.length() != 10;

    }
}

