package prod.degworks_and_bs_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prod.degworks_and_bs_backend.model.Requirement;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequirementRepository extends JpaRepository<Requirement, Integer> {

    // =========================
    // IDENTITY / LOOKUP
    // =========================

    Optional<Requirement> findByCodeIgnoreCase(String code);

    boolean existsByCodeIgnoreCase(String code);

    // =========================
    // VISIBILITY / DISPLAY
    // =========================

    List<Requirement> findByActiveTrue();

    // =========================
    // SEARCH (optional, UI)
    // =========================

    List<Requirement> findByNameContainingIgnoreCase(String keyword);

}