package io.bloco.lasttest.ui.main;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import com.squareup.picasso.Picasso;
import io.bloco.lasttest.R;
import io.bloco.lasttest.data.model.Artist;
import io.bloco.lasttest.ui.common.lists.ItemView;
import javax.inject.Inject;

public class ArtistItemView extends ItemView<Artist> {

  @BindView(R.id.artist_item_image) ImageView image;
  @BindView(R.id.artist_item_name) TextView name;
  @BindView(R.id.artist_item_stats) TextView stats;

  @Inject Resources resources;
  @Inject Picasso picasso;

  public ArtistItemView(Context context) {
    super(context);
    getViewComponent().inject(this);
  }

  @Override protected int getLayoutRes() {
    return R.layout.item_artist;
  }

  @Override public void setItem(Artist item) {
    if (!TextUtils.isEmpty(item.imageUrl())) {
      picasso.load(item.imageUrl())
          .fit()
          .centerCrop()
          .into(image);
    }

    name.setText(item.name());
    stats.setText(resources.getString(R.string.artist_stats, item.playCount()));
  }
}
