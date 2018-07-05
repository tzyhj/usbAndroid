package com.qc.usbandroid;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import com.qc.usbandroid.databinding.ItemMainBinding;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder>{

    private List<String> strList;
    private Context context;

    public MainAdapter(Context context, List<String> strList) {
        this.strList = strList;
        this.context = context;

    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemMainBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_main,parent,false);
        return new MainViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        holder.getBinding().tvItem.setText(strList.get(position));
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return strList == null ? 0:strList.size();
    }

    class MainViewHolder extends RecyclerView.ViewHolder{
        private ItemMainBinding binding;

        public ItemMainBinding getBinding() {
            return binding;
        }

        public MainViewHolder(ItemMainBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
