package com.nexters.rainbow.rainbowcouple.auth.signup;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.nexters.rainbow.rainbowcouple.MainActivity;
import com.nexters.rainbow.rainbowcouple.R;
import com.nexters.rainbow.rainbowcouple.auth.AuthApi;
import com.nexters.rainbow.rainbowcouple.auth.UserDto;
import com.nexters.rainbow.rainbowcouple.common.BaseFragment;
import com.nexters.rainbow.rainbowcouple.common.network.ExceptionHandler;
import com.nexters.rainbow.rainbowcouple.common.network.NetworkManager;
import com.nexters.rainbow.rainbowcouple.common.network.SessionManager;
import com.nexters.rainbow.rainbowcouple.common.utils.DialogManager;
import com.nexters.rainbow.rainbowcouple.common.utils.ObjectUtils;
import com.nexters.rainbow.rainbowcouple.common.utils.StringUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action1;

public class InviteMemberFragment extends BaseFragment {

    private static final String INVITE_MEMBER_FRAGMENT = "invite_member_fragment";
    private static final String ARG_INVITE_CODE = "invite_code";

    private View rootView;

    private SessionManager sessionManager;

    private String inviteCode;
    private boolean invitedMember;

    @Bind(R.id.btnCopyInviteCode) Button btnCopyInviteCode;
    @Bind(R.id.editTextGroupCode) EditText editTextGroupCode;

    public static InviteMemberFragment newInstance(String inviteCode) {
        Bundle arguments = new Bundle();
        arguments.putString(ARG_INVITE_CODE, inviteCode);

        InviteMemberFragment fragment = new InviteMemberFragment();
        fragment.setFragmentTag(INVITE_MEMBER_FRAGMENT);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_invite_member, container, false);
        ButterKnife.bind(this, rootView);

        sessionManager = SessionManager.getInstance(getActivity());

        inviteCode = getInviteCodeArgument();

        invitedMember = false;
        editTextGroupCode.setVisibility(View.GONE);
        editTextGroupCode.setText("");
        btnCopyInviteCode.setText(inviteCode);

        return rootView;
    }

    @OnClick(R.id.btnCopyInviteCode)
    public void copyInviteCode() {
        //TODO 클립보드에 invitecode 복사 후 toast
    }

    @OnClick(R.id.btnStartService)
    public void startMainService() {
        final Intent mainActivity = new Intent(getActivity(), MainActivity.class);
        mainActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mainActivity.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        if (!isInvitedMember()) {
            startActivity(mainActivity);
        }

        String inviteCode = editTextGroupCode.getText().toString();
        if (!isValidInviteCode(inviteCode)) {
            return;
        }

        AuthApi authApi = NetworkManager.getApi(AuthApi.class);
        Observable<UserDto> authObservable = authApi.joinGroup(
                sessionManager.getUserToken(), inviteCode);
        bind(authObservable).subscribe(new Action1<UserDto>() {
            @Override
            public void call(UserDto userDto) {
                sessionManager.createLoginSession(userDto);
                startActivity(mainActivity);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                new ExceptionHandler(getActivity()).handle(throwable);
            }
        });
    }

    private boolean isValidInviteCode(String inviteCode) {
        if (StringUtils.isEmpty(inviteCode)) {
            DialogManager.showAlertDialog(getActivity(), "그룹 코드를 입력 해 주세요.");
            return false;
        }
        return true;
    }

    @OnClick(R.id.textViewSignInGroup)
    public void viewSignInGroup() {
        invitedMember = true;
        btnCopyInviteCode.setVisibility(View.GONE);
        editTextGroupCode.setVisibility(View.VISIBLE);
        editTextGroupCode.setText("");
    }

    @OnClick(R.id.textViewMakeGroup)
    public void viewMakeGroup() {
        invitedMember = false;
        btnCopyInviteCode.setVisibility(View.VISIBLE);
        editTextGroupCode.setVisibility(View.GONE);
        editTextGroupCode.setText("");
    }

    private String getInviteCodeArgument() {
        String inviteCode = getArguments().getString(ARG_INVITE_CODE);
        return ObjectUtils.isEmpty(inviteCode) ? "" : inviteCode;
    }

    public boolean isInvitedMember() {
        return invitedMember;
    }
}
