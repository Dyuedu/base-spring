package demo.service.Impl;

import java.util.List;

import demo.exception.CodeDuplicateException;
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

        if (studentRepository.getStudentByStudentCode(studentRequest.studentCode()).isPresent()){
            throw new CodeDuplicateException();
        }
        Student student = Student.builder()
                .studentCode(studentRequest.studentCode())
                .email(studentRequest.email())
                .fullname(studentRequest.fullName())
                .dateOfBirth(studentRequest.dateOfBirth())
                .major(studentRequest.major())
                .build();
        Student savedStudent = studentRepository.save(student);

        return StudentResponse.builder()
                .id(savedStudent.getId())
                .studentCode(savedStudent.getStudentCode())
                .email(savedStudent.getEmail())
                .fullName(savedStudent.getFullname())
                .major(savedStudent.getMajor())
                .build();
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
        return students.stream().map(student -> StudentResponse.builder()
                .id(student.getId())
                .studentCode(student.getStudentCode())
                .email(student.getEmail())
                .fullName(student.getFullname())
                .dateOfBirth(student.getDateOfBirth())
                .major(student.getMajor())
                .build()).toList();
    }

    @Override
    public StudentResponse findStudentById(Long id) {
        Student student = studentRepository.getStudentById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));
        return StudentResponse.builder()
                .id(student.getId())
                .studentCode(student.getStudentCode())
                .email(student.getEmail())
                .fullName(student.getFullname())
                .dateOfBirth(student.getDateOfBirth())
                .major(student.getMajor())
                .build();
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
        student.setStudentCode(studentRequest.studentCode());
        student.setFullname(studentRequest.fullName());
        student.setEmail(studentRequest.email());
        student.setDateOfBirth(studentRequest.dateOfBirth());
        student.setMajor(studentRequest.major());
        Student updatedStudent = studentRepository.save(student);
        return StudentResponse.builder()
                .id(updatedStudent.getId())
                .studentCode(updatedStudent.getStudentCode())
                .email(updatedStudent.getEmail())
                .fullName(updatedStudent.getFullname())
                .dateOfBirth(updatedStudent.getDateOfBirth())
                .major(updatedStudent.getMajor())
                .build();
    }

}
