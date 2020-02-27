package com.example.poetryrhymedemo.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.poetryrhymedemo.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class  CommunityFragment extends Fragment {
    private View page;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (page == null) {
            page = inflater.inflate(R.layout.tab_community_fragment, container, false);
            //code
        }
        ViewGroup viewGroup = (ViewGroup) page.getParent();
        if (viewGroup != null) {
            viewGroup.removeView(page);
        }
        return page;
    }
}
