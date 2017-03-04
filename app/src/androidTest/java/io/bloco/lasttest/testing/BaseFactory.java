package io.bloco.lasttest.testing;

import io.bloco.faker.Faker;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseFactory<T> {

  protected Faker faker;

  public BaseFactory() {
    this.faker = new Faker();
  }

  abstract T build();

  public List<T> buildList(int number) {
    List<T> list = new ArrayList<>(number);
    for (int i = 0; i < number; i++) {
      list.add(build());
    }
    return list;
  }
}
