package demo.entity.DTO.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record StudentResponse(Long id, String studentCode, String fullName, String email, LocalDate dateOfBirth, String major) {

}
