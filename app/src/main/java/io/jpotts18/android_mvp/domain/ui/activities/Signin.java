package io.jpotts18.android_mvp.domain.ui.activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.jpotts18.android_mvp.R;
import io.jpotts18.android_mvp.domain.signin.ISigninView;
import io.jpotts18.android_mvp.domain.signin.SigninPresenter;

public class Signin extends AppCompatActivity implements ISigninView
{
    ProgressDialog progressDialog;
    SigninPresenter signinPresenter;

    @Bind(R.id.etRegMail)
    EditText etRegMail;

    @Bind(R.id.etRegPass)
    EditText etRegPass;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        ButterKnife.bind(this);

        signinPresenter = new SigninPresenter(Signin.this, this);
    }

    @OnClick(R.id.btnLogin)
    public void loginTapped(View view)
    {
        progressDialog = ProgressDialog.show(this, "Iniciando sesión...", null);
        String email =  etRegMail.getText().toString();
        String password = etRegPass.getText().toString();

        signinPresenter.attemptSignin(email, password, "", "", "");

    }

    @Override
    public void navigateToHome()
    {

    }

    @Override
    public void navigateToPin()
    {

    }

    @Override
    public void loginFailed()
    {

    }
}
