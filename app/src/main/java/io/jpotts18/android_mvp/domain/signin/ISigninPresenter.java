package io.jpotts18.android_mvp.domain.signin;

/**
 * Created by Josué Chávez on 02/12/2016.
 */
public interface ISigninPresenter
{
    void attemptSignin(String username, String password, String info, String externlIp, String deviceId);
}
