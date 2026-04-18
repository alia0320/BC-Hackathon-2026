package prod.degworks_and_bs_backend.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import prod.degworks_and_bs_backend.model.Requirement;
import prod.degworks_and_bs_backend.repository.CourseRepository;
import prod.degworks_and_bs_backend.repository.RequirementRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RequirementService {

    private final RequirementRepository requirementRepository;
    private final CourseRepository courseRepository;

    // =========================
    // CREATE
    // =========================

    public Requirement createRequirement(Requirement requirement) {

        requirement.setCode(requirement.getCode().toUpperCase());

        if (requirementRepository.existsByCodeIgnoreCase(requirement.getCode())) {
            throw new RuntimeException(
                    "Requirement with code already exists: " + requirement.getCode()
            );
        }

        return requirementRepository.save(requirement);
    }

    // =========================
    // READ
    // =========================

    public Requirement getRequirementById(Integer id) {
        return requirementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Requirement not found"));
    }

    public Requirement getRequirementByCode(String code) {
        return requirementRepository.findByCodeIgnoreCase(code)
                .orElseThrow(() -> new RuntimeException("Requirement not found"));
    }

    public List<Requirement> getActiveRequirements() {
        return requirementRepository.findByActiveTrue();
    }

    public List<Requirement> searchRequirements(String keyword) {
        return requirementRepository.findByNameContainingIgnoreCase(keyword);
    }

    // =========================
    // UPDATE
    // =========================

    public Requirement updateRequirement(Integer id, Requirement updated) {


        Requirement existing = getRequirementById(id);
        if(existing == null) {
            throw new RuntimeException("Cannot find requirement");
        }

        // Code is immutable
        updated.setCode(existing.getCode());

        existing.setName(updated.getName());
        existing.setRequiredCredits(updated.getRequiredCredits());

        return requirementRepository.save(existing);
    }

    // =========================
    // VISIBILITY (SOFT DELETE)
    // =========================

    public Requirement hideRequirement(Integer id) {
        Requirement requirement = getRequirementById(id);

        if (requirement == null) {
            throw new RuntimeException("Cannot find requirement");
        }
        requirement.setActive(false);
        return requirementRepository.save(requirement);
    }

    public Requirement restoreRequirement(Integer id) {
        Requirement requirement = getRequirementById(id);
        if (requirement == null) {
            throw new RuntimeException("Cannot find requirement");
        }
        requirement.setActive(true);
        return requirementRepository.save(requirement);
    }

    // =========================
    // HARD DELETE (ADMIN CLEANUP)
    // =========================

    public void deleteRequirement(Integer id) {

        Requirement requirement = getRequirementById(id);

        if (courseRepository.existsBySatisfiesContaining(requirement)) {
            throw new RuntimeException(
                    "Cannot delete requirement that is assigned to courses"
            );
        }

        requirementRepository.delete(requirement);
    }
}