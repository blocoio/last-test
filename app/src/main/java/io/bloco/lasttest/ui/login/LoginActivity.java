package io.bloco.lasttest.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.OnClick;
import io.bloco.lasttest.R;
import io.bloco.lasttest.data.UserRepository;
import io.bloco.lasttest.data.api.LastFmApi;
import io.bloco.lasttest.data.api.response.UserResponse;
import io.bloco.lasttest.ui.BaseActivity;
import io.bloco.lasttest.ui.common.ErrorDisplayer;
import io.bloco.lasttest.ui.common.LoaderManager;
import io.bloco.lasttest.ui.main.MainActivity;
import javax.inject.Inject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class LoginActivity extends BaseActivity {

  @BindView(R.id.login_user) EditText user;
  @BindView(R.id.login_submit) Button submit;

  @Inject ErrorDisplayer errorDisplayer;
  @Inject LoaderManager loaderManager;
  @Inject LastFmApi lastFmApi;
  @Inject UserRepository userRepository;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getActivityComponent().inject(this);
  }

  @Override protected int getLayoutRes() {
    return R.layout.activity_login;
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    loaderManager.dismiss();
  }

  @OnClick(R.id.login_submit) void onSubmitClicked() {
    String userName = user.getText().toString();

    if (TextUtils.isEmpty(userName)) {
      errorDisplayer.show(R.string.login_user_missing);
      return;
    }
    if (userName.length() < 4) {
      errorDisplayer.show(R.string.login_user_too_short);
      return;
    }
    if (userName.length() > 60) {
      errorDisplayer.show(R.string.login_user_too_large);
      return;
    }

    submit.setEnabled(false);
    loaderManager.show(R.string.login_submitting);

    login(userName);
  }

  private void login(String userName) {
    lastFmApi.getUserInfo(userName).enqueue(new Callback<UserResponse>() {
      @Override
      public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
        if (!response.isSuccessful()) {
          loginError();
          return;
        }

        userRepository.set(response.body().getUser());
        loaderManager.dismiss();
        startActivity(MainActivity.Factory.getIntent(LoginActivity.this));
        finish();
      }

      @Override
      public void onFailure(Call<UserResponse> call, Throwable t) {
        Timber.e(t);
        loginError();
      }
    });
  }

  private void loginError() {
    loaderManager.dismiss();
    submit.setEnabled(true);
    errorDisplayer.show(R.string.login_submit_error);
  }

  public static class Factory {
    public static Intent getIntent(Context context) {
      return new Intent(context, LoginActivity.class);
    }
  }
}
