package com.darcan.auth.domain.repositories;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

import com.darcan.auth.domain.models.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Long>{
    Optional<UserEntity> findByName(String name);
}
