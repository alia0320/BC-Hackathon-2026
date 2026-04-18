package prod.degworks_and_bs_backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prod.degworks_and_bs_backend.model.Professor;
import prod.degworks_and_bs_backend.model.ProfessorReview;
import prod.degworks_and_bs_backend.repository.ProfessorReviewsRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProfessorReviewService {

    private final ProfessorReviewsRepository reviewRepository;
    private final ProfessorService professorService;

    public ProfessorReview addReview(ProfessorReview review) {

        // Resolve identity (no ambiguity)
        Professor professor =
                professorService.getProfessorById(review.getProfessorId());

        if (!professor.isActive()) {
            throw new RuntimeException("Cannot review an inactive professor");
        }

        ProfessorReview saved = reviewRepository.save(review);

        professorService.updateProfessorRating(professor.getProfId());

        return saved;
    }

    public List<ProfessorReview> getReviewsByProfessorId(Integer professorId) {
        return reviewRepository.findByProfessorId(professorId);
    }

    public ProfessorReview getReview(Integer id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
    }



    // admin and user
    public void deleteReview(Integer id) {
        ProfessorReview review = getReview(id);
        Integer professorId = review.getProfessorId();

        reviewRepository.delete(review);

        professorService.updateProfessorRating(professorId);
    }

    public List<ProfessorReview> getAllReviews() {
        return reviewRepository.findAll();
    }
}
