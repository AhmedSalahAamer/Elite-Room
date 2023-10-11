package com.example.eliteroom.Adabters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eliteroom.Models.ModelMember;
import com.example.eliteroom.databinding.ItemStudentGradesBinding;

import java.util.ArrayList;

public class StudenGradesAdabter extends RecyclerView.Adapter<StudenGradesAdabter.Holder> {


    private ArrayList<ModelMember> memberList = new ArrayList<>();

    public void setMemberList(ArrayList<ModelMember> memberList) {
        this.memberList = memberList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemStudentGradesBinding binding = ItemStudentGradesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.setBinding(memberList.get(position));
    }

    @Override
    public int getItemCount() {
        return memberList == null ? 0 : memberList.size();
    }


    class Holder extends RecyclerView.ViewHolder {
        ItemStudentGradesBinding binding;

        public Holder(ItemStudentGradesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setBinding(ModelMember modelMember) {
            binding.tvStudentEmail.setText("E-mail: "+modelMember.getEmail());
            binding.tvStudentName.setText("Name: "+modelMember.getName());
            binding.tvStudentAttendGrade.setText("Attendace: "+String.valueOf(modelMember.getAttendGrade()));
            binding.tvStudentQuizGrade.setText("Quiz: "+String.valueOf(modelMember.getQuizGrade()));
            binding.tvStudentProjectGrade.setText("Project: "+String.valueOf(modelMember.getProjectGrade()));
        }
    }
}
