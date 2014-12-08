package com.example.mobile_com_lab_4;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import android.view.View;
import android.widget.EditText;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

public class AddActivity extends Activity {
	
	private ProgressDialog dialog;
	private MyTask myTask;
	private EditText editText;
	private JSONParser jsonParser;
	private List<NameValuePair> pairs;
	private JSONObject jsonObject;
	private static String url_create_new_product = "http://172.16.92.136/php/create_new_product.php";
	private static final String TAG_SUCCESS = "success";
	private int success;
	private Intent intent;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		getActionBar().hide();
		editText = (EditText) findViewById(R.id.newProduct);
		myTask = new MyTask();
		jsonParser = new JSONParser();
	}
	
	public void btnClick(View view) {
		myTask.execute();
	}

	class MyTask extends AsyncTask<Void, Void, Void> {
		
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(AddActivity.this);
			dialog.setMessage("Creating new product...");
			dialog.setIndeterminate(false);
			dialog.setCancelable(true);
			dialog.show();
		}
		
		protected Void doInBackground(Void... params) {
			try {
				pairs = new ArrayList<NameValuePair>();
				pairs.add(new BasicNameValuePair("full_name", editText.getText().toString()));
				jsonObject = jsonParser.makeHttpRequest(url_create_new_product, "POST", pairs);
				success = jsonObject.getInt(TAG_SUCCESS);
				if(success == 1) {
					intent = new Intent(getApplicationContext(), MainActivity.class);
					startActivity(intent);
					finish();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			dialog.dismiss();
		}
		
	}
}
