package com.example.eliteroom.Adabters;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eliteroom.Const;
import com.example.eliteroom.Models.ModelMessage;
import com.example.eliteroom.R;
import com.example.eliteroom.databinding.ItemMessageBinding;

import java.util.ArrayList;

public class ChatMessagesAdabter extends RecyclerView.Adapter<ChatMessagesAdabter.Holder> {
    private ArrayList<ModelMessage> list = new ArrayList<>();

    public void addMessage(ModelMessage message) {
        list.add(message);
        notifyItemInserted(list.size() - 1);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMessageBinding binding = ItemMessageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.setBinding(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        ItemMessageBinding binding;

        public Holder(ItemMessageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setBinding(ModelMessage modelMessage) {
            binding.tvMessage.setText(modelMessage.getMessage());
            if (Const.mUser.getUserId().equals(modelMessage.getSenderId())){

                binding.llView2.setHorizontalGravity(Gravity.END);
                binding.llView.setBackgroundResource(R.drawable.sender_message_bg);
                binding.tvSenderName.setVisibility(View.GONE);

            }else {

                binding.llView2.setHorizontalGravity(Gravity.START);
                binding.llView.setBackgroundResource(R.drawable.reciver_message_bg);
                binding.tvSenderName.setText(modelMessage.getSenderName());
            }


        }
    }
}
