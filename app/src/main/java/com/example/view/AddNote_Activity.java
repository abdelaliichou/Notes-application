package com.example.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.notesapp.Model.Note_model;
import com.example.notesapp.R;
import com.example.notesapp.ViewModel.Notes_ViewModel;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddNote_Activity extends AppCompatActivity {

    TextInputLayout TitleInput, SubTitleInput, BodyInput;
    RelativeLayout DoneButton;
    MaterialCardView greencard, redcard, yellowcard;
    int priority = -1;
    TextView TopText, doneText;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        Setting_Action_Bar_Status_Bar();
        initialisation();
        OnClicks();
        SettingUpdateElements();

    }

    public void SettingUpdateElements() {
        if (getIntent().getStringExtra("title") == null) {
            TopText.setText("Add note");
            doneText.setText("Add Note");
        } else {
            TopText.setText("Modify note");
            doneText.setText("Save changes");
            TitleInput.getEditText().setText(getIntent().getStringExtra("title"));
            SubTitleInput.getEditText().setText(getIntent().getStringExtra("subtitle"));
            BodyInput.getEditText().setText(getIntent().getStringExtra("body"));
            switch (getIntent().getStringExtra("priority")) {
                case "1":
                    greencard.setStrokeWidth(2);
                    yellowcard.setStrokeWidth(0);
                    redcard.setStrokeWidth(0);
                    break;
                case "2":
                    greencard.setStrokeWidth(0);
                    yellowcard.setStrokeWidth(2);
                    redcard.setStrokeWidth(0);
                    break;
                case "3":
                    greencard.setStrokeWidth(0);
                    yellowcard.setStrokeWidth(0);
                    redcard.setStrokeWidth(2);
                    break;
            }
        }
    }

    public void OnClicks() {
        greencard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                greencard.setStrokeWidth(2);
                redcard.setStrokeWidth(0);
                yellowcard.setStrokeWidth(0);
                priority = 1;
            }
        });
        redcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                greencard.setStrokeWidth(0);
                redcard.setStrokeWidth(2);
                yellowcard.setStrokeWidth(0);
                priority = 3;
            }
        });
        yellowcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                greencard.setStrokeWidth(0);
                redcard.setStrokeWidth(0);
                yellowcard.setStrokeWidth(2);
                priority = 2;
            }
        });
        DoneButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                VerifyNote();
            }
        });
    }

    public void VerifyNote() {

        @SuppressLint({"NewApi", "LocalSuppress"})
        String currentTime = new SimpleDateFormat("d MMMM,YYYY", Locale.getDefault()).format(new Date());

        String title = TitleInput.getEditText().getText().toString().trim();
        String subtitle = SubTitleInput.getEditText().getText().toString().trim();
        String body = BodyInput.getEditText().getText().toString().trim();
        Note_model model;


        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(subtitle) || TextUtils.isEmpty(body)) {
            Toast.makeText(AddNote_Activity.this, "Please enter all the note informations ", Toast.LENGTH_SHORT).show();
        } else {

            if (getIntent().getStringExtra("title") == null) {
                if (priority == -1) {
                    model = new Note_model(title, subtitle, currentTime, "1", body);
                } else {
                    model = new Note_model(title, subtitle, currentTime, String.valueOf(priority), body);
                }
                AddNote(model);

            } else {
                // we passed something from the update note activity
                String Note_priority = getIntent().getStringExtra("priority");
                int id = Integer.valueOf(getIntent().getStringExtra("id"));

                if (title.equals(getIntent().getStringExtra("title")) &&
                        subtitle.equals(getIntent().getStringExtra("subtitle")) &&
                        priority == -1 &&
                        body.equals(getIntent().getStringExtra("body"))) {

                    finish();
                    Toast.makeText(this, "Nothing changed !", Toast.LENGTH_SHORT).show();
                } else {
                    if (priority == -1) {
                        model = new Note_model(title, subtitle, currentTime, Note_priority, body);
                    } else {
                        model = new Note_model(title, subtitle, currentTime, String.valueOf(priority), body);
                    }
                    model.setId(id); // must add this so the room can know that this is the dame object as the old one , so it will update it
                    UpdateNote(model);
                }
            }
        }
    }

    public void UpdateNote(Note_model note) {
        Notes_ViewModel.update_Note(note);
        Toast.makeText(this, "Note changed successfully !", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void AddNote(Note_model note) {
        Notes_ViewModel.add_Note(note);
        Toast.makeText(this, "Note added successfully !", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void initialisation() {
        TopText = findViewById(R.id.main_text);
        TitleInput = findViewById(R.id.Title);
        SubTitleInput = findViewById(R.id.SubTitle);
        BodyInput = findViewById(R.id.Notebody);
        DoneButton = findViewById(R.id.doneButton);
        greencard = findViewById(R.id.green);
        redcard = findViewById(R.id.red);
        yellowcard = findViewById(R.id.yellow);
        doneText = findViewById(R.id.doneButtonText);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void Setting_Action_Bar_Status_Bar() {
        //Hiding action bar
        getSupportActionBar().hide();
        // setting the keyboard
        setUpKeybaord(findViewById(R.id.linear), AddNote_Activity.this);

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