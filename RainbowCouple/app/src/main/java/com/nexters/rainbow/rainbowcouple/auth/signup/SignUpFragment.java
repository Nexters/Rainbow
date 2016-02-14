package com.nexters.rainbow.rainbowcouple.auth.signup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nexters.rainbow.rainbowcouple.R;
import com.nexters.rainbow.rainbowcouple.auth.AuthApi;
import com.nexters.rainbow.rainbowcouple.auth.UserDto;
import com.nexters.rainbow.rainbowcouple.common.BaseFragment;
import com.nexters.rainbow.rainbowcouple.common.Messages;
import com.nexters.rainbow.rainbowcouple.common.utils.DebugLog;
import com.nexters.rainbow.rainbowcouple.common.utils.DialogManager;
import com.nexters.rainbow.rainbowcouple.common.network.ExceptionHandler;
import com.nexters.rainbow.rainbowcouple.common.network.NetworkManager;
import com.nexters.rainbow.rainbowcouple.common.utils.ObjectUtils;
import com.nexters.rainbow.rainbowcouple.common.utils.StringUtils;
import com.nexters.rainbow.rainbowcouple.common.widget.AppCompatEditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action1;

public class SignUpFragment extends BaseFragment {

    private static final String TAG_SIGN_UP_FRAGMENT = "sign_up_fragment";

    private View rootView;

    @Bind(R.id.editTextSignUpUserId) AppCompatEditText editTextUserId;
    @Bind(R.id.editTextSignUpUserName) AppCompatEditText editTextUserName;
    @Bind(R.id.editTextSignUpUserPassword) AppCompatEditText editTextUserPassword;

    public static SignUpFragment newInstance() {
        SignUpFragment fragment = new SignUpFragment();
        fragment.setFragmentTag(TAG_SIGN_UP_FRAGMENT);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_sign_up, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.btnSignUpApply)
    public void applySignUp() {
        if (hasEmptyField()) {
            return;
        }

        SignUpForm signUpForm = SignUpForm.builder()
                .userId(editTextUserId.getString())
                .userName(editTextUserName.getString())
                .password(editTextUserPassword.getString());

        processSignUp(signUpForm);
    }

    private boolean hasEmptyField() {
        if (StringUtils.isEmpty(editTextUserId.getString())) {
            DialogManager.showAlertDialog(getActivity(), Messages.SignUpError.EMPTY_USER_ID);
            return true;
        }

        if (StringUtils.isEmpty(editTextUserName.getString())) {
            DialogManager.showAlertDialog(getActivity(), Messages.SignUpError.EMPTY_USER_NAME);
            return true;
        }

        if (StringUtils.isEmpty(editTextUserPassword.getString())) {
            DialogManager.showAlertDialog(getActivity(), Messages.SignUpError.EMPTY_USER_PASSWORD);
            return true;
        }
        return false;
    }

    private void processSignUp(SignUpForm signUpForm) {
        AuthApi authApi = NetworkManager.getApi(AuthApi.class);
        Observable<UserDto> authObservable = authApi.signUp(signUpForm);
        bind(authObservable).subscribe(new Action1<UserDto>() {
            @Override
            public void call(UserDto userDto) {
                DebugLog.d(userDto.toString());
                callInviteMemberFragment(userDto);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                new ExceptionHandler(getActivity()).handle(throwable);
            }
        });
    }

    private void callInviteMemberFragment(UserDto userDto) {
        if (ObjectUtils.isEmpty(userDto.getGroup())) {
            //TODO 가입 재시도 처리
            return;
        }

        String inviteCode = userDto.getGroup().getInviteCode();

        InviteMemberFragment inviteMemberFragment = InviteMemberFragment.newInstance(inviteCode);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.signUpContentPanel, inviteMemberFragment, inviteMemberFragment.getFragmentTag())
                .commitAllowingStateLoss();
    }
}
