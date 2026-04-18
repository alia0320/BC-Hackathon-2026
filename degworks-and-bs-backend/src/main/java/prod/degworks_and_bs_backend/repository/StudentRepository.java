package prod.degworks_and_bs_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prod.degworks_and_bs_backend.model.Student;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    // Find by school email (even though it's @JsonIgnore, it's still persisted)
    Optional<Student> findBySchoolEmail(String schoolEmail);

    // Check if a student exists by email
    boolean existsBySchoolEmail(String schoolEmail);

    // Search by name (case-insensitive)
    Optional<Student> findByNameIgnoreCase(String name);
}

