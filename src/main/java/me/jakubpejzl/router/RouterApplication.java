package me.jakubpejzl.router;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

import java.net.URI;
import java.util.Optional;

@SpringBootApplication
@EnableJdbcRepositories
public class RouterApplication {

    public static void main(String[] args) {
        SpringApplication.run(RouterApplication.class, args);
    }

    @RestController
    public static class RouterController {

        private final LinkRepository linkRepository;
        private final String DEFAULT_URL = "https://t.me/s/mynameisjakub";

        @Autowired
        public RouterController(LinkRepository linkRepository) {
            this.linkRepository = linkRepository;
        }

        @GetMapping("/")
        public ResponseEntity<Void> redirectRoot() {
            return redirect(DEFAULT_URL);
        }

        @GetMapping("/{path}")
        public ResponseEntity<Void> redirectPath(@PathVariable String path) {
            Optional<LinkEntity> linkOpt = linkRepository.findByPath(path);
            if (linkOpt.isPresent()) {
                return redirect(linkOpt.get().getUrl());
            } else {
                return redirect(DEFAULT_URL);
            }
        }

        private ResponseEntity<Void> redirect(String url) {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create(url))
                    .build();
        }
    }
}
