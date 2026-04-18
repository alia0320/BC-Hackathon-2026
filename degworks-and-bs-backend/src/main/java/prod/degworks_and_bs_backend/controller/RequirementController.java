package prod.degworks_and_bs_backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prod.degworks_and_bs_backend.model.Requirement;
import prod.degworks_and_bs_backend.service.RequirementService;

import java.util.List;

@RestController
@RequestMapping("/api/requirements")
@RequiredArgsConstructor
public class RequirementController {

    private final RequirementService requirementService;

    /**
     * Create a new requirement (admin)
     */
    @PostMapping
    public ResponseEntity<Requirement> createRequirement(
            @Valid @RequestBody Requirement requirement
    ) {
        return ResponseEntity.ok(
                requirementService.createRequirement(requirement)
        );
    }

    /**
     * Get requirement by ID (admin)
     */
    @GetMapping("/{id}")
    public ResponseEntity<Requirement> getRequirementById(
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok(
                requirementService.getRequirementById(id)
        );
    }

    /**
     * Get requirement by code (public/admin)
     */
    @GetMapping("/code/{code}")
    public ResponseEntity<Requirement> getRequirementByCode(
            @PathVariable String code
    ) {
        return ResponseEntity.ok(
                requirementService.getRequirementByCode(code)
        );
    }

    /**
     * Get all active requirements (public)
     */
    @GetMapping("/active")
    public ResponseEntity<List<Requirement>> getActiveRequirements() {
        return ResponseEntity.ok(
                requirementService.getActiveRequirements()
        );
    }

    /**
     * Search requirements by name (public)
     */
    @GetMapping("/search")
    public ResponseEntity<List<Requirement>> searchRequirements(
            @RequestParam String keyword
    ) {
        return ResponseEntity.ok(
                requirementService.searchRequirements(keyword)
        );
    }

    /**
     * Update requirement (admin)
     */
    @PutMapping("/{id}")
    public ResponseEntity<Requirement> updateRequirement(
            @PathVariable Integer id,
            @Valid @RequestBody Requirement updated
    ) {
        return ResponseEntity.ok(
                requirementService.updateRequirement(id, updated)
        );
    }

    /**
     * Hide requirement (soft delete) (admin)
     */
    @PutMapping("/{id}/hide")
    public ResponseEntity<Requirement> hideRequirement(
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok(
                requirementService.hideRequirement(id)
        );
    }

    /**
     * Restore hidden requirement (admin)
     */
    @PutMapping("/{id}/restore")
    public ResponseEntity<Requirement> restoreRequirement(
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok(
                requirementService.restoreRequirement(id)
        );
    }

    /**
     * Hard delete requirement (admin cleanup)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequirement(
            @PathVariable Integer id
    ) {
        requirementService.deleteRequirement(id);
        return ResponseEntity.noContent().build();
    }
}