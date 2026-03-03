package demo.entity.DTO.request;

import jakarta.validation.Valid;
import lombok.Data;

import java.util.List;

@Data
public class ListStudentRequest {
    @Valid
    private List<StudentRequest> students;
}
