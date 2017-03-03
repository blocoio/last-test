package io.bloco.lasttest.ui.artist;

import android.content.Context;
import android.widget.TextView;
import butterknife.BindView;
import com.squareup.picasso.Picasso;
import io.bloco.lasttest.R;
import io.bloco.lasttest.data.model.Track;
import io.bloco.lasttest.ui.common.lists.ItemView;
import javax.inject.Inject;

public class TrackItemView extends ItemView<Track> {

  @BindView(R.id.track_item_name) TextView name;
  @BindView(R.id.track_item_listeners) TextView listeners;

  public TrackItemView(Context context) {
    super(context);
    getViewComponent().inject(this);
  }

  @Override protected int getLayoutRes() {
    return R.layout.item_track;
  }

  @Override public void setItem(Track item) {
    name.setText(item.name());
    listeners.setText(String.valueOf(item.listeners()));
  }
}
