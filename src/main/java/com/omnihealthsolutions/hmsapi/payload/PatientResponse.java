package com.omnihealthsolutions.hmsapi.payload;

import lombok.Data;

import java.util.List;

@Data
public class PatientResponse {

   private List<PatientDTO> patientDTOList;

   private int pageNo;

   private int pageSize;

   private Long totalElements;

   private int totalpages;

   private boolean last;


}
