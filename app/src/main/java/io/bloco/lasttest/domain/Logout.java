package io.bloco.lasttest.domain;

import io.bloco.lasttest.common.di.PerApplication;
import io.bloco.lasttest.data.ArtistRepository;
import io.bloco.lasttest.data.TrackRepository;
import io.bloco.lasttest.data.UserRepository;
import javax.inject.Inject;

@PerApplication
public class Logout {

  private final UserRepository userRepository;
  private final ArtistRepository artistRepository;
  private final TrackRepository trackRepository;

  @Inject public Logout(UserRepository userRepository, ArtistRepository artistRepository,
      TrackRepository trackRepository) {
    this.userRepository = userRepository;
    this.artistRepository = artistRepository;
    this.trackRepository = trackRepository;
  }

  public void logout() {
    userRepository.delete();
    artistRepository.deleteAll();
    trackRepository.deleteAll();
  }
}
