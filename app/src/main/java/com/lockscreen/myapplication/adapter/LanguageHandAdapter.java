package com.lockscreen.myapplication.adapter;


import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import androidx.recyclerview.widget.RecyclerView;

import com.lockscreen.myapplication.databinding.ItemLanguageHandBinding;
import com.lockscreen.myapplication.model.LanguageHandModel;
import com.lockscreen.myapplication.R;

import java.util.List;


public class LanguageHandAdapter extends RecyclerView.Adapter<LanguageHandAdapter.LanguageViewHolder> {

    private Context context;
    private List<LanguageHandModel> lists;
    private IClickLanguage iClickLanguage;

    public interface IClickLanguage {
        void onClick(LanguageHandModel model);
    }

    public LanguageHandAdapter(Context context, List<LanguageHandModel> lists, IClickLanguage iClickLanguage) {
        this.context = context;
        this.lists = lists;
        this.iClickLanguage = iClickLanguage;
    }

    @NonNull
    @Override
    public LanguageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLanguageHandBinding binding = ItemLanguageHandBinding.inflate(LayoutInflater.from(context), parent, false);
        return new LanguageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LanguageViewHolder holder, int position) {
        LanguageHandModel data = lists.get(position);
        holder.bind(data, context, position);

        holder.binding.rlItem.setOnClickListener(view -> {
            setSelectLanguage(data.getIsoLanguage());
            iClickLanguage.onClick(data);
            for (LanguageHandModel item : lists) {
                item.setHandVisible(false);
            }
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    private void setSelectLanguage(String code) {
        for (LanguageHandModel data : lists) {
            data.setCheck(data.getIsoLanguage().equals(code));
        }
        notifyDataSetChanged();
    }

    public static class LanguageViewHolder extends RecyclerView.ViewHolder {
        private final ItemLanguageHandBinding binding;

        public LanguageViewHolder(ItemLanguageHandBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public void bind(LanguageHandModel data, Context context, int position) {
            if (data.isHandVisible() && position == 2) {
                binding.animHand.setVisibility(View.VISIBLE);
            } else {
                binding.animHand.setVisibility(View.INVISIBLE);
            }

            binding.ivAvatar.setImageDrawable(context.getDrawable(data.getImage()));
            binding.tvTitle.setText(data.getLanguageName());

            if (data.getCheck()) {
                binding.rlItem.setBackgroundColor(context.getColor(R.color.color_F0F2FB));
                binding.v2.setVisibility(View.VISIBLE);
            } else {
                binding.rlItem.setBackgroundColor(Color.TRANSPARENT);
                binding.v2.setVisibility(View.GONE);
            }
        }
    }
}