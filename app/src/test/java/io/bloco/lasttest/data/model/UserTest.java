package io.bloco.lasttest.data.model;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class UserTest {
  @Test
  public void getBestName_withoutRealName() throws Exception {
    User user = new User();
    user.setName("hello");
    assertThat(user.getBestName(), is(equalTo("hello")));
  }

  @Test
  public void getBestName_withRealName() throws Exception {
    User user = new User();
    user.setName("hello");
    user.setRealName("RealName");
    assertThat(user.getBestName(), is(equalTo("RealName")));
  }
}