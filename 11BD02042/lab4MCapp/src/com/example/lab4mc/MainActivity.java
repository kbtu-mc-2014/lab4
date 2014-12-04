package com.example.lab4mc;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;

public class MainActivity extends ActionBarActivity {

	private Button btnAdd;
	private ListView listView;
	private AQuery aq;
	private ArrayList <Integer> idList = new ArrayList<Integer>();
	private ArrayAdapter<String> adapter;
	private ArrayList<String> list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView) findViewById(R.id.listView1);
		btnAdd = (Button) findViewById(R.id.button1);
		btnAdd.setOnClickListener(myhandler);
		String apiUrl = "http://192.168.56.1:8000/lab4/getUsersList/";
		aq = new AQuery(this);
		aq.ajax(apiUrl, JSONObject.class, MainActivity.this, "apiCallback");
		list = new ArrayList<String>();
		listView.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	          Log.d("MC", "itemClick: position = " + position + ", id = "+id);
	          Intent intent = new Intent(getApplicationContext(), Activity2.class);
	          int res = (int)id;
	          int res2 = idList.get(res);
	          intent.putExtra("Answer", res2);
	          startActivity(intent);
	        }
	      });
	}
	
	View.OnClickListener myhandler = new View.OnClickListener() {
		  public void onClick(View v) {
			  Intent intent = new Intent(getApplicationContext(), Activity3.class);
	          startActivity(intent);
		  }
		};
		
	protected void onResume(){
		super.onResume();
	}
	
	public void apiCallback(String url, JSONObject data, AjaxStatus status){
		Log.d("MC", "code " + status.getCode());
		try{
			JSONArray users = data.getJSONArray("Users");
			for (int i=0; i<users.length(); i++){
				JSONObject ob = users.getJSONObject(i);
				String name = ob.getString("name");
				String surname = ob.getString("surname");
				int id = ob.getInt("id");
				list.add(name+" "+surname);
				idList.add(id);
			}
			adapter = new ArrayAdapter<String>(this,
			        android.R.layout.simple_list_item_1, list);
			listView.setAdapter(adapter);
		}catch(JSONException e){
			e.printStackTrace();
		}
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
}
