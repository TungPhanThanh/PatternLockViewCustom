package com.tungpt.PatternLockView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DetailActivity extends AppCompatActivity {

    private Button mButtonAdd;
    private Button mButtonDelete;
    private EditText mEditTextTitle;
    private EditText mEditTextWebsite;
    private EditText mEditTextId;
    private EditText mEditTextPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_info);
        initView();
        mButtonAdd = findViewById(R.id.button_add);
        mButtonDelete = findViewById(R.id.button_delete);
        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ModelValue model = new ModelValue(mEditTextTitle.getText().toString(),
//                        mEditTextWebsite.getText().toString(),
//                        mEditTextId.getText().toString(),
//                        mEditTextPassword.getText().toString());
//                MainActivity.getmModelList().add(model);
                Intent intent = MainActivity.getIntent(DetailActivity.this);
                startActivity(intent);
            }
        });
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, DetailActivity.class);
    }
    public void initView(){
        mEditTextTitle = findViewById(R.id.edit_title);
        mEditTextWebsite = findViewById(R.id.edit_website);
        mEditTextId = findViewById(R.id.edit_id);
        mEditTextPassword = findViewById(R.id.edit_password);
    }
}
