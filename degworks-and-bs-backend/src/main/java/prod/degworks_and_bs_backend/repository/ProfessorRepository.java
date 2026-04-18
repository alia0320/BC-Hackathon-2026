package prod.degworks_and_bs_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prod.degworks_and_bs_backend.model.Professor;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Integer> {

    // =========================
    // IDENTITY (SAFE)
    // =========================

    // Already provided by JpaRepository:
    // Optional<Professor> findById(Integer profId);

    // =========================
    // SEARCH / DISCOVERY (UI)
    // =========================

    // User-facing search
    List<Professor> findByNameContainingIgnoreCase(String name);

    // Optional filters
    List<Professor> findByDepartmentIgnoreCase(String department);

    // =========================
    // ADMIN / VALIDATION
    // =========================

    // Used when creating professors to avoid duplicates
    boolean existsByNameIgnoreCaseAndDepartmentIgnoreCase(
            String name,
            String department
    );

    // Visibility
    List<Professor> findByActiveTrue();
}
