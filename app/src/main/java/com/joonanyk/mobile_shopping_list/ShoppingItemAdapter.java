package com.joonanyk.mobile_shopping_list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ShoppingItemAdapter extends RecyclerView.Adapter<ShoppingItemAdapter.ViewHolder> {

    private List<ShoppingItem> shoppingItems;
    private OnItemClickListener onItemClickListener;

    public ShoppingItemAdapter(List<ShoppingItem> shoppingItems, OnItemClickListener onItemClickListener) {
        this.shoppingItems = shoppingItems;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShoppingItem shoppingItem = shoppingItems.get(position);
        holder.itemName.setText(shoppingItem.getName());
    }

    @Override
    public int getItemCount() {
        return shoppingItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        ImageButton deleteButton;
        ImageButton editButton;

        public ViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item_name);
            deleteButton = itemView.findViewById(R.id.delete_button);
            editButton = itemView.findViewById(R.id.edit_button);

            // Set listeners for buttons
            deleteButton.setOnClickListener(v -> onItemClickListener.onDeleteClick(getAdapterPosition()));
            editButton.setOnClickListener(v -> onItemClickListener.onEditClick(getAdapterPosition()));
        }
    }

    public interface OnItemClickListener {
        void onDeleteClick(int position);
        void onEditClick(int position);
    }
}
