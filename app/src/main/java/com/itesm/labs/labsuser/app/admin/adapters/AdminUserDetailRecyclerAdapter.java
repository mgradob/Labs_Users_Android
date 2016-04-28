package com.itesm.labs.labsuser.app.admin.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.admin.adapters.models.ItemHistory;
import com.itesm.labs.labsuser.app.bases.BaseRecyclerAdapter;
import com.itesm.labs.labsuser.app.bases.BaseViewHolder;
import com.itesm.labs.labsuser.app.commons.utils.SwipeDetector;

import butterknife.Bind;

/**
 * Created by mgradob on 2/20/16.
 */
public class AdminUserDetailRecyclerAdapter extends BaseRecyclerAdapter<ItemHistory, AdminUserDetailRecyclerAdapter.ViewHolder> {

    public AdminUserDetailRecyclerAdapter(Activity activity) {
        super(activity);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.list_item_record, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.bindData(DATA.get(position));
    }

    public class ViewHolder extends BaseViewHolder<ItemHistory> implements SwipeDetector.SwipeListener {

        @Bind(R.id.record_item_category_name)
        TextView mCategoryName;
        @Bind(R.id.record_item_component_name_note_qty)
        TextView mComponentNameNoteQty;
        @Bind(R.id.record_item_date_out_in)
        TextView mDateOutIn;

        SwipeDetector mSwipeDetector;

        /**
         * Constructor that binds to Butterknife automatically.
         *
         * @param itemView
         */
        public ViewHolder(View itemView) {
            super(itemView);

            mSwipeDetector = new SwipeDetector();
            mSwipeDetector.setSwipeListener(this);
            itemView.setOnTouchListener(mSwipeDetector);
        }

        @Override
        public void bindData(ItemHistory holderItem) {
            mCategoryName.setText(holderItem.getCategoryName());
            mComponentNameNoteQty.setText(
                    String.format(mContext.getString(R.string.user_list_item_history_name_note_qty),
                            holderItem.getComponentName(), holderItem.getComponentNote(), holderItem.getHistory().getQuantity())
            );
            mDateOutIn.setText(
                    String.format(mContext.getString(R.string.user_list_item_history_date_out_in),
                            holderItem.getHistory().getDateOut(), holderItem.getHistory().getDateIn())
            );
        }

        @Override
        public void onClick(View v) {

        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }

        @Override
        public void swipedRight() {
            // TODO: 4/25/16 update date in.
        }

        @Override
        public void swipedLeft() {

        }
    }
}
