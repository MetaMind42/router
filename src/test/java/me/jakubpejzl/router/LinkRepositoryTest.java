package me.jakubpejzl.router;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles("test")
public class LinkRepositoryTest {

    @Autowired
    private LinkRepository linkRepository;

    @Test
    @Sql("/test-data.sql")
    void findByPath_shouldReturnCorrectLink_whenPathExists() {
        // given
        String existingPath = "github";

        // when
        Optional<LinkEntity> result = linkRepository.findByPath(existingPath);

        // then
        assertThat(result).isPresent();
        assertThat(result.get().getPath()).isEqualTo(existingPath);
        assertThat(result.get().getUrl()).isEqualTo("https://github.com");
    }

    @Test
    @Sql("/test-data.sql")
    void findByPath_shouldReturnEmpty_whenPathDoesNotExist() {
        // given
        String nonExistentPath = "nonexistent";

        // when
        Optional<LinkEntity> result = linkRepository.findByPath(nonExistentPath);

        // then
        assertThat(result).isEmpty();
    }

    @Test
    @Sql("/test-data.sql")
    void findByPath_shouldBeCaseSensitive() {
        // given
        String existingPath = "github";
        String wrongCasePath = "GitHub";

        // when
        Optional<LinkEntity> result = linkRepository.findByPath(wrongCasePath);

        // then
        assertThat(result).isEmpty();
    }
}
