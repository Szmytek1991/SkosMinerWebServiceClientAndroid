package com.szmytek.srir_webservice_skos_miner;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.util.Date;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Activity activity = this;
        final Context context = getApplicationContext();
        final Button button = (Button) findViewById(R.id.button);
        final EditText name = (EditText) findViewById(R.id.editText);
        final EditText surname = (EditText) findViewById(R.id.editText2);
        final TextView tv = (TextView) findViewById(R.id.textView3);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Date d1 = new Date();
                if(name.getText().toString().equals("") || surname.getText().toString().equals("")){
                    Toast.makeText(context, "Pola nie mogą być puste!",Toast.LENGTH_SHORT).show();
                }
                else{
                new Thread(new Runnable() {
                    public void run() {
                        WebServiceCall wsc = new WebServiceCall();
                        final String result = wsc.Call(name.getText().toString(), surname.getText().toString());
                        final String[] split = result.split(";");
                        activity.runOnUiThread(new Runnable() {
                            public void run() {
                                tv.setText("");
                                for(String s : split){
                                    tv.append(s +"\n");
                                }
                                Date d2=new Date();
                                long interval = d2.getTime() - d1.getTime();
                                Toast.makeText(context, "Operacja wykonana w ciagu " + interval + " milisekund",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).start();
                }

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
