package prod.degworks_and_bs_backend.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import prod.degworks_and_bs_backend.exception.ApiException;
import prod.degworks_and_bs_backend.model.Professor;
import prod.degworks_and_bs_backend.model.ProfessorReview;
import prod.degworks_and_bs_backend.repository.ProfessorRepository;
import prod.degworks_and_bs_backend.repository.ProfessorReviewsRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProfessorService {

    private final ProfessorRepository professorRepository;
    private final ProfessorReviewsRepository reviewsRepository;

    //For frontend search
    public List<Professor> searchProfessors(String keyword) {
        return professorRepository.findByNameContainingIgnoreCase(keyword);
    }

    // Used internally
    public Professor getProfessorById(Integer profId) {
        return professorRepository.findById(profId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND,"Professor not found"));
    }

    // Update professor rating by averaging all review scores
    public Professor updateProfessorRating(Integer professorId) {
        Professor professor = getProfessorById(professorId);

        List<ProfessorReview> reviews = reviewsRepository.findByProfessorId(professorId);

        if (reviews.isEmpty()) {
            professor.setRating(0.0);
            return professorRepository.save(professor);
        }

        double avg = reviews.stream()
                .mapToInt(ProfessorReview::getScore)
                .average()
                .orElse(0.0);

        professor.setRating(avg);
        return professorRepository.save(professor);
    }

    // Get all professors
    public List<Professor> getAllProfessors() {
        return professorRepository.findAll();
    }

    // Save or update a professor (for admin)
    public Professor saveProfessor(Professor professor) {
        return professorRepository.save(professor);
    }

    // Delete professor (for admin)
    public void deleteProfessor(Integer profId) {

        Professor professor = professorRepository.findById(profId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND,"Professor not found"));

        // Block deletion if reviews exist
        if (reviewsRepository.existsByProfessorId(profId)) {
            throw new ApiException(HttpStatus.BAD_REQUEST,
                    "Cannot delete professor with existing reviews"
            );
        }

        professorRepository.delete(professor);
    }

    // for admin
    public Professor hideProfessor(Integer profId) {
        Professor professor = professorRepository.findById(profId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND,"Professor not found"));

        professor.setActive(false);
        return professorRepository.save(professor);
    }

    // for admin
    public Professor restoreProfessor(Integer professorID) {
        Professor professor = getProfessorById(professorID);
        professor.setActive(true);
        return professorRepository.save(professor);
    }

    public List<Professor> getActiveProfessors() {
        return professorRepository.findByActiveTrue();
    }

    // this method is for the admin
    public Professor updateProfessor(Integer professorId, Professor updated) {
        Professor professor = getProfessorById(professorId);

        professor.setName(updated.getName());
        professor.setDepartment(updated.getDepartment());
        // rating is not updated here — only via reviews

        return professorRepository.save(professor);
    }


}
