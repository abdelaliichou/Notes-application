package com.example.view;

import static android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.WindowInsetsController;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.compose.material.ripple.Ripple;
import androidx.compose.material.ripple.RippleAnimation;
import androidx.compose.material.ripple.RippleAnimationKt;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.notesapp.Model.Note_model;
import com.example.notesapp.R;
import com.example.notesapp.ViewModel.Notes_ViewModel;
import com.example.notesapp.adapter.RecyclerViewAdapter;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Notes_ViewModel viewModel;
    RecyclerView recyclerview;
    RecyclerViewAdapter recyclerViewAdapter;
    FloatingActionButton floatingActionButton;
    MaterialCardView none, high, low;
    TextView noneText, highText, lowText;
    ImageView clearImage;
    EditText searchEditeText;
    SwitchCompat switchCompat;
    ArrayList<Note_model> list;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Setting_Action_Bar_Status_Bar();
        initialisation();
        SettingRecycler();
        FloatingButton();
        onclicks();
        SearchNote();


    }

    public void SearchNote() {
        recyclerViewAdapter = new RecyclerViewAdapter(list, MainActivity.this);
        searchEditeText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                recyclerViewAdapter.cancelTimer();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                recyclerViewAdapter.Search_note(editable.toString());
                if (editable.toString().trim().length() > 0) {
                    clearImage.setVisibility(View.VISIBLE);
                } else {
                    clearImage.setVisibility(View.GONE);
                }
            }
        });
    }

    public void FloatingButton() {
        recyclerview.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState > 0) {
                    floatingActionButton.hide();
                } else {
                    floatingActionButton.show();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddNote_Activity.class));
            }
        });
    }

    public void onclicks() {
        clearImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchEditeText.setText("");
            }
        });
        none.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!searchEditeText.getText().toString().trim().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Can't use filter when searching , please clear the search !", Toast.LENGTH_SHORT).show();
                } else {
                    none.setCardBackgroundColor(getResources().getColor(R.color.yellowlite));
                    high.setCardBackgroundColor(getResources().getColor(R.color.cardback));
                    low.setCardBackgroundColor(getResources().getColor(R.color.cardback));
                    noneText.setTextColor(getResources().getColor(R.color.white));
                    lowText.setTextColor(getResources().getColor(R.color.black));
                    highText.setTextColor(getResources().getColor(R.color.black));

                    // setting the recycler view list filtered
                    viewModel.get_All().observe(MainActivity.this, model_classes -> {
                        recyclerViewAdapter = new RecyclerViewAdapter(model_classes, MainActivity.this);
                        recyclerview.setLayoutManager(new StaggeredGridLayoutManager(2, 1));
                        recyclerview.setAdapter(recyclerViewAdapter);
                    });
                }
            }
        });
        high.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!searchEditeText.getText().toString().trim().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Can't use filter when searching , please clear the search !", Toast.LENGTH_SHORT).show();
                } else {
                    none.setCardBackgroundColor(getResources().getColor(R.color.cardback));
                    high.setCardBackgroundColor(getResources().getColor(R.color.yellowlite));
                    low.setCardBackgroundColor(getResources().getColor(R.color.cardback));
                    noneText.setTextColor(getResources().getColor(R.color.black));
                    lowText.setTextColor(getResources().getColor(R.color.black));
                    highText.setTextColor(getResources().getColor(R.color.white));

                    viewModel.get_All_High_To_Low().observe(MainActivity.this, model_classes -> {
                        recyclerViewAdapter = new RecyclerViewAdapter(model_classes, MainActivity.this);
                        recyclerview.setLayoutManager(new StaggeredGridLayoutManager(2, 1));
                        recyclerview.setAdapter(recyclerViewAdapter);
                    });
                }
            }
        });
        low.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!searchEditeText.getText().toString().trim().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Can't use filter when searching , please clear the search !", Toast.LENGTH_SHORT).show();
                } else {
                    none.setCardBackgroundColor(getResources().getColor(R.color.cardback));
                    high.setCardBackgroundColor(getResources().getColor(R.color.cardback));
                    low.setCardBackgroundColor(getResources().getColor(R.color.yellowlite));
                    noneText.setTextColor(getResources().getColor(R.color.black));
                    lowText.setTextColor(getResources().getColor(R.color.white));
                    highText.setTextColor(getResources().getColor(R.color.black));

                    viewModel.get_All_Low_To_High().observe(MainActivity.this, model_classes -> {
                        recyclerViewAdapter = new RecyclerViewAdapter(model_classes, MainActivity.this);
                        recyclerview.setLayoutManager(new StaggeredGridLayoutManager(2, 1));
                        recyclerview.setAdapter(recyclerViewAdapter);
                    });
                }
            }
        });

        // Night mode
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });
    }

    public void initialisation() {
        list = new ArrayList<>();
        switchCompat = findViewById(R.id.daynight);
        clearImage = findViewById(R.id.clear);
        searchEditeText = findViewById(R.id.search);
        noneText = findViewById(R.id.nonetext);
        highText = findViewById(R.id.hightext);
        lowText = findViewById(R.id.lowtext);
        none = findViewById(R.id.none);
        low = findViewById(R.id.low);
        high = findViewById(R.id.high);
        floatingActionButton = findViewById(R.id.floating);
        recyclerview = findViewById(R.id.recycler);
        viewModel = new ViewModelProvider
                .AndroidViewModelFactory(MainActivity.this.getApplication())
                .create(Notes_ViewModel.class);
    }

    public void SettingRecycler() {
        viewModel.get_All().observe(this, model_classes -> {
            list = (ArrayList<Note_model>) model_classes;
            recyclerViewAdapter = new RecyclerViewAdapter(model_classes, MainActivity.this);
            recyclerview.setLayoutManager(new StaggeredGridLayoutManager(2, 1));
            //recyclerview.setLayoutManager(new GridLayoutManager(this,2));
            recyclerview.setAdapter(recyclerViewAdapter);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void Setting_Action_Bar_Status_Bar() {

        getSupportActionBar().hide();
        setUpKeybaord(findViewById(R.id.parent),MainActivity.this);
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