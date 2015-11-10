package com.codepath.simpletodo;

import java.io.Serializable;

/**
 * Simple data transfer data bean for todo leaving public access properties...
 */
public class EditItemData implements Serializable {
    public String itemText;
    public int position;

    public EditItemData(String itemText, int position){
            this.itemText = itemText;
            this.position = position;
        }
}
