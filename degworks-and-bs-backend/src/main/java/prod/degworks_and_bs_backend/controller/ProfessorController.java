package prod.degworks_and_bs_backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import prod.degworks_and_bs_backend.model.Professor;
import prod.degworks_and_bs_backend.service.ProfessorService;

import java.util.List;

@RestController
@RequestMapping("/api/professors")
@RequiredArgsConstructor
public class ProfessorController {

    private final ProfessorService professorService;

    /**
     * Get all professors (admin)
     */
    @GetMapping
    public ResponseEntity<List<Professor>> getAllProfessors() {
        return ResponseEntity.ok(professorService.getAllProfessors());
    }

    /**
     * Get active professors only (public)
     */
    @GetMapping("/active")
    public ResponseEntity<List<Professor>> getActiveProfessors() {
        return ResponseEntity.ok(professorService.getActiveProfessors());
    }

    /**
     * Search professors by name (public)
     */
    @GetMapping("/search")
    public ResponseEntity<List<Professor>> searchProfessors(
            @RequestParam String keyword
    ) {
        return ResponseEntity.ok(
                professorService.searchProfessors(keyword)
        );
    }

    /**
     * Get professor by ID (public/admin)
     */
    @GetMapping("/{id}")
    public ResponseEntity<Professor> getProfessorById(
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok(
                professorService.getProfessorById(id)
        );
    }

    /**
     * Create professor (admin)
     */
    @PostMapping
    public ResponseEntity<Professor> createProfessor(
            @Valid @RequestBody Professor professor
    ) {
        return ResponseEntity.ok(
                professorService.saveProfessor(professor)
        );
    }

    /**
     * Update professor info (admin)
     */
    @PutMapping("/{id}")
    public ResponseEntity<Professor> updateProfessor(
            @PathVariable Integer id,
            @RequestBody Professor updated
    ) {
        return ResponseEntity.ok(
                professorService.updateProfessor(id, updated)
        );
    }

    /**
     * Hide professor (soft delete) (admin)
     */
    @PutMapping("/{id}/hide")
    public ResponseEntity<Professor> hideProfessor(
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok(
                professorService.hideProfessor(id)
        );
    }

    /**
     * Restore hidden professor (admin)
     */
    @PutMapping("/{id}/restore")
    public ResponseEntity<Professor> restoreProfessor(
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok(
                professorService.restoreProfessor(id)
        );
    }

    /**
     * Delete professor (admin)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfessor(
            @PathVariable Integer id
    ) {
        professorService.deleteProfessor(id);
        return ResponseEntity.noContent().build();
    }
}

