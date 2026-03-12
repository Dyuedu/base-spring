package demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import demo.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByEmail(String email);
    List<Student> findAll();
    Optional<Student> getStudentById(Long id);
    Optional<Student> getStudentByStudentCode(String studentCode);
    void deleteStudentById(Long id);
}
