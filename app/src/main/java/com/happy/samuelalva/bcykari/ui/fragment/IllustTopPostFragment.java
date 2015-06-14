package com.happy.samuelalva.bcykari.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.happy.samuelalva.bcykari.support.Constants;
import com.happy.samuelalva.bcykari.support.adapter.HomeListAdapter;
import com.happy.samuelalva.bcykari.ui.fragment.base.BcyChildFragment;

/**
 * Created by Samuel.Alva on 2015/4/17.
 */
public class IllustTopPostFragment extends BcyChildFragment {
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        requestUrl = Constants.ILLUST_TOP_POST_100;
        hasAvatar = true;
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected HomeListAdapter getAdapter() {
        return new HomeListAdapter(getActivity(), requestHostType, true);
    }

    @Override
    protected void doLoad() {
        Toast.makeText(getActivity(), "没有更多了", Toast.LENGTH_SHORT).show();
    }
}
