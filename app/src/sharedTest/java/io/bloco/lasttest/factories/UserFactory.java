package io.bloco.lasttest.factories;

import io.bloco.lasttest.data.model.User;

public class UserFactory extends BaseFactory<User> {

  @Override public User build() {
    User user = new User();
    user.setName(faker.internet.userName());
    user.setRealName(faker.name.name());
    return user;
  }
}
