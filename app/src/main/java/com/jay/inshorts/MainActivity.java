package com.jay.inshorts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.jay.inshorts.model.News;
import com.jay.inshorts.retrofit.ApiClient;
import com.jay.inshorts.retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, NewsAdapter.MessageAdapterListener {

    private List<News> messages = new ArrayList<>();
    private RecyclerView recyclerView;
    private NewsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new NewsAdapter(this, messages, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        downloadNews();
    }

    @Override
    public void onRefresh() {
        downloadNews();
    }

    @Override
    public void onIconImportantClicked(int position) {
        // Star icon is clicked,
        // mark the message as important
        News message = messages.get(position);
        message.setFavorite(!message.isFavorite());
        messages.set(position, message);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onMessageRowClicked(int position) {
        Intent intent = new Intent(MainActivity.this, BrowserActivity.class);
        intent.putExtra("url", messages.get(position).getUrl());
        startActivity(intent);
    }

    @Override
    public void onRowLongClicked(int position) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.old_to_new:
                Collections.sort(messages, new Comparator<News>() {
                    @Override
                    public int compare(News o1, News o2) {
                        return Long.valueOf(o1.getTimestamp()).compareTo(o2.getTimestamp());
                    }
                });
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.new_to_old:
                Collections.sort(messages, new Comparator<News>() {
                    @Override
                    public int compare(News o1, News o2) {
                        return Long.valueOf(o2.getTimestamp()).compareTo(o1.getTimestamp());
                    }
                });
                mAdapter.notifyDataSetChanged();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void downloadNews() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<News>> call = apiService.getNews();
        call.enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                for (int i = 0; i < response.body().size(); i++) {
                    for (News message : response.body()) {
                        messages.add(message);
                    }
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
