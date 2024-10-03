package com.darcan.auth.domain;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long>{
    Optional<UserEntity> findByName(String name);
}
