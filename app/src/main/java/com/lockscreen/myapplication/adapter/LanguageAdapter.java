package com.lockscreen.myapplication.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.lockscreen.myapplication.databinding.ItemLanguageBinding;
import com.lockscreen.myapplication.model.LanguageModel;
import com.lockscreen.myapplication.R;

import java.util.List;


public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.LanguageViewHolder> {

    private final List<LanguageModel> lists;
    private final IClickLanguage iClickLanguage;

    public LanguageAdapter(List<LanguageModel> lists, IClickLanguage iClickLanguage) {
        this.lists = lists;
        this.iClickLanguage = iClickLanguage;
    }

    @NonNull
    @Override
    public LanguageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemLanguageBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_language, parent, false);
        return new LanguageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LanguageViewHolder holder, int position) {
        holder.bind(lists.get(position));
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class LanguageViewHolder extends RecyclerView.ViewHolder {
        private final ItemLanguageBinding binding;

        public LanguageViewHolder(ItemLanguageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(LanguageModel data) {
            if (data == null) {
                return;
            }
            binding.setLanguage(data);
            binding.ivAvatar.setImageResource(data.getImage());
            binding.setClickListener(iClickLanguage);
            binding.executePendingBindings();
            binding.getRoot().setOnClickListener(v -> {
                setSelectLanguage(data.getIsoLanguage());
                iClickLanguage.onClick(data);
            });
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setSelectLanguage(String code) {
        for (LanguageModel data : lists) {
            data.setCheck(data.getIsoLanguage().equals(code));
        }
        notifyDataSetChanged();
    }

    public interface IClickLanguage {
        void onClick(LanguageModel model);
    }
}