package io.bloco.lasttest.domain;

import io.bloco.lasttest.common.di.PerApplication;
import io.bloco.lasttest.data.ArtistRepository;
import io.bloco.lasttest.data.model.Artist;
import javax.inject.Inject;

@PerApplication
public class GetArtist {

  private final ArtistRepository artistRepository;

  @Inject public GetArtist(ArtistRepository artistRepository) {
    this.artistRepository = artistRepository;
  }

  public Artist get(long artistId) {
    return artistRepository.get(artistId);
  }
}
