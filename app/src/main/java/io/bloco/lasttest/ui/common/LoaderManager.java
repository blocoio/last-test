package io.bloco.lasttest.ui.common;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.support.annotation.StringRes;
import io.bloco.lasttest.common.di.PerActivity;
import javax.inject.Inject;

@PerActivity public class LoaderManager {

  private final Activity activity;
  private final Resources resources;
  private ProgressDialog progressDialog;

  @Inject public LoaderManager(Activity activity, Resources resources) {
    this.activity = activity;
    this.resources = resources;
  }

  public void show(@StringRes int messageRes) {
    if (progressDialog != null) {
      dismiss();
    }

    progressDialog = new ProgressDialog(activity);
    progressDialog.setMessage(resources.getString(messageRes));
    progressDialog.setIndeterminate(true);
    progressDialog.setCanceledOnTouchOutside(false);
    progressDialog.show();
  }

  public void dismiss() {
    if (progressDialog != null && progressDialog.isShowing()) {
      progressDialog.dismiss();
    }
    progressDialog = null;
  }
}

