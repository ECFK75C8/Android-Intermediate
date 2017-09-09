package com.francis.gitme;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainScreen extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Developer>> {

    ListView listView;
    DeveloperAdapter adapter;
    TextView empty_list_view_text;
    ProgressBar progressBar;
    private static final int LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        // TODO: 09/09/2017 Add more functionality to your app

        listView= (ListView) findViewById(R.id.list);
        progressBar = (ProgressBar) findViewById(R.id.progress_indicator);
        empty_list_view_text = (TextView) findViewById(R.id.no_network);
        adapter = new DeveloperAdapter(this, new ArrayList<Developer>());
        listView.setAdapter(adapter);
        listView.setEmptyView(empty_list_view_text);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Developer selected_developer = adapter.getItem(position);
                Bundle args = new Bundle();
                args.putString("username", selected_developer.getUsername());
                args.putString("image_url", selected_developer.getImage_url());
                args.putString("dev_url", selected_developer.getDeveloper_url());
                Intent intent = new Intent(MainScreen.this, ProfileScreen.class);
                intent.putExtras(args);
                startActivity(intent);
            }
        });

        ConnectivityManager conn = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo connection = conn.getActiveNetworkInfo();

        if (connection != null && connection.isConnected()){
            getSupportLoaderManager().initLoader(LOADER_ID, null, this);
        }
        else{
            progressBar.setVisibility(View.GONE);
            empty_list_view_text.setText(getString(R.string.no_network_text));
        }

    }

    @Override
    public Loader<List<Developer>> onCreateLoader(int id, Bundle args) {
        return new CustomLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<Developer>> loader, List<Developer> data) {
        adapter.clear();

        progressBar.setVisibility(View.GONE);
        empty_list_view_text.setText(R.string.no_developer_text);

        if (data != null && !data.isEmpty()){
            adapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Developer>> loader) {
        adapter.clear();
    }
}
