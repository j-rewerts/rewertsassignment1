package com.example.rewertsassignment1.model;

import java.io.Serializable;
import java.util.UUID;

/**
 * 
 * This is the model used for the displayed TodoItems. These
 * are saved and loaded inside the TodoItemController.
 * 
 * @author Jared Rewerts
 *
 */
public class TodoItem implements Serializable {
	

	private static final long serialVersionUID = 1L;
	private String content;
	private UUID id;
	private boolean isChecked;
	private boolean isArchived;
	private boolean isSelected;
	
	public TodoItem() {
		this.id = UUID.randomUUID();
		this.isArchived = false;
		this.isChecked = false;
		this.isSelected = false;
		this.content = "";
	}
	
	public TodoItem(String content) {
		this.id = UUID.randomUUID();
		this.isArchived = false;
		this.isChecked = false;
		this.isSelected = false;
		this.content = content;
	}
	
	@Override
	public String toString() {
		return this.content;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof TodoItem))
            return false;
        if (obj == this)
            return true;
        
        TodoItem input = (TodoItem) obj;
        
        if (input.id.equals(this.id))
        	return true;
        
        return false;
	}
	
	/**
	 * Gets the content of the TodoItem
	 * @return String content
	 */
	public String getContent() {
		return this.content;
	}
	
	/**
	 * Sets the content of the TodoItem.
	 * @param content
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
	/**
	 * Gets whether the TodoItem is checked or not.
	 * @return boolean isChecked
	 */
	public boolean isChecked() {
		return this.isChecked;
	}
	
	/**
	 * Sets whether the TodoItem is checked or not.
	 * @param checked
	 */
	public void setChecked (boolean checked) {
		this.isChecked = checked;
	}
	
	/**
	 * Gets whether the TodoItem has been archived or not.
	 * @return boolean isArchived
	 */
	public boolean isArchived() {
		return this.isArchived;
	}
	
	/**
	 * Sets whether the TodoItem is checked or not.
	 * @param archived
	 */
	public void setArchived(boolean archived) {
		this.isArchived = archived;
	}
	
	/**
	 * Gets whether the TodoItem is selected or not.
	 * @return boolean isSelected
	 */
	public boolean isSelected() {
		return this.isSelected;
	}
	
	/**
	 * Sets whether the TodoItem is selected or not.
	 * @param selected
	 */
	public void setSelected(boolean selected) {
		this.isSelected = selected;
	}
}
