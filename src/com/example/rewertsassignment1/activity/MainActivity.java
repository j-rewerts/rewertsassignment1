package com.example.rewertsassignment1.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rewertsassignment1.R;
import com.example.rewertsassignment1.adapter.TodoListAdapter;
import com.example.rewertsassignment1.application.TodoApplication;
import com.example.rewertsassignment1.model.TodoItem;


/**
 * 
 * This is the main and only activity for this app.
 * It allows the following actions, as per the assignment spec.:
 * Adding new TodoItems
 * Archiving/unarchiving selected TodoItems
 * Showing/hiding archived items
 * Deleting selected TodoItems
 * Emailing all or a selection of TodoItems
 * Viewing a summary of TodoItems
 * 
 * @author Jared Rewerts
 *
 */
public class MainActivity extends Activity  {

	private TodoListAdapter adapter;
	private ListView lv;
	private Menu menu;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Try to load the todos from file.
		TodoApplication.getTodoController().loadTodoItems();
		
		adapter = new TodoListAdapter();
		
		// Add the adapter to the listview
		lv = (ListView) findViewById(R.id.listView1);
		lv.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		this.menu = menu;
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_add:
	            this.add();
	            return true;
	        case R.id.action_archive:
	        	ArrayList<TodoItem> items1 = TodoApplication.getTodoController().getSelected();
	        	this.toggleArchived(items1);
	            return true;
	        case R.id.action_toggle_show_archive:
	        	this.toggleShowArchives();
	            return true;
	        case R.id.action_delete:
	        	ArrayList<TodoItem> items2 = TodoApplication.getTodoController().getSelected();
	        	this.deleteTodoItems(items2);
	            return true;
	        case R.id.action_email_selection:
	        	ArrayList<TodoItem> items3 = TodoApplication.getTodoController().getSelected();
	        	this.emailTodoItems(items3);
	            return true;
	        case R.id.action_email_all:
	        	ArrayList<TodoItem> items4 = TodoApplication.getTodoController().getTodoItems();
	        	this.emailTodoItems(items4);
	            return true;
	        case R.id.action_summary:	        		        	
	        	this.summaryToast();
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	/**
	 * Adds an empty todo item to the TodoController and refreshes the adapter.
	 */
	private void add() {
		TodoApplication.getTodoController().addTodoItem(new TodoItem(""));
		TodoApplication.getTodoController().saveTodoItems();
		adapter.notifyDataSetChanged();
	}

	/**
	 * Toggles whether all TodoItems in todoItems are archived or not.
	 * @param ArrayList<TodoItem> todoItems
	 */
	private void toggleArchived(ArrayList<TodoItem> todoItems) {
		for (TodoItem t : todoItems) {
			t.setArchived(!t.isArchived());
		}
		adapter.notifyDataSetChanged();
		TodoApplication.getTodoController().saveTodoItems();
	}
	
	/**
	 * Toggles whether archived TodoItems are shown or not.
	 */
	private void toggleShowArchives() {
		MenuItem toggleShowArchived = menu.findItem(R.id.action_toggle_show_archive);
		
		if (TodoApplication.getShowArchives()) {
			TodoApplication.setShowArchives(false);
			toggleShowArchived.setTitle(TodoApplication.context().getString(R.string.button_Show_Archived));
			adapter.notifyDataSetChanged();
		}
		else {
			TodoApplication.setShowArchives(true);
			toggleShowArchived.setTitle(TodoApplication.context().getString(R.string.button_Hide_Archived));
			adapter.notifyDataSetChanged();
		}
	}
	
	/**
	 * Removes all TodoItems in deleteMe from the TodoController.
	 * @param deleteMe
	 */
	private void deleteTodoItems(ArrayList<TodoItem> deleteMe) {
		for (TodoItem t : deleteMe) {
			TodoApplication.getTodoController().deleteTodoItem(t);
		}
		adapter.notifyDataSetChanged();
		TodoApplication.getTodoController().saveTodoItems();
	}
	
	/**
	 * Opens an email app with the body of the email set to 
	 * the TodoItems in sendMe.
	 * @param sendMe
	 */
	private void emailTodoItems(ArrayList<TodoItem> sendMe) {
		String sendString = new String();
		
		for (TodoItem t : sendMe) {
			if (t.isChecked()) {
				sendString += "[X] ";
			}
			else {
				sendString += "[ ] ";
			}
			
			sendString += t.getContent() + "\n";
		}
		
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/html");
		intent.putExtra(Intent.EXTRA_TEXT, sendString);
		
		startActivity(Intent.createChooser(intent, "Send Email"));
	}
	
	/**
	 * Pops up a summary of the TodoItems.
	 */
	private void summaryToast() {
		ArrayList<TodoItem> archived = TodoApplication.getTodoController().getArchivedTodoItems();
    	ArrayList<TodoItem> nonarchived = TodoApplication.getTodoController().getNonarchivedTodoItems();
    	
    	int archivedChecked = TodoApplication.getTodoController().getTotalChecked(archived);
    	int nonarchivedChecked = TodoApplication.getTodoController().getTotalChecked(nonarchived);
    	
    	String toastString = new String();
    	
    	toastString += "Not Archived: " + nonarchived.size() + "\n";
    	toastString += "\tChecked: " + nonarchivedChecked + "\n";
    	toastString += "\tUnchecked: " + (nonarchived.size() - nonarchivedChecked) + "\n";
    	toastString += "Archived: " + archived.size() + "\n";
    	toastString += "\tChecked: " + archivedChecked + "\n";
    	toastString += "\tUnchecked: " + (archived.size() - archivedChecked) + "\n";
    	
    	Toast.makeText(getApplicationContext(), toastString, Toast.LENGTH_LONG).show();
	}
}
