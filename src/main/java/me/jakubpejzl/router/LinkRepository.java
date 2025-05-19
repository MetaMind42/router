package me.jakubpejzl.router;

import org.springframework.data.repository.Repository;
import java.util.Optional;

@org.springframework.stereotype.Repository
public interface LinkRepository extends Repository<LinkEntity, Integer> {
    Optional<LinkEntity> findByPath(String path);
}
