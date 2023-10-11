package com.example.eliteroom.Adabters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eliteroom.databinding.ItemQuizBinding;

import java.util.ArrayList;

public class QuizzesAdabter extends RecyclerView.Adapter<QuizzesAdabter.Holder> {
    ArrayList<String> list;

    OnItemClicked onItemClicked;

    public void setList(ArrayList<String> list) {
        this.list = list;
    }

    public void setOnItemClicked(OnItemClicked onItemClicked) {
        this.onItemClicked = onItemClicked;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemQuizBinding binding = ItemQuizBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.setBinding(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        ItemQuizBinding binding;

        public Holder(ItemQuizBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClicked.onClickRoot(list.get(getLayoutPosition()));
                }
            });

        }

        public void setBinding(int pos) {
            binding.tvTitleQuiz.setText("Lecture " + pos+1);
        }
    }

    public interface OnItemClicked {
        void onClickRoot(String id);
    }
}
