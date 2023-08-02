package com.omnihealthsolutions.hmsapi.repository;


import com.omnihealthsolutions.hmsapi.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;



public interface PatientRepository extends JpaRepository<Patient, Long> {
    Page<Patient> findByDeletedFalse(Pageable pageable);
}
