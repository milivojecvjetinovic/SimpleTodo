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

import com.activeandroid.query.Select;
import com.codepath.simpletodo.data.TodoAdapter;
import com.codepath.simpletodo.data.TodoItem;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    private static final int REQUEST_CODE = 20 ;  //tracking activity
    private ArrayList<TodoItem> items;
    //adapter to eazy display items array list to the list container
//    private ArrayAdapter<TodoItem> itemsAdapter;`
    private TodoAdapter itemsAdapter;
    private ListView lvItems;
    public static final String EDIT_ITEM = "editItem";
    private DueDateComparator comparator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        items = new ArrayList<>();
        //get hold of references
        lvItems = (ListView) findViewById(R.id.lvItems);
//        items = new ArrayList<>();
        readItemsFromDb();
//        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        itemsAdapter = new TodoAdapter(this,R.layout.item_todo, items);
        comparator =  new DueDateComparator();
        Collections.sort(items, comparator);
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
                        TodoItem todoItem = items.get(pos);
                        items.remove(pos);
                        //notify screen about the changes
                        itemsAdapter.notifyDataSetChanged();
                        todoItem.delete();
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
        TodoItem todoItem = items.get(position);
        String itemText = todoItem.itemText;
        //create flow from main to edit
        Intent i = new Intent(MainActivity.this, EditItemActivity.class);
        //set key value pairs
        EditItemData data = new EditItemData(itemText, position, todoItem.dueDate, todoItem.priority);
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
        TodoItem item = new TodoItem(itemText, new Date());
        itemsAdapter.add(item);
        Collections.sort(items, comparator);

        itemsAdapter.notifyDataSetChanged();
        //clear entry
        etNewItem.setText("");
        writeItemToDb(item);
    }



    private void readItemsFromDb(){
        List<TodoItem> todoItems = new Select().from(TodoItem.class).orderBy("dueDate ASC").limit(100).execute();
        if(todoItems!=null && !todoItems.isEmpty()){
            items.addAll(todoItems);
        }
    }

//    private void readItems() {
//        File filesDir = getFilesDir();
//        File todoFiles = new File(filesDir, "todo.txt");
//
//        try {
//            items = new ArrayList<>(FileUtils.readLines(todoFiles));
//        } catch (IOException e) {
//            items = new ArrayList<>();
//        }
//    }


//    private void writeItems() {
//        File filesDir = getFilesDir();
//        File todoFile = new File(filesDir, "todo.txt");
//
//        try {
//            FileUtils.writeLines(todoFile, items);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


    private void writeItemToDb(TodoItem item){
        item.save();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent i) {
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            //get data
            EditItemData data = (EditItemData) i.getSerializableExtra(MainActivity.EDIT_ITEM);
            TodoItem todoItem = items.get(data.position);
            todoItem.itemText = data.itemText;
            todoItem.lastModified = new Date();
            todoItem.dueDate = data.date;
            todoItem.priority = data.priority;
            Collections.sort(items, comparator);
            itemsAdapter.notifyDataSetChanged();
            writeItemToDb(todoItem);
        }
    }
}
