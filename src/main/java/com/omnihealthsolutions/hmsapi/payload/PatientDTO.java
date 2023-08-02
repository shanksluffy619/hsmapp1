package com.omnihealthsolutions.hmsapi.payload;

import com.omnihealthsolutions.hmsapi.utils.Gender;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
@Data
public class PatientDTO {
    private Long id;
    @NotBlank
    @Size(min = 3,message = "min 3 letters required")
    private String name;

    private Gender gender;

    private LocalDate dateOfBirth;
    private boolean deleted;

    // Other attributes, if any

    // Getters and setters

    // Constructors, if needed

    // Other methods
}
