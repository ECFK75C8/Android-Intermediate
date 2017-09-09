package com.francis.gitme;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

public class CustomLoader extends AsyncTaskLoader<List<Developer>> {

    public CustomLoader(Context context){
        super(context);
    }

    @Override
    public List<Developer> loadInBackground() {
        return Network.fetchJSONData(getContext().getString(R.string.query_url));
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
