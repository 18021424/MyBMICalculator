package sg.edu.rp.c346.mybmicalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
EditText et,et2;
Button bt,bt2;
TextView tv,tv2,tv3;
String datetime = "";
Float bmi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et = findViewById(R.id.editText);
        et2 = findViewById(R.id.editText2);
        bt = findViewById(R.id.button);
        bt2 = findViewById(R.id.button2);
        tv = findViewById(R.id.textView);
        tv2 = findViewById(R.id.textView2);
        tv3 = findViewById(R.id.textView3);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(et.getText().toString().isEmpty()) && !(et2.getText().toString().isEmpty())) {

                    Float weight = Float.parseFloat(et.getText().toString());
                    Float height = Float.parseFloat(et2.getText().toString());
                     bmi = weight / (height * height);
                    Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
                     datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                            (now.get(Calendar.MONTH) + 1) + "/" +
                            now.get(Calendar.YEAR) + " " +
                            now.get(Calendar.HOUR_OF_DAY) + ":" +
                            now.get(Calendar.MINUTE);
                    tv.setText("Last Calculated Date: " + datetime);
                    tv2.setText(String.format("Last Calculated BMI: %.3f", bmi));
                    et.setText("");
                    et2.setText("");
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                    SharedPreferences.Editor prefEdit = prefs.edit();
                    prefEdit.putString("key","Last Calculated Date: " + datetime);
                    prefEdit.putString("key2",String.format("Last Calculated BMI: %.3f", bmi));
                    if (bmi < 18.5){
                        prefEdit.putString("key3","You are underweight");
                        tv3.setText("You are underweight");
                    }
                    else if(bmi >= 18.5 && bmi <= 24.9){
                        prefEdit.putString("key3","Your BMI is normal");
                        tv3.setText("Your BMI is normal");
                    }
                    else if (bmi >= 25 && bmi <= 29.9){
                        prefEdit.putString("key3","You are overweight");
                        tv3.setText("You are overweight");
                    }
                    else{
                        prefEdit.putString("key3","You are obese");
                        tv3.setText("You are obese");
                    }


                    prefEdit.commit();
                }
                else{
                    Toast.makeText(MainActivity.this,"Fill in both weight and height",Toast.LENGTH_SHORT).show();
                }
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor prefEdit = prefs.edit();
                prefEdit.putString("key","Last Calculated Date: ");
                prefEdit.putString("key2","Last Calculated BMI:");
                prefEdit.putString("key3","");

                tv.setText("Last Calculated Date:");
                tv2.setText("Last Calculated BMI:");
                tv3.setText("");
                prefEdit.commit();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String msg = prefs.getString("key","Last Calculated Date:");
        String msg2 = prefs.getString("key2","Last Calculated BMI: 0.0");
        String msg3 = prefs.getString("key3","");

        tv.setText(msg);
        tv2.setText(msg2);
        tv3.setText(msg3);

    }

}
