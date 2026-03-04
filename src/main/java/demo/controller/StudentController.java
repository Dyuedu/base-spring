package demo.controller;

import demo.entity.DTO.request.ListStudentRequest;
import demo.exception.ApiErrorException;
import demo.exception.EmailDuplicateException;
import demo.exception.ResourceDuplicateException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.entity.DTO.request.StudentRequest;
import demo.entity.DTO.response.StudentResponse;
import demo.service.StudentService;
import demo.util.ApiResponse;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/students")    
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    @PostMapping("/list")
    public ResponseEntity<ApiResponse<List<StudentResponse>>> save(@RequestBody @Valid ListStudentRequest request) {
        List<StudentResponse> responses = request.getStudents().stream()
                .map(studentService::saveStudent)
                .toList();
        ApiResponse<List<StudentResponse>> apiResponse = ApiResponse.success(200, responses);
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<StudentResponse>> save(@RequestBody @Valid StudentRequest request) {
        try {
            StudentResponse response = studentService.saveStudent(request);
            ApiResponse<StudentResponse> apiResponse = ApiResponse.success(200, response);
            return ResponseEntity.ok(apiResponse);
        } catch (EmailDuplicateException ex) {
            System.out.println("DUPLICATE");
            throw new ResourceDuplicateException("Resource duplicate", "Email is already in use");
        } catch (Exception ex) {
            System.out.println("EXCEPTION");
            throw new ApiErrorException(ex.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<StudentResponse>>> getAllStudents() {
        List<StudentResponse> students = studentService.findAllStudents();
        ApiResponse<List<StudentResponse>> apiResponse = ApiResponse.success(200, students);
        return ResponseEntity.ok(apiResponse);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponse>> getStudentById(@PathVariable Long id) {
        StudentResponse student = studentService.findStudentById(id);
        ApiResponse<StudentResponse> apiResponse = ApiResponse.success(200, student);
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponse>> updateStudent(@PathVariable Long id, @RequestBody StudentRequest request) {
        StudentResponse response = studentService.updateStudent(id, request);
        ApiResponse<StudentResponse> apiResponse = ApiResponse.success(200, response);
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteStudent(@PathVariable Long id) {
        ApiResponse<Void> apiResponse = studentService.deleteStudentById(id);
        return ResponseEntity.ok(apiResponse);
    }
}
