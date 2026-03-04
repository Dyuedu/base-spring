package demo.service.Impl;

import java.util.List;

import demo.exception.EmailDuplicateException;
import org.springframework.stereotype.Service;

import demo.entity.Student;
import demo.entity.DTO.request.StudentRequest;
import demo.entity.DTO.response.StudentResponse;
import demo.exception.StudentNotFoundException;
import demo.repository.StudentRepository;
import demo.service.StudentService;
import demo.util.ApiResponse;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public StudentResponse saveStudent(StudentRequest studentRequest) {
        if (studentRepository.findByEmail(studentRequest.email()).isPresent()) {
            throw new EmailDuplicateException();
        }
        Student student = new Student();
        student.setStudent_code(studentRequest.studentCode());
        student.setFull_name(studentRequest.fullName());
        student.setEmail(studentRequest.email());
        student.setDate_of_birth(studentRequest.dateOfBirth());
        student.setMajor(studentRequest.major());
        Student savedStudent = studentRepository.save(student);

        StudentResponse studentResponse = new StudentResponse(
                savedStudent.getId(),
                savedStudent.getStudent_code(),
                savedStudent.getFull_name(),
                savedStudent.getEmail(),
                savedStudent.getDate_of_birth(),
                savedStudent.getMajor());
        return studentResponse;
    }

    @Override
    public List<StudentResponse> saveStudents(List<StudentRequest> studentRequests) {
        return studentRequests.stream()
                .map(this::saveStudent)
                .toList();
    }

    @Override
    public List<StudentResponse> findAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream().map(student -> new StudentResponse(
                student.getId(),
                student.getStudent_code(),
                student.getFull_name(),
                student.getEmail(),
                student.getDate_of_birth(),
                student.getMajor())).toList();
    }

    @Override
    public StudentResponse findStudentById(Long id) {
        Student student = studentRepository.getStudentById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));
        return new StudentResponse(
                student.getId(),
                student.getStudent_code(),
                student.getFull_name(),
                student.getEmail(),
                student.getDate_of_birth(),
                student.getMajor());
    }

    @Override
    public ApiResponse<Void> deleteStudentById(Long id) {
        Student student = studentRepository.getStudentById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));
        studentRepository.delete(student);
        return ApiResponse.success(200,null);
    }

    @Override
    public StudentResponse updateStudent(Long id, StudentRequest studentRequest) {
        Student student = studentRepository.getStudentById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));
        student.setStudent_code(studentRequest.studentCode());
        student.setFull_name(studentRequest.fullName());
        student.setEmail(studentRequest.email());
        student.setDate_of_birth(studentRequest.dateOfBirth());
        student.setMajor(studentRequest.major());
        Student updatedStudent = studentRepository.save(student);
        return new StudentResponse(
                updatedStudent.getId(),
                updatedStudent.getStudent_code(),
                updatedStudent.getFull_name(),
                updatedStudent.getEmail(),
                updatedStudent.getDate_of_birth(),
                updatedStudent.getMajor());
    }

}
