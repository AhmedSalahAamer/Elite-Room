package com.example.eliteroom.Adabters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eliteroom.Models.ModelCourse;
import com.example.eliteroom.Models.ModelMember;
import com.example.eliteroom.databinding.ItemCourseBinding;

import java.util.ArrayList;

public class StudentCourseAdabter extends RecyclerView.Adapter<StudentCourseAdabter.Holder> {

    ArrayList<ModelMember> coursesList = new ArrayList<>();
    OnItemClick onItemClick;


    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public void setCoursesList(ArrayList<ModelMember> coursesList) {
        this.coursesList = coursesList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCourseBinding binding = ItemCourseBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.setBinding(coursesList.get(position));
    }

    @Override
    public int getItemCount() {
        return coursesList == null ? 0 : coursesList.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        ItemCourseBinding binding;

        public Holder(ItemCourseBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.onClickRoot(coursesList.get(getLayoutPosition()));
                }
            });
        }

        public void setBinding(ModelMember modelMember) {
            binding.tvCourseTitle.setText(modelMember.getCourseName());
        }


    }

    public interface OnItemClick {
        void onClickRoot(ModelMember modelMember);
    }
}
