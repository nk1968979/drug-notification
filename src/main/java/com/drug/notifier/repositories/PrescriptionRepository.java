package com.drug.notifier.repositories;

import com.drug.notifier.model.Prescription;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PrescriptionRepository extends CrudRepository<Prescription,Long> {
    List<Prescription> findByPatientId(int id);
}
