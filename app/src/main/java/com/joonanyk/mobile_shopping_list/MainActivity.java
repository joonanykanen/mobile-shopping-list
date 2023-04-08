package com.joonanyk.mobile_shopping_list;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ShoppingItemAdapter.OnItemClickListener {

    private List<ShoppingItem> shoppingItems;
    private RecyclerView recyclerView;
    private ShoppingItemAdapter shoppingItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shoppingItems = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view);

        shoppingItemAdapter = new ShoppingItemAdapter(shoppingItems, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(shoppingItemAdapter);

        Button addItemButton = findViewById(R.id.add_item_button);
        addItemButton.setOnClickListener(v -> showAddItemDialog());

        Button sortAlphabeticallyButton = findViewById(R.id.sort_alphabetically_button);
        sortAlphabeticallyButton.setOnClickListener(v -> sortAlphabetically());

        Button sortByDateButton = findViewById(R.id.sort_by_date_button);
        sortByDateButton.setOnClickListener(v -> sortByDate());
    }

    private void showAddItemDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Lisää ostos");

        View view = getLayoutInflater().inflate(R.layout.dialog_add_item, null);
        EditText itemNameEditText = view.findViewById(R.id.item_name_edit_text);

        builder.setView(view);
        builder.setPositiveButton("Lisää", (dialog, which) -> {
            String itemName = itemNameEditText.getText().toString();
            addItem(itemName);
        });
        builder.setNegativeButton("Peruuta", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void addItem(String itemName) {
        ShoppingItem newItem = new ShoppingItem(itemName, System.currentTimeMillis());
        shoppingItems.add(newItem);
        shoppingItemAdapter.notifyItemInserted(shoppingItems.size() - 1);
    }

    @Override
    public void onDeleteClick(int position) {
        shoppingItems.remove(position);
        shoppingItemAdapter.notifyItemRemoved(position);
    }

    @Override
    public void onEditClick(int position) {
        showEditItemDialog(position);
    }

    private void showEditItemDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Muokkaa ostosta");

        View view = getLayoutInflater().inflate(R.layout.dialog_add_item, null);
        EditText itemNameEditText = view.findViewById(R.id.item_name_edit_text);
        itemNameEditText.setText(shoppingItems.get(position).getName());

        builder.setView(view);
        builder.setPositiveButton("Tallenna", (dialog, which) -> {
            String itemName = itemNameEditText.getText().toString();
            editItem(position, itemName);
        });
        builder.setNegativeButton("Peruuta", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void editItem(int position, String itemName) {
        ShoppingItem editedItem = shoppingItems.get(position);
        editedItem.setName(itemName);
        shoppingItemAdapter.notifyItemChanged(position);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void sortAlphabetically() {
        Collections.sort(shoppingItems, (item1, item2) -> item1.getName().compareToIgnoreCase(item2.getName()));
        shoppingItemAdapter.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void sortByDate() {
        Collections.sort(shoppingItems, (item1, item2) -> Long.compare(item1.getTimestamp(), item2.getTimestamp()));
        shoppingItemAdapter.notifyDataSetChanged();
    }
}
