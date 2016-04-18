package com.munchies.abhishek.munchies;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.AccountPicker;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    ImageButton signIn;
    String accountName;
    String name, email, dpPath;
    Activity act;


    String token;
    String GMAIL_SCOPE="oauth2:https://www.googleapis.com/auth/userinfo.email " +
            "https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/plus.login " +
            "https://www.googleapis.com/auth/plus.me";
    public JSONObject json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signIn = (ImageButton) findViewById(R.id.signIn);
        signIn.setOnClickListener(this);
        act = this;
        signIn.setBackgroundResource(R.drawable.login_google);
        signIn.setAdjustViewBounds(true);

        SharedPreferences sp = getApplication().getSharedPreferences("com.munchies.abhishek", Context.MODE_PRIVATE);
        if(sp.getBoolean("login", false) == true){
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            i.putExtra("name", sp.getString("name", "Shubham Goswami"));
            i.putExtra("email", sp.getString("email", "shubham14100@iiitd.ac.in"));
            i.putExtra("dpPath", sp.getString("dpPath", "https://lh4.googleusercontent.com/-f_BKM9EgYz8/AAAAAAAAAAI/AAAAAAAAC5g/EgAeeDNbnbw/photo.jpg"));
            startActivity(i);

        }
    }

    @Override
    public void onClick(View v) {
        if(isNetworkAvailable()){
            if(v.getId() == R.id.signIn){
                Intent googlePicker = AccountPicker.newChooseAccountIntent(null,
                        null, new String[]{GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE},
                        true, null, null, null, null);
                startActivityForResult(googlePicker, 1);
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
            Log.d("acount", accountName);

            new getAuthToken().execute();

        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connMgr = (ConnectivityManager) this
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    public class getAuthToken extends AsyncTask<String, String, String> {

        ProgressDialog dialog = new ProgressDialog(act);

        @Override
        protected void onPreExecute() {
            // Show Progress dialog
            dialog.setMessage("Authenticating..");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... arg0) {
            try {
                token = GoogleAuthUtil.getToken(act,
                        accountName, GMAIL_SCOPE);

            } catch (UserRecoverableAuthException e) {
                startActivityForResult(e.getIntent(), 1);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (GoogleAuthException e) {
                e.printStackTrace();
            }

            return token;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
//            Log.d("tokenval", s);
//            mLoginbtn.setVisibility(View.INVISIBLE);
            //signIn.setText("LOGOUT");
            try {
                new Import().execute("OK").get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            //_signupLink.setText("token:" + s);
//            Intent ii= new Intent(getActivity(), siddharth.com.tagtraqr.MapsActivity.class);
//            startActivity(ii);

        }
    }

    class Import extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            String result = "";
            if(token != null){
                String str = "https://www.googleapis.com/oauth2/v1/userinfo?alt=json&access_token="+token;
                try {
                    URL url = new URL(str);
                    HttpURLConnection connect = (HttpURLConnection) url.openConnection();
                    InputStream in = connect.getInputStream();
                    InputStreamReader ir = new InputStreamReader(in);
                    int data = ir.read();
                    while(data != -1){
                        char c = (char)data;
                        result += c;
                        data = ir.read();
                    }
                    Log.d("result",result);
                    JSONObject json = new JSONObject(result);
                    URL url1 = new URL("http://192.168.2.0:4000/login/"+json.getString("email"));
                    HttpURLConnection connect1 = (HttpURLConnection) url1.openConnection();
                    connect1.setRequestMethod("POST");
                    connect1.setRequestProperty("Content-Type", "application/json");
                    connect1.setDoInput(true);
                    connect1.setDoOutput(true);
                    //connect.connect();
                    OutputStream os = connect1.getOutputStream();
                    OutputStreamWriter ow = new OutputStreamWriter(os);
                    ow.write(json.toString());
                    ow.flush();
                    ow.close();

                    InputStream in1 = connect1.getInputStream();
                    InputStreamReader ir1 = new InputStreamReader(in1);
                    String str1 = "";
                    data = ir1.read();
                    while(data != -1){
                        char c = (char)data;
                        str1 += c;
                        data = ir1.read();
                    }
                    name = json.getString("name");
                    email = json.getString("email");
                    dpPath = json.getString("picture");
                    Log.d("logInfo", json.toString());

                    SharedPreferences sp = getApplication().getSharedPreferences("com.munchies.abhishek", Context.MODE_PRIVATE);
                    sp.edit().putString("email",email);
                    sp.edit().putString("name", name);
                    sp.edit().putString("dpPath", dpPath);
                    sp.edit().putBoolean("login", true);

                    Intent i = new Intent(getApplicationContext(),MainActivity.class);
                    i.putExtra("name",name);
                    i.putExtra("email", email);
                    i.putExtra("dpPath", dpPath);
                    startActivity(i);


                    return str1;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }
    }
}
