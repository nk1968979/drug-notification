package com.drug.notifier.repositories;

import com.drug.notifier.model.City;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface CityRepository extends CrudRepository<City,Long> {
    City findByName(String name);
}
