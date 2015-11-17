package com.codepath.simpletodo.data;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by mcvjetinovic on 11/15/15.
 */
@Table(name = "TodoItem")
public class TodoItem extends Model {

    @Column(name = "itemText")
    public String itemText;

    @Column(name = "priority")
    public int priority;

    @Column(name = "dueDate")
    public Date dueDate;

    @Column(name="lastModified")
    public Date lastModified;

    public TodoItem(){
        super();
    }

    public TodoItem(String itemText, Date created){
        this.itemText = itemText;
        this.lastModified = created;
//        Calendar c = Calendar.getInstance();
//        c.add(Calendar.DATE, 5);
//        this.dueDate =
        this.dueDate = created;
        this.priority = 1;
    }
}
