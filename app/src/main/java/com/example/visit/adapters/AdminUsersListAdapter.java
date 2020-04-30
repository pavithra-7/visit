package com.example.visit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.visit.R;
import com.example.visit.model.UsersModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminUsersListAdapter extends RecyclerView.Adapter<AdminUsersListAdapter.MyViewHolder> implements Filterable {



    private List<UsersModel> userModelList;
    private List<UsersModel> userModelListFull;
    private Context context;
    private DatabaseReference myref;

    public AdminUsersListAdapter(List<UsersModel> userModelList, Context context) {
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



        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.admin_user_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        UsersModel userModel = userModelListFull.get(position);
        holder.txtDepId.setText("User ID:  "+userModel.getUserId());
        holder.txtName.setText("Name:  "+userModel.getName());
        holder.txtEmail.setText("Email:  "+userModel.getEmail());
        holder.txtPhone.setText("Phone: "+userModel.getPhone());
        holder.txtmeetTo.setText("Whom to Meet: "+ userModel.getWhomToMeet());
        holder.txtCheckInTime.setText("Check In Time"+ userModel.getCheckInTime());
        holder.txtCheckOutTime.setText("Check Out Time: "+ userModel.getCheckOutTime());

        Glide.with(context).load(userModel.getImageUrl()).into(holder.imgProfile);

    }

    @Override
    public int getItemCount() {
        return userModelListFull.size();
    }

    public void removeAt(int position,String userName) {
        myref = FirebaseDatabase.getInstance().getReference("UserDetails");
        userModelListFull.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, userModelList.size());
        myref.child(userName).removeValue();
    }


}