package com.example.lab4mc;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;

public class Activity2 extends ActionBarActivity{
	
	private Button btnEdit, btnDelete, btnAdd;
	private EditText et1, et2;
	private AQuery aq;
	private int id;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_2);
		et1 = (EditText) findViewById(R.id.editText1);
		et2 = (EditText) findViewById(R.id.editText2);
		btnEdit = (Button) findViewById(R.id.button2);
		btnDelete = (Button) findViewById(R.id.button1);
		id = getIntent().getIntExtra("Answer",-1);
		String apiUrl = "http://192.168.56.1:8000/lab4/getUser/?id="+id;
		aq = new AQuery(this);
		aq.ajax(apiUrl, JSONObject.class, Activity2.this, "apiCallback");
		btnEdit.setOnClickListener(myhandler);
		btnDelete.setOnClickListener(myhandler);
	}
	
	View.OnClickListener myhandler = new View.OnClickListener() {
		  public void onClick(View v) {
		      switch(v.getId()) {
		        case R.id.button2:
		          String name = et1.getText()+"";
		          String surname = et2.getText()+"";
		          String apiUrl1 = "http://192.168.56.1:8000/lab4/editUser/?id="+id+"&name="+name+"&surname="+surname;
		          aq.ajax(apiUrl1, JSONObject.class, Activity2.this, "apiEdit");
		          break;
		        case R.id.button1:
		          String apiUrl2 = "http://192.168.56.1:8000/lab4/deleteUser/?id="+id;
			      aq.ajax(apiUrl2, JSONObject.class, Activity2.this, "apiDelete");
		          break;
		      }
		  }
		};
	
	public void apiCallback(String url, JSONObject data, AjaxStatus status){
		Log.d("MC", "code " + status.getCode());
		try{
			String name = data.getString("name");
			String surname = data.getString("surname");
			et1.setText(name);
			et2.setText(surname);
		}catch(JSONException e){
			e.printStackTrace();
		}
	}
	
	public void apiEdit(String url, JSONObject data, AjaxStatus status){
		Log.d("MC", "code " + status.getCode());
		try{
			String result = data.getString("success");
			Intent intent = new Intent(getApplicationContext(),MainActivity.class);
	        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		    startActivity(intent);
		}catch(JSONException e){
			e.printStackTrace();
		}
	}
	
	public void apiDelete(String url, JSONObject data, AjaxStatus status){
		Log.d("MC", "code " + status.getCode());
		try{
			String result = data.getString("success");
			Intent intent = new Intent(getApplicationContext(),MainActivity.class);
	        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		    startActivity(intent);
		}catch(JSONException e){
			e.printStackTrace();
		}
	}
}
