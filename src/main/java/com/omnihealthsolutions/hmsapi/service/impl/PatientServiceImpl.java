package com.omnihealthsolutions.hmsapi.service.impl;

import com.omnihealthsolutions.hmsapi.entity.Patient;
import com.omnihealthsolutions.hmsapi.exception.ResourceNotFoundException;
import com.omnihealthsolutions.hmsapi.payload.PatientDTO;
import com.omnihealthsolutions.hmsapi.payload.PatientResponse;
import com.omnihealthsolutions.hmsapi.repository.PatientRepository;
import com.omnihealthsolutions.hmsapi.service.PatientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {

        this.patientRepository = patientRepository;
    }

    @Override
    public PatientDTO createPatient(PatientDTO patientDTO) {

        Patient patient=MapTOEntity(patientDTO);
//        Patient patient = new Patient();
//        patient.setName(patientDTO.getName());
//        patient.setGender(patientDTO.getGender());
//        patient.setDateOfBirth(patientDTO.getDateOfBirth());
//        patient.setDeleted(false);

        Patient savedPatient = patientRepository.save(patient);
        return mapPatientToDTO(savedPatient);
    }

    private Patient MapTOEntity(PatientDTO patientDTO) {
        Patient patient = new Patient();
        patient.setName(patientDTO.getName());
        patient.setGender(patientDTO.getGender());
        patient.setDateOfBirth(patientDTO.getDateOfBirth());
        patient.setDeleted(false);
        return patient;
    }

    @Override
    public PatientDTO getPatientById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient","id",id));

        return mapPatientToDTO(patient);
    }

    @Override
    public PatientResponse getAllPatients(int pageNo,int pageSize,String sortBy,String sortDir) {


        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();


        Pageable pageable = PageRequest.of(pageNo,pageSize, sort);
        Page<Patient> patientPage = patientRepository.findByDeletedFalse(pageable);
        List<Patient> content = patientPage.getContent();

        List<PatientDTO> collect = content.stream().map(contents -> mapPatientToDTO(contents)).collect(Collectors.toList());
        PatientResponse patientResponse = new PatientResponse();
        patientResponse.setPatientDTOList(collect);
        patientResponse.setTotalpages(patientPage.getTotalPages());
        patientResponse.setPageNo(patientPage.getNumber());
        patientResponse.setPageSize(patientPage.getSize());
        patientResponse.setTotalElements(patientPage.getTotalElements());
        patientResponse.setLast(patientPage.isLast());

        return patientResponse;
//
//        List<Patient> patients = patientRepository.findByDeletedFalse(); // Only fetch non-deleted patients
//        return patients.stream()
//                .map(this::mapPatientToDTO)
//                .collect(Collectors.toList());
    }


    @Override
    public void softDeletePatient(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", "id", id));

        patient.setDeleted(true);
        patientRepository.save(patient);
    }

    @Override
    public PatientDTO updatePatientById(long id, PatientDTO patientDTO) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient","id",id));
        patient.setId(patientDTO.getId());
        patient.setName(patientDTO.getName());
        patient.setGender(patientDTO.getGender());
        patient.setDateOfBirth(patientDTO.getDateOfBirth());
        Patient UpdatedPatient = patientRepository.save(patient);
        PatientDTO patientDTO1 = mapPatientToDTO(UpdatedPatient);

        return patientDTO1;
    }

//    @Override
//    public PatientDTO updatePatientById(long id, PatientDTO patientDTO) {
//        Patient patient = patientRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Patient","id",id));
//        patient.setId(patientDTO.getId());
//        patient.setName(patientDTO.getName());
//        patient.setGender(patientDTO.getGender());
//        patient.setDateOfBirth(patientDTO.getDateOfBirth());
//        Patient UpdatedPatient = patientRepository.save(patient);
//        PatientDTO patientDTO1 = mapPatientToDTO(UpdatedPatient);
//
//        return patientDTO1;
//    }

    private PatientDTO mapPatientToDTO(Patient patient) {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setId(patient.getId());
        patientDTO.setName(patient.getName());
        patientDTO.setGender(patient.getGender());
        patientDTO.setDateOfBirth(patient.getDateOfBirth());
        patientDTO.setDeleted(patient.isDeleted());

        return patientDTO;
    }
}
