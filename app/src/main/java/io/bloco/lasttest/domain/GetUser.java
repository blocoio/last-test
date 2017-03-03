package io.bloco.lasttest.domain;

import io.bloco.lasttest.data.UserRepository;
import io.bloco.lasttest.data.model.User;
import javax.inject.Inject;

public class GetUser {

  private final UserRepository userRepository;

  @Inject public GetUser(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User get() {
    return userRepository.get();
  }
}
