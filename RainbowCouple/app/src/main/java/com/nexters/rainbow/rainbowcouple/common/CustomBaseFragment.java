package com.nexters.rainbow.rainbowcouple.common;

import android.support.v4.app.Fragment;

/**
 * fragment tag 관리용으로 만든 BaseFragment
 */
public class CustomBaseFragment extends Fragment {

    private String tag;

    public String getFragmentTag() {
        return tag;
    }

    public void setFragmentTag(String fragmentTag) {
        this.tag = fragmentTag;
    }
}
