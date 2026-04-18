package prod.degworks_and_bs_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prod.degworks_and_bs_backend.model.ProfessorReview;

import java.util.List;

@Repository
public interface ProfessorReviewsRepository
        extends JpaRepository<ProfessorReview, Integer> {

    // =========================
    // IDENTITY-SAFE METHODS
    // =========================

    // Used for rating calculations, admin logic
    List<ProfessorReview> findByProfessorId(Integer professorId);

    // =========================
    // SEARCH / UI METHODS ONLY
    // =========================

    // User-facing search (NOT for logic)
    List<ProfessorReview> findByProfessorNameContainingIgnoreCase(String name);

    // Optional: moderation / admin tools
    List<ProfessorReview> findByHelpfulTrue();

    List<ProfessorReview> findByScoreGreaterThanEqual(int score);

    List<ProfessorReview> findAllByOrderByReviewTimeDesc();

    boolean existsByProfessorId(Integer professorId);
}
