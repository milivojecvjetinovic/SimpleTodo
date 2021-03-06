package com.codepath.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;

public class EditItemActivity extends ActionBarActivity {

    private EditText editItemText;
    private EditItemData data;
    private DatePicker datePicker;
    private Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        editItemText = (EditText) findViewById(R.id.editItemText);
        datePicker = (DatePicker) findViewById(R.id.datePicker);

        data = (EditItemData) getIntent().getSerializableExtra(MainActivity.EDIT_ITEM);
        editItemText.setText(data.itemText);


        Calendar c = Calendar.getInstance();
        c.setTime(data.date);
        datePicker.updateDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE));


        spinner = (Spinner) findViewById(R.id.priority);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.priority_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(adapter.getPosition(data.priority));
    }

    public void onSaveEdit(View view) {
        //get hold of text and send it back
        data.itemText = editItemText.getText().toString();
        Calendar c = Calendar.getInstance();
        c.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());

        data.date = c.getTime();
        data.priority = String.valueOf(spinner.getSelectedItem());

        Intent i = new Intent();
        i.putExtra(MainActivity.EDIT_ITEM, data);
        setResult(MainActivity.RESULT_OK, i);


        //end session
        finish();
    }



}
