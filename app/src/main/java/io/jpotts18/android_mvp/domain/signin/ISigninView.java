package io.jpotts18.android_mvp.domain.signin;

import io.jpotts18.android_mvp.domain.models.ErrorResponseViewModel;

/**
 * Created by Josué Chávez on 02/12/2016.
 */
public interface ISigninView
{
    //Success
    void navigateToHome();

    //Success
    void navigateToPin();

    //Fail
    void loginFailed(ErrorResponseViewModel errorResponse);



}
