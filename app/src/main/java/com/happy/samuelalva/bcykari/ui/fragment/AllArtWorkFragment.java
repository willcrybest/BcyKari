package com.happy.samuelalva.bcykari.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.happy.samuelalva.bcykari.model.StatusModel;
import com.happy.samuelalva.bcykari.support.Constants;
import com.happy.samuelalva.bcykari.support.adapter.HomeListAdapter;
import com.happy.samuelalva.bcykari.ui.fragment.base.ChildBaseFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * Created by Samuel.Alva on 2015/4/19.
 */
public class AllArtWorkFragment extends ChildBaseFragment {
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        requestUrl = Constants.ALL_ART_WORK;
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected List<StatusModel> responseDeal(String response) {
        Matcher coverMatcher = Constants.COVER_PATTERN.matcher(response);
        Matcher authorMatcher = Constants.AUTHOR_PATTERN.matcher(response);
        Matcher detailMatcher = Constants.ILLUST_DETAIL_PATTERN.matcher(response);
        Matcher totalPageMatcher = Constants.TOTAL_PAGE_PATTERN.matcher(response);
        if (totalPageMatcher.find()) {
            String s = totalPageMatcher.group();
            totalPage = Math.ceil(Double.parseDouble(s.substring(s.indexOf("共") + 1, s.lastIndexOf("篇"))) / 60);
        }

        List<StatusModel> data = new ArrayList<>();
        while (coverMatcher.find() && authorMatcher.find() && detailMatcher.find()) {
            StatusModel model = new StatusModel();
            model.setCover(coverMatcher.group());
            model.setAuthor(authorMatcher.group().substring(authorMatcher.group().indexOf(">") + 1, authorMatcher.group().lastIndexOf("<")));
            model.setDetail(detailMatcher.group());
            data.add(model);
        }
        return data;
    }

    @Override
    protected HomeListAdapter getAdapter() {
        return new HomeListAdapter(getActivity(), false);
    }
}