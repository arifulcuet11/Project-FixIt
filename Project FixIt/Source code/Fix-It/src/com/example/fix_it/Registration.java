package com.example.fix_it;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Registration extends Activity {
	EditText name, password, confirmPass;
	Spinner spin;
	Button Register;
	ArrayList<String> nameList = new ArrayList<String>();
	ArrayList<String> age = new ArrayList<String>();
	JSONParser jParser = new JSONParser();
	String userName, userPassword, userType;
	Typeface typeface;
	TextView Reg_text;
	
	  String[] strings = {"Customer","Fixer"};
   
	  String[] subs = {"I would like to get help!", "I like to help the world with my abilities!"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customer_login);
		name = (EditText) findViewById(R.id.cName);
		password = (EditText) findViewById(R.id.cPass);
		confirmPass = (EditText) findViewById(R.id.cCPass);
		spin = (Spinner) findViewById(R.id.cSpinner);
		spin.setAdapter(new MyAdapter(this, R.layout.spinner, strings));
		Register = (Button) findViewById(R.id.register);
		Reg_text = (TextView) findViewById(R.id.register_text);
		typeface = Typeface.createFromAsset(getApplicationContext().getAssets(), "font/GILLUBCD.TTF");
		setFont();
		Register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				userName = name.getText().toString();
				userPassword = password.getText().toString();
				userType = spin.getSelectedItem().toString();
				new AsyncTaskRunnerCurrent().execute();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// ----------- Fetching Current Project
	class AsyncTaskRunnerCurrent extends AsyncTask<String, String, String> {
		ProgressDialog progressDialog = new ProgressDialog(Registration.this);

		String error = "";
		String status,successo;

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub

			try {
				List<NameValuePair> pair = new ArrayList<NameValuePair>();

				pair.add(new BasicNameValuePair("Username", userName));
				pair.add(new BasicNameValuePair("Password", userPassword));
				if (userType.equals("Customer"))
					pair.add(new BasicNameValuePair("User_type", "1"));
				else
					pair.add(new BasicNameValuePair("User_type", "2"));
				// JSONObject
				// json=jParser.makeHttpRequest("http://everestdiners.com/market/get_product_name.php",
				// "GET", pair) ;
				
				JSONObject json2 = jParser.makeHttpRequest(
						"http://fix-it.3eeweb.com/postdata_login.php", "POST",
						pair);
				
				nameList.clear();
				age.clear();

				int success = json2.getInt("success");
				if (success == 1) {
					status = "Username already exist. Please choose another one.";
					success = 0;
				} else {
					status = "Registration successfull.";
				}

			} catch (Exception e) {
				// Toast.makeText(getApplicationContext(),"A problem occured. Please restart the application once again",
				// Toast.LENGTH_LONG).show();

				Log.d("Problemasdddddddddddddd", e + "");
				error = e + "";
			}
			return null;
		}

		protected void onPostExecute(String string) {

			progressDialog.dismiss();
			Intent intent = null;
			Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG).show();
		}

		protected void onPreExecute() {

			progressDialog.setMessage("Please wait. Loading..");
			progressDialog.setCancelable(false);
			progressDialog.show();
		}

	}

	// ------------------------------------------------------------------

	public void setFont()
	{
		Register.setTypeface(typeface);
		Register.setTextSize(25);
		Register.setTextColor(Color.WHITE);
		Register.setText("Register!");
		Reg_text.setTypeface(typeface);
		Reg_text.setTextSize(25);
		Reg_text.setTextColor(Color.WHITE);
		Reg_text.setText("Registration!");
		name.setTypeface(typeface);
		name.setTextSize(15);
		password.setTypeface(typeface);
		password.setTextSize(15);
		confirmPass.setTypeface(typeface);
		confirmPass.setTextSize(15);	
	}
	
	
	  public class MyAdapter extends ArrayAdapter<String>{
	        
	        public MyAdapter(Context context, int textViewResourceId,   String[] objects) {
	            super(context, textViewResourceId, objects);
	        }

	        @Override
	        public View getDropDownView(int position, View convertView,ViewGroup parent) {
	            return getCustomView(position, convertView, parent);
	        }

	        @Override
	        public View getView(int position, View convertView, ViewGroup parent) {
	            return getCustomView(position, convertView, parent);
	        }

	        public View getCustomView(int position, View convertView, ViewGroup parent) {

	            LayoutInflater inflater=getLayoutInflater();
	            View row=inflater.inflate(R.layout.spinner, parent, false);
	            TextView label=(TextView)row.findViewById(R.id.company);
	            label.setText(strings[position]);
	            label.setTypeface(typeface);
	            label.setTextSize(15);
	            TextView sub=(TextView)row.findViewById(R.id.sub);
	            sub.setText(subs[position]);
	            sub.setTypeface(typeface);
	            sub.setTextSize(15);
	            
	            return row;
	            }
	        }
	
	
	
}
