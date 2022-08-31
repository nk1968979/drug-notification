package com.drug.notifier.repositories;

import com.drug.notifier.model.Patient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface PatientRepository extends CrudRepository<Patient,Long> {
    Patient findByEmail(String username);
}
