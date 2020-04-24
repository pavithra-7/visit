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

import androidx.recyclerview.widget.RecyclerView;

import com.example.visit.R;
import com.example.visit.model.UsersModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.MyViewHolder> implements Filterable {



    private List<UsersModel> userModelList;
    private List<UsersModel> userModelListFull;
    private Context context;

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
        @BindView(R.id.btnEdit)
        Button btnEdit;
        @BindView(R.id.btnDelete)
        Button btnDelete;
        @BindView(R.id.btnCheckout)
        Button btnCheckout;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        UsersModel userModel = userModelListFull.get(position);
        holder.txtDepId.setText(userModel.getUserId());
        holder.txtName.setText(userModel.getName());
        holder.txtEmail.setText(userModel.getEmail());
        holder.txtPhone.setText(userModel.getPhone());
        holder.txtmeetTo.setText(userModel.getWhomToMeet());

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return userModelListFull.size();
    }


}