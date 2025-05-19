package me.jakubpejzl.router;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

@WebMvcTest(RouterApplication.RouterController.class)
public class RouterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LinkRepository linkRepository;

    @Test
    public void testRedirectRoot() throws Exception {
        // Root path by default redirects to Telegram
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("https://t.me/s/mynameisjakub"));
    }

    @Test
    public void testRedirectExistingPath() throws Exception {
        // Setup mock repository to return a link for "github" path
        LinkEntity githubLink = new LinkEntity("github", "https://github.com");
        Mockito.when(linkRepository.findByPath("github")).thenReturn(Optional.of(githubLink));

        // Test that the controller redirects to the correct URL
        mockMvc.perform(MockMvcRequestBuilders.get("/github"))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("https://github.com"));
    }

    @Test
    public void testRedirectNonExistentPath() throws Exception {
        // Setup mock repository to return empty for "nonexistent" path
        Mockito.when(linkRepository.findByPath("nonexistent")).thenReturn(Optional.empty());

        // Test that the controller redirects to the default URL for non-existent paths
        mockMvc.perform(MockMvcRequestBuilders.get("/nonexistent"))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("https://t.me/s/mynameisjakub"));
    }

    @Test
    public void testRedirectWithSpecialCharacters() throws Exception {
        // Setup mock repository to return a link for a path with special characters
        LinkEntity specialLink = new LinkEntity("special-path-123", "https://example.com/special");
        Mockito.when(linkRepository.findByPath("special-path-123")).thenReturn(Optional.of(specialLink));

        // Test that the controller handles paths with special characters
        mockMvc.perform(MockMvcRequestBuilders.get("/special-path-123"))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("https://example.com/special"));
    }
}
