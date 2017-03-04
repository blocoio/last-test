package io.bloco.lasttest.testing;

import io.bloco.lasttest.data.model.User;

public class UserFactory extends BaseFactory<User> {

  @Override User build() {
    User user = new User();
    user.setName(faker.internet.userName());
    user.setRealName(faker.name.name());
    return user;
  }
}
