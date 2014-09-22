package com.example.rewertsassignment1.application;

import android.app.Application;
import android.content.Context;

import com.example.rewertsassignment1.controller.TodoController;

/**
 * 
 * This is a singleton class that I use to access controllers from
 * any other class as needed. It also holds the app context, which
 * is handy for non-activity classes sometimes.
 * 
 * @author Jared Rewerts
 *
 */
public class TodoApplication extends Application {
	
	private static TodoController todoController;
	
	private static Context context;
	private static boolean showArchives;
	
	public void onCreate(){
	    super.onCreate();
	    context = getApplicationContext();
	    showArchives = false;
	}
	
	/**
	 * returns the current context
	 * @return Context
	 */
	public static Context context() {
	    return context;
	}
	
	/**
	 * Sets whether archived TodoItems are being shown.
	 * @param state
	 */
	public static void setShowArchives(boolean state) {
		showArchives = state;
	}
	
	/**
	 * Gets whether archives are being shown.
	 * @return boolean showArchives
	 */
	public static boolean getShowArchives() {
		return showArchives;
	}
	
	/**
	 * Creates a singleton of the TodoController if it hasn't yet been created.
	 * @return TodoController
	 */
	public static TodoController getTodoController()
	{
		if (todoController == null) {
			todoController = new TodoController();
		}
		return todoController;
	}
}
