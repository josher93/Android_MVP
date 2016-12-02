package io.jpotts18.android_mvp.domain.signin.async;

import android.content.Context;

/**
 * Created by Josué Chávez on 02/12/2016.
 */
public interface IAsyncSigninInteractor
{
    void validateCredentialsAsync(OnSigninFinishedListener listener, Context context, String username, String password, String info, String externlIp, String deviceId);
}
