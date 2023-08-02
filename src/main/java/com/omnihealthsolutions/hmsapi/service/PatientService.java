package com.omnihealthsolutions.hmsapi.service;



import com.omnihealthsolutions.hmsapi.payload.PatientDTO;
import com.omnihealthsolutions.hmsapi.payload.PatientResponse;

import java.util.List;

public interface PatientService {
    PatientDTO createPatient(PatientDTO patientDTO);

    PatientDTO getPatientById(Long id);

    PatientResponse getAllPatients(int pageNo, int pageSize, String sortBy, String sortDir);

    void softDeletePatient(Long id);


    PatientDTO updatePatientById(long id, PatientDTO patientDTO);
}
