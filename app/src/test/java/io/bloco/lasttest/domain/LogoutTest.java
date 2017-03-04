package io.bloco.lasttest.domain;

import android.content.SharedPreferences;
import com.google.gson.Gson;
import io.bloco.lasttest.data.ArtistRepository;
import io.bloco.lasttest.data.TrackRepository;
import io.bloco.lasttest.data.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class LogoutTest {

  @Mock UserRepository userRepository;
  @Mock ArtistRepository artistRepository;
  @Mock TrackRepository trackRepository;
  @InjectMocks Logout logout;

  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void logout() throws Exception {
    logout.logout();

    verify(userRepository).delete();
    verify(artistRepository).deleteAll();
    verify(trackRepository).deleteAll();
  }
}