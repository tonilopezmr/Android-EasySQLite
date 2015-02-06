package com.tonilopezmr.sample.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tonilopezmr.sample.R;
import com.tonilopezmr.sample.ui.presenter.Presenter;
import com.tonilopezmr.sample.ui.presenter.SubjectListPresenter;
import com.tonilopezmr.sample.ui.view.View;
import com.tonilopezmr.sample.ui.viewmodel.SubjectViewModel;

import java.util.Collection;
import java.util.List;

public class MainActivity extends Activity implements View {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private Presenter presenter;
    private LinearLayout layoutError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        layoutError = (LinearLayout) findViewById(R.id.layout_error);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        presenter = new SubjectListPresenter(this);

        presenter.onInit();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError() {
        layoutError.setVisibility(android.view.View.VISIBLE);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(android.view.View.VISIBLE);
        recyclerView.setVisibility(android.view.View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(android.view.View.GONE);
        recyclerView.setVisibility(android.view.View.VISIBLE);
    }

    @Override
    public void showMessage(String message) {
        showToastMessage(message);
    }

    @Override
    public void showSubjects(Collection<SubjectViewModel> subjects) {
        MyRecyclerAdapter adapter = new MyRecyclerAdapter((List<SubjectViewModel>)subjects, R.layout.item_list);
        adapter.setOnItemClickListener(new MyRecyclerAdapter.OnRecyclerViewItemClickListener<SubjectViewModel>() {
            @Override
            public void onItemClick(android.view.View view, SubjectViewModel subject) {
                presenter.onClickItem(subject.getName());
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }
}
