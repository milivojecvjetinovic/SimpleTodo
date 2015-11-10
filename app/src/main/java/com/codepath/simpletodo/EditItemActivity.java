package com.codepath.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends ActionBarActivity {

    private EditText editItemText;
    private EditItemData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        editItemText = (EditText) findViewById(R.id.editItemText);

        data = (EditItemData) getIntent().getSerializableExtra(MainActivity.EDIT_ITEM);
        editItemText.setText(data.itemText);
    }

    public void onSaveEdit(View view) {
        //get hold of text and send it back
        data.itemText = editItemText.getText().toString();
        Intent i = new Intent();
        i.putExtra(MainActivity.EDIT_ITEM, data);
        setResult(MainActivity.RESULT_OK, i);

        //end session
        finish();
    }
}
