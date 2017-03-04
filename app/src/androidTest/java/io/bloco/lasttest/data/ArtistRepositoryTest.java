package io.bloco.lasttest.data;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import io.bloco.lasttest.AndroidApplication;
import io.bloco.lasttest.common.di.ApplicationComponent;
import io.bloco.lasttest.data.model.Artist;
import java.util.Collections;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class ArtistRepositoryTest {

  private ArtistRepository artistRepository;

  @Before
  public void setUp() throws Exception {
    ApplicationComponent applicationComponent =
        ((AndroidApplication) InstrumentationRegistry.getTargetContext()
            .getApplicationContext()).getApplicationComponent();

    artistRepository = new ArtistRepository(applicationComponent.db());
  }

  @After
  public void tearDown() throws Exception {
    artistRepository.deleteAll();
  }

  @Test
  public void saveAndGet() throws Exception {
    Artist artist = new Artist();
    artist.setId(123);
    artistRepository.save(Collections.singletonList(artist));

    List<Artist> retrievedArtists = artistRepository.getAll();

    assertThat(retrievedArtists, hasSize(1));
    assertThat(retrievedArtists.get(0), equalTo(artist));
  }
}