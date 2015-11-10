package com.codepath.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {

    private static final int REQUEST_CODE = 20 ;  //tracking activity
    private ArrayList<String> items;
    //adapter to eazy display items array list to the list container
    private ArrayAdapter<String> itemsAdapter;
    private ListView lvItems;
    public static final String EDIT_ITEM = "editItem";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get hold of references
        lvItems = (ListView) findViewById(R.id.lvItems);
//        items = new ArrayList<>();
        readItems();
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
//        items.add("First item");
//        items.add("Second item");
        setupListViewListener();
    }

    private void setupListViewListener() {

        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                        //remove item on position
                        items.remove(pos);
                        //notify screen about the changes
                        itemsAdapter.notifyDataSetChanged();
                        writeItems();
                        return true;
                    }
                }
        );

        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        launchComposeView(position);
                    }
                }
        );
    }

    private void launchComposeView(int position) {

        String itemText = items.get(position);
        //create flow from main to edit
        Intent i = new Intent(MainActivity.this, EditItemActivity.class);
        //set key value pairs
        EditItemData data = new EditItemData(itemText, position);
        i.putExtra(MainActivity.EDIT_ITEM, data);
        //start screen
        startActivityForResult(i, REQUEST_CODE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //adding item to the list
    public void onAddItem(View view) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        //add to adapter
        itemsAdapter.add(itemText);
        //clear entry
        etNewItem.setText("");
        writeItems();
    }


    private void readItems() {
        File filesDir = getFilesDir();
        File todoFiles = new File(filesDir, "todo.txt");

        try {
            items = new ArrayList<>(FileUtils.readLines(todoFiles));
        } catch (IOException e) {
            items = new ArrayList<>();
        }
    }


    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");

        try {
            FileUtils.writeLines(todoFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent i) {
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            //get data
            EditItemData data = (EditItemData) i.getSerializableExtra(MainActivity.EDIT_ITEM);
            //change adaptor and display item
            items.set(data.position, data.itemText);
            itemsAdapter.notifyDataSetChanged();
            writeItems();
        }
    }
}
