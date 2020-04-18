package com.example.visit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.visit.R;
import com.example.visit.model.DepartmentModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DepartmentListAdapter extends RecyclerView.Adapter<DepartmentListAdapter.MyViewHolder> implements Filterable{


    private List<DepartmentModel> departmentModelList;
    private List<DepartmentModel> departmentModelListFull;
    private Context context;

    public DepartmentListAdapter(List<DepartmentModel> departmentModelList, Context context) {
        this.departmentModelList = departmentModelList;
        departmentModelListFull = departmentModelList;
        this.context = context;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    departmentModelListFull = departmentModelList;
                } else {

                    ArrayList<DepartmentModel> filteredList = new ArrayList<>();

                    for (DepartmentModel androidVersion : departmentModelList) {

                        if (androidVersion.getDepName().toLowerCase().contains(charString) || androidVersion.getDepID().toLowerCase().contains(charString) ) {

                            filteredList.add(androidVersion);
                        }
                    }

                    departmentModelListFull = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = departmentModelListFull;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                departmentModelListFull = (ArrayList<DepartmentModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtDepId)
        TextView txtDepId;
        @BindView(R.id.txtDepName)
        TextView txtDepName;
        @BindView(R.id.txtDepEmail)
        TextView txtDepEmail;
        @BindView(R.id.txtDepPhone)
        TextView txtDepPhone;
        @BindView(R.id.txtDepCode)
        TextView txtDepCode;
        @BindView(R.id.btnEdit)
        Button btnEdit;
        @BindView(R.id.btnDelete)
        Button btnDelete;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.department_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DepartmentModel departmentModel = departmentModelListFull.get(position);
        holder.txtDepId.setText("Department ID : " + departmentModel.getDepID());
        holder.txtDepName.setText("Department Name : " + departmentModel.getDepName());
        holder.txtDepEmail.setText("Department Email : " + departmentModel.getDepMail());
        holder.txtDepCode.setText("Department Code : " + departmentModel.getDepCode());
        holder.txtDepPhone.setText("Department Phone : " + departmentModel.getDepPhone());

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
    }

    @Override
    public int getItemCount() {
        return departmentModelListFull.size();
    }



}