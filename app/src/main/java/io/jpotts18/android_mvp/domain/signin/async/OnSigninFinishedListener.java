package io.jpotts18.android_mvp.domain.signin.async;

import io.jpotts18.android_mvp.domain.models.ErrorResponseViewModel;

/**
 * Created by Josué Chávez on 02/12/2016.
 */
public interface OnSigninFinishedListener
{
    void onError(ErrorResponseViewModel errorResponse);
    void onSuccess();
}
