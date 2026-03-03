package demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false, columnDefinition = "varchar(20)")
    private String student_code;
    @Column(nullable = false, columnDefinition = "varchar(100)")
    private String full_name;
    @Column(unique = true, nullable = false, columnDefinition = "varchar(100)")
    private String email;
    @Column(nullable = false)
    private LocalDate date_of_birth;
    @Column(nullable = false, columnDefinition = "varchar(100)")
    private String major;
}
