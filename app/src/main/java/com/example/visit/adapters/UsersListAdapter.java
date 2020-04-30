package com.example.visit.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.visit.R;
import com.example.visit.activity.UpdateUser;
import com.example.visit.model.UsersModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.MyViewHolder> implements Filterable {


    private List<UsersModel> userModelList;
    private List<UsersModel> userModelListFull;
    private Context context;
    private DatabaseReference myRef;
    private String checkOutTime;

    public UsersListAdapter(List<UsersModel> userModelList, Context context) {
        this.userModelList = userModelList;
        userModelListFull = userModelList;
        this.context = context;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    userModelListFull = userModelList;
                } else {

                    ArrayList<UsersModel> filteredList = new ArrayList<>();

                    for (UsersModel androidVersion : userModelList) {

                        if (androidVersion.getName().toLowerCase().contains(charString)) {

                            filteredList.add(androidVersion);
                        }
                    }

                    userModelListFull = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = userModelListFull;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                userModelListFull = (ArrayList<UsersModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgProfile)
        ImageView imgProfile;
        @BindView(R.id.txtDepId)
        TextView txtDepId;
        @BindView(R.id.txtName)
        TextView txtName;
        @BindView(R.id.txtEmail)
        TextView txtEmail;
        @BindView(R.id.txtPhone)
        TextView txtPhone;
        @BindView(R.id.txtmeetTo)
        TextView txtmeetTo;
        @BindView(R.id.txtCheckInTime)
        TextView txtCheckInTime;
        @BindView(R.id.txtCheckOutTime)
        TextView txtCheckOutTime;
        @BindView(R.id.txtCheckStatus)
        TextView txtCheckStatus;
        @BindView(R.id.btnCheckout)
        Button btnCheckout;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        UsersModel userModel = userModelListFull.get(position);
        holder.txtDepId.setText("User ID:  " + userModel.getUserId());
        holder.txtName.setText("Name:  " + userModel.getName());
        holder.txtEmail.setText("Email:  " + userModel.getEmail());
        holder.txtPhone.setText("Phone: " + userModel.getPhone());
        holder.txtmeetTo.setText("Whom to Meet: " + userModel.getWhomToMeet());
        holder.txtCheckInTime.setText("Check In Time" + userModel.getCheckInTime());
        holder.txtCheckOutTime.setText("Check Out Time: " + userModel.getCheckOutTime());
        holder.txtCheckStatus.setText("Check Status " + userModel.getStatus());

        Glide.with(context).load(userModel.getImageUrl()).into(holder.imgProfile);


        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        String currentTime = new SimpleDateFormat("hh:mm:sss", Locale.getDefault()).format(new Date());

        checkOutTime = currentDate + " " + currentTime;

        myRef = FirebaseDatabase.getInstance().getReference("UserDetails");

        holder.btnCheckout.setOnClickListener(view -> {
            AlertDialog.Builder alertbox = new AlertDialog.Builder(view.getRootView().getContext());
            alertbox.setMessage("Are you sure want to checkout this user?");
            alertbox.setTitle("Checkout?");
            alertbox.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            alertbox.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    myRef.child(userModel.getName()).child("status").setValue("Check-Out");
                    myRef.child(userModel.getName()).child("checkOutTime").setValue(checkOutTime);

                }
            });
            alertbox.show();
        });
    }

    @Override
    public int getItemCount() {
        return userModelListFull.size();
    }

    public void removeAt(int position, String userName) {
        DatabaseReference myref = FirebaseDatabase.getInstance().getReference("UserDetails");
        userModelListFull.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, userModelList.size());
        myref.child(userName).removeValue();
    }


}