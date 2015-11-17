package com.codepath.simpletodo;

import java.io.Serializable;
import java.util.Date;
/**
 * Simple data transfer data bean for todo leaving public access properties...
 */
public class EditItemData implements Serializable {
    public String itemText;
    public int position;
    public Date date;
    public String priority;

    public EditItemData(String itemText, int position, Date date, String priority){
            this.itemText = itemText;
            this.position = position;
            this.date = date;
            this.priority = priority;
        }
}
