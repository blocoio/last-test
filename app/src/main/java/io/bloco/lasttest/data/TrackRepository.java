package io.bloco.lasttest.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.squareup.sqldelight.SqlDelightStatement;
import io.bloco.lasttest.common.di.PerApplication;
import io.bloco.lasttest.data.model.Track;
import io.bloco.lasttest.data.model.TrackModel;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import timber.log.Timber;

@PerApplication
public class TrackRepository {

  private final SQLiteDatabase db;

  @Inject public TrackRepository(SQLiteDatabase db) {
    this.db = db;
  }

  public void save(long artistId, List<Track> tracks) {
    db.beginTransaction();
    try {
      deleteByArtist(artistId);
      TrackModel.InsertQuery insertQuery = new Track.InsertQuery(db);
      for (Track track : tracks) {
        insertQuery.bind(
            track.mbid(),
            track.name(),
            track.url(),
            track.imageUrl(),
            track.playCount(),
            track.listeners(),
            artistId
        );
        insertQuery.program.executeInsert();
      }
      db.setTransactionSuccessful();
    } catch (Exception exception) {
      Timber.e(exception);
    } finally {
      db.endTransaction();
    }
  }

  public List<Track> getByArtist(long artistId) {
    List<Track> tracks = new ArrayList<>();
    SqlDelightStatement query = Track.FACTORY.select_by_artist_id(artistId);
    try (Cursor cursor = db.rawQuery(query.statement, query.args)) {
      while (cursor.moveToNext()) {
        tracks.add(Track.FACTORY.select_by_artist_idMapper().map(cursor));
      }
    }
    return tracks;
  }

  private void deleteByArtist(long artistId) {
    TrackModel.DeleteByArtistId deleteQuery = new Track.DeleteByArtistId(db);
    deleteQuery.bind(artistId);
    deleteQuery.program.executeUpdateDelete();
  }

  public void deleteAll() {
    db.execSQL(Track.DELETEALL);
  }
}
