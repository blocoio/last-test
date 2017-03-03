package io.bloco.lasttest.ui.artist;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.BindView;
import butterknife.OnClick;
import io.bloco.lasttest.R;
import io.bloco.lasttest.common.Preconditions;
import io.bloco.lasttest.data.model.Artist;
import io.bloco.lasttest.data.model.Track;
import io.bloco.lasttest.ui.BaseActivity;
import io.bloco.lasttest.ui.common.ErrorDisplayer;
import io.bloco.lasttest.ui.common.lists.DividerItemDecoration;
import io.bloco.lasttest.ui.common.lists.RecyclerAdapter;
import java.util.List;
import javax.inject.Inject;

public class ArtistActivity extends BaseActivity implements ArtistPresenter.View {

  @BindView(R.id.artist_tracks) RecyclerView tracksList;
  @BindView(R.id.artist_empty) View tracksEmpty;

  @Inject ArtistPresenter presenter;
  @Inject ErrorDisplayer errorDisplayer;

  private RecyclerAdapter<Track, TrackItemView> tracksAdapter;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getActivityComponent().inject(this);
    enableToolbarBack();

    tracksAdapter = new RecyclerAdapter<>(TrackItemView.class);
    tracksList.setAdapter(tracksAdapter);
    tracksList.setLayoutManager(new LinearLayoutManager(this));
    tracksList.addItemDecoration(new DividerItemDecoration(this));

    long artistId = getIntent().getLongExtra(Factory.ARTIST_ID, -1);
    Preconditions.checkArgument(artistId != -1, "Artist id not provided");

    presenter.start(this, artistId);
  }

  @Override protected int getLayoutRes() {
    return R.layout.activity_artist;
  }

  @OnClick(R.id.artist_open) void onOpenClicked() {
    presenter.onOpenClicked();
  }

  @Override public void showArtist(Artist artist) {
    setTitle(artist.name());
  }

  @Override public void showTracks(List<Track> tracks) {
    tracksList.setVisibility(View.VISIBLE);
    tracksEmpty.setVisibility(View.GONE);
    tracksAdapter.updateItems(tracks);
  }

  @Override public void showTracksEmpty() {
    tracksList.setVisibility(View.GONE);
    tracksEmpty.setVisibility(View.VISIBLE);
  }

  @Override public void showTracksError() {
    errorDisplayer.show(R.string.artist_tracks_error);
  }

  @Override public void openArtistUrl(String artistUrl) {
    Intent intent = new Intent(Intent.ACTION_VIEW);
    intent.setData(Uri.parse(artistUrl));
    startActivity(intent);
  }

  public static class Factory {

    private static final String ARTIST_ID = "artistId";

    public static Intent getIntent(Context context, Artist artist) {
      Intent intent = new Intent(context, ArtistActivity.class);
      intent.putExtra(ARTIST_ID, artist.id());
      return intent;
    }
  }
}
