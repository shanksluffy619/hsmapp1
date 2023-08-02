package com.omnihealthsolutions.hmsapi.repository;

import com.omnihealthsolutions.hmsapi.entity.Appointment;
import com.omnihealthsolutions.hmsapi.entity.Patient;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    // Custom query methods, if needed

//    List<Patient> findByIdAndPatient(Long appointmentId);
List<Appointment> findByPatient(Patient patient);
//    Optional<List<Appointment>> findByPatient(Patient patient);
}