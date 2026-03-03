package demo.entity.DTO.response;

import java.time.LocalDate;

public record StudentResponse(Long id, String studentCode, String fullName, String email, LocalDate dateOfBirth, String major) {

}
