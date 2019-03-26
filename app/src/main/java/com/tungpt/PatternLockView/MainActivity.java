package com.tungpt.PatternLockView;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private Adapter mAdapter;
    private View mFabBGLayout;
    private LinearLayout mFabLayout1;
    private FloatingActionButton mFab;
    private FloatingActionButton mFab1, mFab2;
    boolean isFABOpen = false;
    private static ArrayList<Model> mModelList;
    private static int flare = 0;

    public static ArrayList<Model> getmModelList() {
        return mModelList;
    }

    public static void setmModelList(ArrayList<Model> mModelList) {
        MainActivity.mModelList = mModelList;
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flare++;
        initData();
        initRecyclerView();
    }

    public void initRecyclerView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mRecyclerView = findViewById(R.id.recycler_list_item);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this,
                        LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new Adapter(this, mModelList);
        mRecyclerView.setAdapter(mAdapter);

        mFabLayout1 = findViewById(R.id.fabLayout1);
        mFabBGLayout = findViewById(R.id.fabBGLayout);
        mFab = findViewById(R.id.fab);
        mFab1 = findViewById(R.id.fab1);
        mFab2 = findViewById(R.id.fab2);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFABOpen) {
                    showFABMenu();
                } else {
                    closeFABMenu();
                }
            }
        });

        mFabBGLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFABMenu();
            }
        });

        mFab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(DetailActivity.getIntent(MainActivity.this));
            }
        });

        mFab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(CreatePasswordPattern.getIntent(MainActivity.this));
            }
        });
    }

    public void initData() {
        if (flare == 1) {
            mModelList = new ArrayList<>();
            mModelList.add(new Model("aaaa", "", "", ""));
            mModelList.add(new Model("aaaa", "", "", ""));
        }
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
            Intent intent = new Intent(this, ChangeListener.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    private void showFABMenu() {
        isFABOpen = true;
        mFabLayout1.setVisibility(View.VISIBLE);
        mFabBGLayout.setVisibility(View.VISIBLE);

        mFab.animate().rotationBy(180).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (mFab.getRotation() != 180) {
                    mFab.setRotation(180);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        mFabLayout1.animate().translationY(-getResources().getDimension(R.dimen.dp_55));
    }

    private void closeFABMenu() {
        isFABOpen = false;
        mFabBGLayout.setVisibility(View.GONE);
        mFab.animate().rotationBy(-180);
        mFabLayout1.animate().translationY(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (!isFABOpen) {
                    mFabLayout1.setVisibility(View.GONE);
                }
                if (mFab.getRotation() != -180) {
                    mFab.setRotation(-180);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (isFABOpen) {
            closeFABMenu();
        } else {
            super.onBackPressed();
        }
    }
}
