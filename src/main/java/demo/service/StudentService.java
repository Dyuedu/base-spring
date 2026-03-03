package demo.service;

import java.util.List;

import demo.entity.DTO.request.StudentRequest;
import demo.entity.DTO.response.StudentResponse;
import demo.util.ApiResponse;

public interface StudentService {
    StudentResponse saveStudent(StudentRequest studentRequest);
    List<StudentResponse> saveStudents(List<StudentRequest> studentRequests);
    List<StudentResponse> findAllStudents();
    StudentResponse findStudentById(Long id);
    ApiResponse<Void> deleteStudentById(Long id);
    StudentResponse updateStudent(Long id, StudentRequest studentRequest);
}
