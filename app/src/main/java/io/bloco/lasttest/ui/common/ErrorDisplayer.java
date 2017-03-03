package io.bloco.lasttest.ui.common;

import android.app.Activity;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;
import io.bloco.lasttest.R;
import io.bloco.lasttest.common.di.PerActivity;
import javax.inject.Inject;

@PerActivity public class ErrorDisplayer {

  private final Activity activity;

  @Inject public ErrorDisplayer(Activity activity) {
    this.activity = activity;
  }

  public void show(@StringRes int messageRes) {
    show(activity.getString(messageRes));
  }

  public void show(String message) {
    View rootView = activity.findViewById(android.R.id.content);
    Snackbar snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_LONG);
    setTextColor(snackbar);
    snackbar.show();
  }

  private void setTextColor(Snackbar snackbar) {
    View view = snackbar.getView();
    TextView textView = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
    textView.setTextColor(ContextCompat.getColor(activity, R.color.text_alt));
  }
}
