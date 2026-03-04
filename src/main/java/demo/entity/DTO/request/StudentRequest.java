package demo.entity.DTO.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public record StudentRequest(
        @NotNull(message = "student code is not null")
        String studentCode,
        @NotNull(message = "full name is not null")
        String fullName,
        @NotNull(message = "email is not null")
        String email,
        LocalDate dateOfBirth,
        String major) {

}
