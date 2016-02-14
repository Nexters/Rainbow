package com.nexters.rainbow.rainbowcouple.auth.signup;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.nexters.rainbow.rainbowcouple.MainActivity;
import com.nexters.rainbow.rainbowcouple.R;
import com.nexters.rainbow.rainbowcouple.common.BaseFragment;
import com.nexters.rainbow.rainbowcouple.common.utils.ObjectUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InviteMemberFragment extends BaseFragment {

    private static final String INVITE_MEMBER_FRAGMENT = "invite_member_fragment";
    private static final String ARG_INVITE_CODE = "invite_code";

    private View rootView;
    private String inviteCode;

    @Bind(R.id.btnCopyInviteCode)
    Button btnCopyInviteCode;

    public static InviteMemberFragment newInstance(String inviteCode) {
        Bundle arguments = new Bundle();
        arguments.putString(ARG_INVITE_CODE, inviteCode);

        InviteMemberFragment fragment = new InviteMemberFragment();
        fragment.setFragmentTag(INVITE_MEMBER_FRAGMENT);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        inviteCode = getInviteCodeArgument();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_invite_member, container, false);
        ButterKnife.bind(this, rootView);

        btnCopyInviteCode.setText(inviteCode);
        return rootView;
    }

    @OnClick(R.id.btnCopyInviteCode)
    public void copyInviteCode() {
        //TODO 클립보드에 invitecode 복사 후 toast
    }

    @OnClick(R.id.btnStartService)
    public void startMainService() {
        Intent mainActivity = new Intent(getActivity(), MainActivity.class);
        startActivity(mainActivity);
    }

    private String getInviteCodeArgument() {
        return ObjectUtils.isEmpty(inviteCode) ? "" : inviteCode;
    }
}
