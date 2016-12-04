package io.jpotts18.android_mvp.domain.signin;

import android.content.Context;

import io.jpotts18.android_mvp.domain.models.ErrorResponseViewModel;
import io.jpotts18.android_mvp.domain.signin.async.AsyncSigninInteractor;
import io.jpotts18.android_mvp.domain.signin.async.OnSigninFinishedListener;

/**
 * Created by Josué Chávez on 02/12/2016.
 */
public class SigninPresenter implements ISigninPresenter, OnSigninFinishedListener
{
    // Referencing any class that implements the ILoginView interface provides greater flexibility
    private ISigninView view;
    private AsyncSigninInteractor interactor;
    private Context context;

    public SigninPresenter(ISigninView pView, Context context)
    {
        this.view = pView;
        this.interactor = new AsyncSigninInteractor();
        this.context = context;
    }

    @Override
    public void attemptSignin(String username, String password, String info, String externlIp, String deviceId)
    {
        interactor.validateCredentialsAsync(this, context, username, password, info, externlIp, deviceId);
    }

    /*@Override
    public void onError()
    {

    }*/

    @Override
    public void onError(ErrorResponseViewModel errorResponse)
    {
        view.loginFailed(errorResponse);
    }

    @Override
    public void onSuccess()
    {
        view.navigateToHome();
    }
}
