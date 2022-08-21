package com.drug.notifier.repositories;

import com.drug.notifier.model.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<UserEntity,Long> {
    UserEntity findByEmail(String username);
}
