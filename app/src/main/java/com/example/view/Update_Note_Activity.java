package com.example.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.notesapp.R;
import com.google.android.material.card.MaterialCardView;


public class Update_Note_Activity extends AppCompatActivity {

    RelativeLayout update;
    MaterialCardView priority;
    TextView title, subtitle, body;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);
        Setting_Action_Bar_Status_Bar();
        initialisation();
        initialeNote();
        onClicks();


    }

    public void onClicks() {
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Update_Note_Activity.this, AddNote_Activity.class);
                intent.putExtra("title", getIntent().getStringExtra("title"));
                intent.putExtra("subtitle", getIntent().getStringExtra("subtitle"));
                intent.putExtra("priority", getIntent().getStringExtra("priority"));
                intent.putExtra("body", getIntent().getStringExtra("body"));
                intent.putExtra("id", getIntent().getStringExtra("id"));
                Log.d("magsssss", getIntent().getStringExtra("id"));
                startActivity(intent);
                finish();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void initialeNote() {
        title.setText(getIntent().getStringExtra("title"));
        subtitle.setText(getIntent().getStringExtra("subtitle"));
        body.setText(getIntent().getStringExtra("body"));
        switch (getIntent().getStringExtra("priority")) {
            case "1":
                priority.setCardBackgroundColor(getColor(R.color.green));
                break;
            case "2":
                priority.setCardBackgroundColor(getColor(R.color.yellow));
                break;
            case "3":
                priority.setCardBackgroundColor(getColor(R.color.red));
                break;
        }
    }

    public void initialisation() {
        update = findViewById(R.id.update);
        priority = findViewById(R.id.priority);
        title = findViewById(R.id.title);
        subtitle = findViewById(R.id.subtitle);
        body = findViewById(R.id.body);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void Setting_Action_Bar_Status_Bar() {
        //Hiding action bar
        getSupportActionBar().hide();
        // setting the keyboard
        setUpKeybaord(findViewById(R.id.linear), Update_Note_Activity.this);

        this.getWindow().setStatusBarColor(getColor(R.color.back));
        this.getWindow().setNavigationBarColor(getColor(R.color.back));
    }

    public static void SettingKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isAcceptingText()) {
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(),
                    0
            );
        }
    }

    // hiding the keyboard when we clicks any where ( better user experience )
    public static void setUpKeybaord(View view, Activity activity) {
        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    SettingKeyboard(activity);
                    return false;
                }
            });
        }
        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setUpKeybaord(innerView, activity);
            }
        }
    }
}