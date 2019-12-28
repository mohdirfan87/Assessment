package com.example.assingment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.example.assingment.networking.NetworkUtil;
import com.example.assingment.ui.facts.fragments.FactsFragment;

public class MainActivity extends AppCompatActivity implements FactsFragment.FragmentCallback {
    private TextView toolbar_title;
    private ImageButton refresh;
    public FactsFragment factsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        toolbar_title = findViewById(R.id.toolbar_title);
        refresh = findViewById(R.id.refresh);
        factsFragment = FactsFragment.newInstance();

        refresh.setOnClickListener(view -> {
            if (!NetworkUtil.isNetworkAvailable(this)) {
                Toast.makeText(this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                return;
            }
            factsFragment.refreshFacts();
        });
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, factsFragment)
                    .commitNow();
        }
    }


    @Override
    public void titleUpdate(String title) {
        toolbar_title.setText(title);
    }
}
