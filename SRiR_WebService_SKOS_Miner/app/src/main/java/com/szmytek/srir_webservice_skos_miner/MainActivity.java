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

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.Time;
import java.util.Date;


public class MainActivity extends Activity {

    Activity activity;
    Context context;
    EditText name;
    EditText surname;
    TextView tv;
    @Override
    //! This is initial method for main activity
        /*!
          \param savedInstanceState Bundle Bundle object
          \sa onCreate()
        */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
        context = getApplicationContext();
        name = (EditText) findViewById(R.id.editText);
        surname = (EditText) findViewById(R.id.editText2);
        tv = (TextView) findViewById(R.id.textView3);
    }

    //! Button click, after click it ask first server for person information if failed ask second server
        /*!
          \param view View View object
          \sa getInfoButtonClick()
        */
    public void getInfoButtonClick(View view) {
        final Date d1 = new Date();
        if(name.getText().toString().equals("") || surname.getText().toString().equals("")){
            Toast.makeText(context, "Pola nie mogą być puste!",Toast.LENGTH_SHORT).show();
        }
        else{
            new Thread(new Runnable() {
                public void run() {
                    WebServiceCall wsc = new WebServiceCall("http://webserviceskosminer.gear.host/WebService.asmx");
                    String result="";
                    try {
                        result = wsc.Call(name.getText().toString(), surname.getText().toString());
                    } catch (UnknownHostException uhEx) {
                        activity.runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(context, "No internet connection",Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (Exception e) {
                        activity.runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(context, "Wystapił błąd polaczenia z serwerem wywoluje serwer alternatywny",Toast.LENGTH_LONG).show();
                            }
                        });

                        WebServiceCall wsc2 = new WebServiceCall("http://skosminer-001-site1.mywindowshosting.com/WebService.asmx");
                        try {
                            result = wsc2.Call(name.getText().toString(), surname.getText().toString());
                        } catch (UnknownHostException uhEx) {
                            Toast.makeText(context, "No internet connection",Toast.LENGTH_SHORT).show();
                        } catch (Exception ex) {
                            result="Oba serwery zawiodły \n" + ex.getMessage();
                        }
                    }
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
}
