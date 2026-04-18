package prod.degworks_and_bs_backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import prod.degworks_and_bs_backend.model.ProfessorReview;
import prod.degworks_and_bs_backend.service.ProfessorReviewService;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ProfessorReviewController {

    private final ProfessorReviewService professorReviewService;

    /**
     * Add a review for a professor (user)
     */
    @PostMapping
    public ResponseEntity<ProfessorReview> addReview(
            @Valid @RequestBody ProfessorReview review
    ) {
        return ResponseEntity.ok(
                professorReviewService.addReview(review)
        );
    }

    /**
     * Get all reviews for a specific professor (public)
     */
    @GetMapping("/professor/{professorId}")
    public ResponseEntity<List<ProfessorReview>> getReviewsByProfessor(
            @PathVariable Integer professorId
    ) {
        return ResponseEntity.ok(
                professorReviewService.getReviewsByProfessorId(professorId)
        );
    }

    /**
     * Get a single review by ID (public/admin)
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProfessorReview> getReview(
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok(
                professorReviewService.getReview(id)
        );
    }

    /**
     * Delete a review (admin)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(
            @PathVariable Integer id
    ) {
        professorReviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get all reviews (admin)
     */
    @GetMapping
    public ResponseEntity<List<ProfessorReview>> getAllReviews() {
        return ResponseEntity.ok(
                professorReviewService.getAllReviews()
        );
    }
}