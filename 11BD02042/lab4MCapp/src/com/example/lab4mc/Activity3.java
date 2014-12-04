package com.example.lab4mc;

import org.json.JSONException;
import org.json.JSONObject;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Activity3 extends ActionBarActivity{
	
	private Button btnAdd;
	private EditText et1, et2;
	private AQuery aq;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_3);
		et1 = (EditText) findViewById(R.id.editText1);
		et2 = (EditText) findViewById(R.id.editText2);
		btnAdd = (Button) findViewById(R.id.button1);
		btnAdd.setOnClickListener(myhandler);
		aq = new AQuery(this);
	}
	
	View.OnClickListener myhandler = new View.OnClickListener() {
		  public void onClick(View v) {
			  String name = et1.getText()+"";
	          String surname = et2.getText()+"";
	          String apiUrl = "http://192.168.56.1:8000/lab4/setUser/?name="+name+"&surname="+surname;
	          aq.ajax(apiUrl, JSONObject.class, Activity3.this, "apiAdd");
		  }
	};
	
	public void apiAdd (String url, JSONObject data, AjaxStatus status){
		Log.d("MC", "code " + status.getCode());
		try{
			String result = data.getString("success");
			Intent intent = new Intent(getApplicationContext(), MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        startActivity(intent);
		}catch(JSONException e){
			e.printStackTrace();
		}
	}
}
