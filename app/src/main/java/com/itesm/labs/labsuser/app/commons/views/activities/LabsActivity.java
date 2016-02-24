package com.itesm.labs.labsuser.app.commons.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.bases.BaseActivity;
import com.itesm.labs.labsuser.app.commons.adapters.LabsRecyclerAdapter;
import com.itesm.labs.labsuser.app.commons.adapters.models.ItemLaboratory;
import com.itesm.labs.labsuser.app.commons.events.DismissDialogEvent;
import com.itesm.labs.labsuser.app.commons.events.ItemClickEvent;
import com.itesm.labs.labsuser.app.commons.events.ItemLongClickEvent;
import com.itesm.labs.labsuser.app.commons.events.ShowDialogEvent;
import com.itesm.labs.labsuser.app.commons.views.presenters.LabsActivityPresenter;
import com.mgb.labsapi.models.Laboratory;
import com.squareup.otto.Subscribe;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LabsActivity extends BaseActivity {

    private static final String TAG = LabsActivity.class.getSimpleName();

    @Bind(R.id.labs_grid)
    RecyclerView mRecyclerView;
    @Bind(R.id.labs_grid_swipe_refresh)
    SwipeRefreshLayout mRefreshLayout;

    private LabsRecyclerAdapter mLabsAdapter;

    private LabsActivityPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labs);

        ButterKnife.bind(this);

        mPresenter = new LabsActivityPresenter(this);

        setupUi();
    }

    //region UI setup
    @Override
    public void setupUi() {
        setupRefreshLayout();

        setupLabsGrid();

        mPresenter.getAllowedLabs();
    }

    private void setupRefreshLayout() {
        mRefreshLayout.setColorSchemeResources(
                R.color.material_red,
                R.color.material_pink,
                R.color.material_deep_purple,
                R.color.material_indigo,
                R.color.material_blue,
                R.color.material_light_blue,
                R.color.material_cyan,
                R.color.material_teal,
                R.color.material_green,
                R.color.material_light_green,
                R.color.material_yellow,
                R.color.material_amber,
                R.color.material_deep_orange,
                R.color.material_brown,
                R.color.material_grey
        );
        mRefreshLayout.setOnRefreshListener(() -> mPresenter.getAllowedLabs());
    }

    private void setupLabsGrid() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        mRecyclerView.setHasFixedSize(true);

        mLabsAdapter = new LabsRecyclerAdapter();
        mRecyclerView.setAdapter(mLabsAdapter);
    }

    public void updateInfo(List list) {
        mLabsAdapter.refresh(list);
        mRefreshLayout.setRefreshing(false);
    }

    public void showError() {
        mRefreshLayout.setRefreshing(false);
    }
    //endregion

    //region Event Bus
    @Subscribe
    public void onItemClickEvent(ItemClickEvent<ItemLaboratory> event) {
        if (event.getItem() == null) return;

        ItemLaboratory item = event.getItem();

        String[] params = item.getLaboratory().getLink().split("/");
        item = new ItemLaboratory.Builder()
                .setLaboratory(new Laboratory.Builder()
                        .setName(item.getLaboratory().getName())
                        .setLink(params[params.length - 1])
                        .build())
                .setColorResource(item.getColorResource())
                .setImageResource(item.getImageResource())
                .build();

        mLabsPreferences.putLabLink(params[params.length - 1]);
        mLabsPreferences.putCurrentLab(item.getLaboratory());
        mLabsPreferences.putLabColor(item.getColorResource());

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_in_top);
    }

    @Subscribe
    public void onItemLongClickEvent(ItemLongClickEvent event) {

    }

    @Override
    public void onShowDialogEvent(ShowDialogEvent event) {

    }

    @Override
    public void onDismissDialogEvent(DismissDialogEvent event) {

    }
    //endregion
}
