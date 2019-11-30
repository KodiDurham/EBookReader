package com.example.ebookreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SettingActivity extends AppCompatActivity {

    String SETTING_TEXTSIZE="size";
    int textSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        SharedPreferences preferencesGet = PreferenceManager.getDefaultSharedPreferences( this );
        textSize = preferencesGet.getInt( SETTING_TEXTSIZE, 14);

        final EditText inputTextSize = findViewById(R.id.et_fontSize);

        inputTextSize.setText(""+textSize);

        inputTextSize.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                TextView example=findViewById(R.id.tv_exampleFS);
                if(!inputTextSize.getText().toString().equals(""))
                    example.setTextSize(Integer.parseInt( (inputTextSize.getText().toString()) ));
            }
        });

        Button updateBtn= findViewById(R.id.btn_update);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get values
                textSize= Integer.parseInt( (inputTextSize.getText().toString()) ) ;

                //set value
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt( SETTING_TEXTSIZE, textSize);
                editor.apply();

                finish();
            }
        });

    }
}
