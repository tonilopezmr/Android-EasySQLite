package com.tonilopezmr.sample.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.tonilopezmr.sample.R;
import com.tonilopezmr.sample.di.BaseActivity;
import com.tonilopezmr.sample.ui.presenter.MainPresenter;
import com.tonilopezmr.sample.ui.presenter.SubjectListPresenterImp;
import com.tonilopezmr.sample.ui.view.SubjectListView;
import com.tonilopezmr.sample.ui.viewmodel.SubjectViewModel;
import com.tonilopezmr.sample.ui.viewmodel.SubjectViewModelImp;

import java.util.Collection;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends BaseActivity implements SubjectListView {

    MainPresenter presenter;

    @InjectView(R.id.my_recycler_view)
    RecyclerView recyclerView;
    @InjectView(R.id.progress_bar)
    ProgressBar progressBar;
    @InjectView(R.id.layout_error)
    LinearLayout layoutError;
    @InjectView(R.id.floating_button)
    FloatingActionButton floatingButton;

    MyRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        inicializeRecyclerView(recyclerView);
        init();
    }

    private void init(){
        presenter = new SubjectListPresenterImp(this);

        floatingButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                presenter.onFloatingButtonClick(new SubjectViewModelImp("New Subject"));
            }
        });

        presenter.onInit();
    }

    private void inicializeRecyclerView(RecyclerView recyclerView){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new MyRecyclerAdapter(R.layout.item_list);
        adapter.setOnItemClickListener(new MyRecyclerAdapter.OnRecyclerViewItemClickListener<SubjectViewModel>() {
            @Override
            public void onItemClick(android.view.View view, SubjectViewModel subject) {
                presenter.onClickItem(subject);
            }
        });
        adapter.setOnLongClickListener(new MyRecyclerAdapter.OnRecyclerViewItemLongClickListener<SubjectViewModel>() {
            @Override
            public void onItemLongClick(View view, SubjectViewModel subject) {
                presenter.onLongItemClick(subject);
            }
        });
        recyclerView.setAdapter(adapter);
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
    public boolean isShowLayoutError() {
        return layoutError.getVisibility() == View.VISIBLE;
    }

    @Override
    public void hideLayoutError() {
        layoutError.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLayoutError() {
        floatingButton.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        layoutError.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProgress() {
        recyclerView.setVisibility(View.INVISIBLE);
        layoutError.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(android.view.View.VISIBLE);
    }

    @Override
    public void showMessage(String message) {
        showToastMessage(message);
    }

    @Override
    public void showSubjects(Collection<SubjectViewModel> subjects) {
        adapter.add((List<SubjectViewModel>) subjects);
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void add(SubjectViewModel subjectModel) {
        adapter.add(subjectModel);
    }

    @Override
    public void remove(SubjectViewModel subjectModel) {
        adapter.remove(subjectModel);
    }
}
