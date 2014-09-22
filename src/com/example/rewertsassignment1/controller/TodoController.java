package com.example.rewertsassignment1.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import com.example.rewertsassignment1.R;
import com.example.rewertsassignment1.application.TodoApplication;
import com.example.rewertsassignment1.model.TodoItem;

public class TodoController {
	
	private ArrayList<TodoItem> todoItems;
	
	public TodoController () {
		this.todoItems = new ArrayList<TodoItem>();
	}
	
	/**
	 * Adds a new TodoItem to the list of TodoItems.
	 * @param TodoItem todoItem
	 */
	public void addTodoItem(TodoItem todoItem) {
		this.todoItems.add(todoItem);
	}
	
	/**
	 * Gets a list of all todo items.
	 * @return An ArrayList of TodoItem
	 */
	public ArrayList<TodoItem> getTodoItems() {
		return this.todoItems;
	}
	
	/**
	 * Gets all TodoItems with isArchived set to false.
	 * @return ArrayList<TodoItem> of only TodoItems with isArchived set to false.
	 */
	public ArrayList<TodoItem> getNonarchivedTodoItems() {
		ArrayList<TodoItem> nonarchived = new ArrayList<TodoItem>();
		
		for (TodoItem t : this.todoItems) {
			if (!t.isArchived()) {
				nonarchived.add(t);
			}
		}
		
		return nonarchived;
	}
	
	/**
	 * Gets all TodoItems with isArchived set to true.
	 * @return ArrayList<TodoItem> of only TodoItems with isArchived set to true.
	 */
	public ArrayList<TodoItem> getArchivedTodoItems() {
		ArrayList<TodoItem> archived = new ArrayList<TodoItem>();
		
		for (TodoItem t : this.todoItems) {
			if (t.isArchived()) {
				archived.add(t);
			}
		}
		
		return archived;
	}
	
	/**
	 * Sets the value todoItems
	 * @param todoItems
	 */
	public void setTodoItems(ArrayList<TodoItem> todoItems) {
		this.todoItems = todoItems;
	}
	
	/**
	 * Deletes TodoItems item from the controller.
	 * @param item
	 */
	public void deleteTodoItem(TodoItem item) {
		this.todoItems.remove(item);
	}
	
	/**
	 * Gets number of items checked.
	 * @param items
	 * @return int number checked
	 */
	public int getTotalChecked(ArrayList<TodoItem> items) {
		int checked = 0;
		
		for(TodoItem t : items) {
			if (t.isChecked()) {
				checked++;
			}
		}
		
		return checked;
	}
	
	/**
	 * Gets all selected TodoItems.
	 * @return ArrayList<TodoItem> of selected TodoItems
	 */
	public ArrayList<TodoItem> getSelected() {
		ArrayList<TodoItem> selected = new ArrayList<TodoItem>();
		
		for (TodoItem t : todoItems) {
			if (t.isSelected()) {
				selected.add(t);
			}
		}
		
		return selected;
	}
	
	/**
	 * Saves the current todoItems.
	 */
	public void saveTodoItems() {
		try {
			FileOutputStream fos =  TodoApplication.context().openFileOutput(TodoApplication.context().getString(R.string.save_File), 0);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(this.todoItems);
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Loads the saved todoItems.
	 */
	public void loadTodoItems() {
		ArrayList<TodoItem> result = new ArrayList<TodoItem>();
		try {
			FileInputStream fis = TodoApplication.context().openFileInput(TodoApplication.context().getString(R.string.save_File));

			ObjectInputStream ois = new ObjectInputStream(fis);
			while (true) {
				result.addAll((ArrayList) ois.readObject());
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		this.setTodoItems(result);
	}
}
