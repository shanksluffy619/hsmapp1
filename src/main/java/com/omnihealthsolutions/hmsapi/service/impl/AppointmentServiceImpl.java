    package com.omnihealthsolutions.hmsapi.service.impl;

    import com.omnihealthsolutions.hmsapi.entity.Appointment;
    import com.omnihealthsolutions.hmsapi.entity.Patient;

    import com.omnihealthsolutions.hmsapi.exception.ResourceNotFoundException;
    import com.omnihealthsolutions.hmsapi.payload.AppointmentDTO;
    import com.omnihealthsolutions.hmsapi.repository.AppointmentRepository;
    import com.omnihealthsolutions.hmsapi.repository.PatientRepository;
    import com.omnihealthsolutions.hmsapi.service.AppointmentService;
    import org.modelmapper.ModelMapper;

    import org.springframework.stereotype.Service;

    import java.util.List;
    import java.util.stream.Collectors;

    @Service
    public class AppointmentServiceImpl implements AppointmentService {
        private final AppointmentRepository appointmentRepository;
        private final PatientRepository patientRepository;
        private final ModelMapper modelMapper;

        public AppointmentServiceImpl(AppointmentRepository appointmentRepository, PatientRepository patientRepository, ModelMapper modelMapper) {
            this.appointmentRepository = appointmentRepository;
            this.patientRepository = patientRepository;
            this.modelMapper = modelMapper;
        }

        @Override
        public AppointmentDTO createAppointment(Long patientId, AppointmentDTO appointmentDTO) {


            Patient patient = patientRepository.findById(patientId)
                    .orElseThrow(() -> new ResourceNotFoundException("Patient","id",patientId));

            Appointment appointment = mapToEntity(appointmentDTO);
            appointment.setPatient(patient);

            Appointment savedAppointment = appointmentRepository.save(appointment);
            return mapToDTO(savedAppointment);
        }

        @Override
        public AppointmentDTO getAppointmentById(Long patientId, Long appointmentId) {
            Patient patient = patientRepository.findById(patientId)
                    .orElseThrow(() -> new ResourceNotFoundException("Patient","id",patientId));

            Appointment appointment = appointmentRepository.findById(patientId).orElseThrow(
                    () -> new ResourceNotFoundException("appointment", "appointmentId", appointmentId)
            );

            if(!appointment.getPatient().getId().equals(patient.getId())){
                throw new ResourceNotFoundException( "appointment","appointmentId",appointmentId);
            }

    //        Appointment appointment = appointmentRepository.findByIdAndPatient(appointmentId, patient)
    //                .orElseThrow(() -> new ResourceNotFoundException("Patient","id",patientId));

            return mapToDTO(appointment);
        }

        @Override
        public List<AppointmentDTO> getAllAppointments(Long patientId) {
            Patient patient = patientRepository.findById(patientId)
                    .orElseThrow(() -> new ResourceNotFoundException("Patient","id",patientId));

            List<Appointment> appointments = appointmentRepository.findByPatient(patient);


            if (appointments.isEmpty()) {
                throw new ResourceNotFoundException("Appointments", "patient", patientId);
            }
            return appointments.stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());
        }

        @Override
        public void deleteAppointment(Long patientId, Long appointmentId) {
            Patient patient = patientRepository.findById(patientId)
                    .orElseThrow(() -> new ResourceNotFoundException("Patient","id",patientId));

            Appointment appointment = appointmentRepository.findById(appointmentId)
                    .orElseThrow(() -> new ResourceNotFoundException("Appointment","appointmentId",appointmentId));

            appointmentRepository.delete(appointment);
        }

        private AppointmentDTO mapToDTO(Appointment appointment) {
            return modelMapper.map(appointment, AppointmentDTO.class);
        }

        private Appointment mapToEntity(AppointmentDTO appointmentDTO) {
            return modelMapper.map(appointmentDTO, Appointment.class);
        }
    }