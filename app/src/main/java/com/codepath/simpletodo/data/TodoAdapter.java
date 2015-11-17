package com.codepath.simpletodo.data;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.codepath.simpletodo.R;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by mcvjetinovic on 11/15/15.
 */
public class TodoAdapter extends ArrayAdapter<TodoItem> {


    private static final SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

    public TodoAdapter(Activity activity, int resource, List<TodoItem> itemList) {
        super(activity, resource, itemList);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TodoItem item = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_todo, parent, false);
        }

        TextView itemText = (TextView) convertView.findViewById(R.id.itemText);
        TextView priority = (TextView) convertView.findViewById(R.id.priority);
        TextView dueDate =  (TextView) convertView.findViewById(R.id.dueDate);

        itemText.setText(item.itemText);
        //setting int to string will throw Resources$NotFoundException:!!!
        priority.setText(item.priority);
        if(item.dueDate != null) {
            dueDate.setText(formatter.format(item.dueDate));
        }
        return convertView;
    }

}
