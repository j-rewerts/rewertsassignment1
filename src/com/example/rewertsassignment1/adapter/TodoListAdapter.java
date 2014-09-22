package com.example.rewertsassignment1.adapter;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;

import com.example.rewertsassignment1.R;
import com.example.rewertsassignment1.application.TodoApplication;
import com.example.rewertsassignment1.model.TodoItem;

/**
 * Adapter for the TodoItem ListView. 
 * References the todo_row.xml.
 * 
 * @author Jared Rewerts
 *
 */
public class TodoListAdapter extends BaseAdapter {
	
	private LayoutInflater inflater;
	
	public TodoListAdapter() {
		this.inflater = LayoutInflater.from(TodoApplication.context());
	}
	
	@Override
	public int getCount() {
		if (TodoApplication.getShowArchives()) {
			return TodoApplication.getTodoController().getTodoItems().size();
		}
		else {
			return TodoApplication.getTodoController().getNonarchivedTodoItems().size();
		}
	}
	
	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if (TodoApplication.getTodoController().getTodoItems() != null) {
			TodoItem item = (TodoItem) this.getItem(position);
			if (item == null) {
				return convertView;
			}
			
			if (!TodoApplication.getShowArchives() && item.isArchived()) {
				return convertView;
			}
			
			if (convertView == null) {
				convertView = this.inflater.inflate(R.layout.todo_row, parent, false);
			}
			
			int unselected = TodoApplication.context().getResources().getColor(R.color.color_unselected);
			int selected = TodoApplication.context().getResources().getColor(R.color.color_selected);
			
			if (item.isSelected()) {
				convertView.setBackgroundColor(selected);
			}
			else {
				convertView.setBackgroundColor(unselected);
			}						
			
			
			CheckBox cb = (CheckBox) convertView.findViewById(R.id.checkBox1);
			EditText et = (EditText) convertView.findViewById(R.id.editText1);
			TextView tv = (TextView) convertView.findViewById(R.id.textView1);
			
			// All the components have a reference to the model
			et.setTag(this.getItem(position)); 
			cb.setTag(this.getItem(position));
			convertView.setTag(this.getItem(position));
			
			cb.setChecked(item.isChecked());
			et.setText(item.toString());
			
			convertView.setOnClickListener(new OnClickListener() {
				@SuppressLint("ResourceAsColor")
				public void onClick(View v) {
					Drawable drawable = v.getBackground();
					
					if (!(drawable instanceof ColorDrawable)) {
						return;
					}
					
					int unselected = TodoApplication.context().getResources().getColor(R.color.color_unselected);
					int selected = TodoApplication.context().getResources().getColor(R.color.color_selected);
					
					int backgroundColor = ((ColorDrawable) drawable).getColor();
					
					// Update the content of the model.
	            	TodoItem todoItem = (TodoItem) v.getTag();
	            	
	            	TodoApplication.getTodoController().saveTodoItems();
					
					if (backgroundColor == selected) {
						v.setBackgroundColor(unselected);
						
						if (todoItem != null) {
		            		todoItem.setSelected(false);
		            	}
					}
					else if (backgroundColor == unselected) {
						v.setBackgroundColor(selected);
						
						if (todoItem != null) {
		            		todoItem.setSelected(true);
		            	}
					}
				}
			});
			
			et.setOnFocusChangeListener(new OnFocusChangeListener() {     
		        public void onFocusChange(View v, boolean hasFocus) {
		            if(!hasFocus) {
		            	if (!(v instanceof EditText)) {
		            		return;
		            	}
		            	
		            	EditText etInner = (EditText) v;
		            	String text = etInner.getText().toString();
		            	
		            	// Update the content of the model.
		            	TodoItem todoItem = (TodoItem) etInner.getTag();
		            	if (todoItem != null) {
		            		todoItem.setContent(text);
		            	}
		            	TodoApplication.getTodoController().saveTodoItems();
		            }
		        }
		    });
			
			cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				public void onCheckedChanged(CompoundButton checkBoxView, boolean isChecked) {
					if (!(checkBoxView instanceof CheckBox)) {
						return;
					}
					
					CheckBox cbInner = (CheckBox) checkBoxView;
					
					// Update the content of the model.
	            	TodoItem todoItem = (TodoItem) cbInner.getTag();
	            	if (todoItem != null) {
	            		todoItem.setChecked(isChecked);
	            	}
	            	TodoApplication.getTodoController().saveTodoItems();
				}
			});
			
			if (item.isArchived()) {
				tv.setVisibility(View.VISIBLE);
			}
			else {
				tv.setVisibility(View.GONE);
			}
		}
		
		return convertView;
	}
	
	@Override
	public Object getItem(int position) {				
		if (TodoApplication.getTodoController().getTodoItems() != null) {
			if (TodoApplication.getShowArchives() && TodoApplication.getTodoController().getTodoItems().size() > position) {
				return TodoApplication.getTodoController().getTodoItems().get(position);
			}
			else if (!TodoApplication.getShowArchives() && TodoApplication.getTodoController().getNonarchivedTodoItems().size() > position){
				return TodoApplication.getTodoController().getNonarchivedTodoItems().get(position);
			}
			else {
				return null;
			}
		}
		else {
			return null;
		}
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

}
