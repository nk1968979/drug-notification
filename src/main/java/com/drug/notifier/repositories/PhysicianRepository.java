package com.drug.notifier.repositories;

import com.drug.notifier.model.Physician;
import org.springframework.data.repository.CrudRepository;

public interface PhysicianRepository extends CrudRepository<Physician,Long> {
    Physician findByEmail(String username);
}
