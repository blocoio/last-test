package io.bloco.lasttest.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import com.squareup.picasso.Picasso;
import io.bloco.lasttest.R;
import io.bloco.lasttest.data.model.Artist;
import io.bloco.lasttest.data.model.User;
import io.bloco.lasttest.ui.BaseActivity;
import io.bloco.lasttest.ui.artist.ArtistActivity;
import io.bloco.lasttest.ui.common.CircleTransform;
import io.bloco.lasttest.ui.common.ErrorDisplayer;
import io.bloco.lasttest.ui.common.lists.ItemClickListener;
import io.bloco.lasttest.ui.common.lists.RecyclerAdapter;
import io.bloco.lasttest.ui.login.LoginActivity;
import java.util.List;
import javax.inject.Inject;

public class MainActivity extends BaseActivity implements MainPresenter.View,
    ItemClickListener<Artist> {

  @BindView(R.id.main_user_image) ImageView userImage;
  @BindView(R.id.main_user_name) TextView userName;
  @BindView(R.id.main_user_stats) TextView userStats;
  @BindView(R.id.main_artists_refresh) SwipeRefreshLayout artistsRefresh;
  @BindView(R.id.main_artists) RecyclerView artistsList;
  @BindView(R.id.main_artists_empty) View artistsEmpty;

  @Inject MainPresenter presenter;
  @Inject ErrorDisplayer errorDisplayer;
  @Inject Picasso picasso;

  private RecyclerAdapter<Artist, ArtistItemView> artistsAdapter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getActivityComponent().inject(this);

    artistsAdapter = new RecyclerAdapter<>(ArtistItemView.class, this);
    artistsList.setAdapter(artistsAdapter);
    artistsList.setLayoutManager(new GridLayoutManager(this, 2));

    artistsRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override public void onRefresh() {
        presenter.artistsRefresh();
      }
    });

    presenter.start(this);
  }

  @Override protected int getLayoutRes() {
    return R.layout.activity_main;
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    presenter.stop();
  }

  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.main_logout:
        presenter.logoutClick();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override public void onItemClick(Artist artist) {
    presenter.artistClicked(artist);
  }

  @Override public void showUser(User user) {
    userName.setText(user.getBestName());
    userStats.setText(getString(R.string.main_user_stats, user.getPlayCount()));
    if (!TextUtils.isEmpty(user.getImageUrl())) {
      picasso.load(user.getImageUrl())
          .transform(new CircleTransform())
          .fit()
          .centerCrop()
          .into(userImage);
    }
  }

  @Override public void showArtists(List<Artist> artists) {
    artistsEmpty.setVisibility(View.GONE);
    artistsList.setVisibility(View.VISIBLE);
    artistsRefresh.setRefreshing(false);
    artistsAdapter.updateItems(artists);
  }

  @Override public void showArtistsEmpty() {
    artistsEmpty.setVisibility(View.VISIBLE);
    artistsList.setVisibility(View.GONE);
    artistsRefresh.setRefreshing(false);
  }

  @Override public void showArtistsError() {
    errorDisplayer.show(R.string.main_artists_error);
  }

  @Override public void openArtist(Artist artist) {
    startActivity(ArtistActivity.Factory.getIntent(this, artist));
  }

  @Override public void openLogin() {
    artistsRefresh.setRefreshing(false);
    startActivity(LoginActivity.Factory.getIntent(this));
  }

  public static class Factory {
    public static Intent getIntent(Context context) {
      return new Intent(context, MainActivity.class);
    }
  }
}
