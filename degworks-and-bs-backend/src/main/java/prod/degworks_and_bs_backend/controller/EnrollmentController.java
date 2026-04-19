package prod.degworks_and_bs_backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prod.degworks_and_bs_backend.model.StudentEnrollment;
import prod.degworks_and_bs_backend.service.EnrollmentService;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    /**
     * Create a new enrollment (admin)
     */
    @PostMapping("/students/{emplid}")
    public ResponseEntity<StudentEnrollment> enrollStudent(
            @PathVariable Integer emplid,
            @Valid @RequestBody StudentEnrollment enrollment
    ) {
        return ResponseEntity.ok(
                enrollmentService.enrollStudent(emplid, enrollment)
        );
    }

    /**
     * Get all enrollments for a student (admin + student)
     */
    @GetMapping("/students/{emplid}")
    public ResponseEntity<List<StudentEnrollment>> getEnrollments(
            @PathVariable Integer emplid
    ) {
        return ResponseEntity.ok(
                enrollmentService.getEnrollments(emplid)
        );
    }

    /**
     * Get completed enrollments for a student (admin)
     */
    @GetMapping("/students/{emplid}/completed")
    public ResponseEntity<List<StudentEnrollment>> getCompletedEnrollments(
            @PathVariable Integer emplid
    ) {
        return ResponseEntity.ok(
                enrollmentService.getCompletedEnrollments(emplid)
        );
    }

    /**
     * Update grade for an enrollment (admin)
     */
    @PutMapping("/{enrollmentId}/grade")
    public ResponseEntity<StudentEnrollment> updateGrade(
            @PathVariable Integer enrollmentId,
            @RequestParam String grade
    ) {
        return ResponseEntity.ok(
                enrollmentService.updateGrade(enrollmentId, grade)
        );
    }

    /**
     * Complete a course and trigger GPA update (admin)
     */
    @PutMapping("/complete")
    public ResponseEntity<StudentEnrollment> completeCourse(
            @RequestParam Integer emplid,
            @RequestParam String courseCode,
            @RequestParam String grade,
            @RequestParam String semester
    ) {
        return ResponseEntity.ok(
                enrollmentService.completeCourse(
                        emplid,
                        courseCode,
                        grade,
                        semester
                )
        );
    }

    /**
     * Delete an enrollment (admin)
     */
    @DeleteMapping("/{enrollmentId}")
    public ResponseEntity<Void> deleteEnrollment(
            @PathVariable Integer enrollmentId
    ) {
        enrollmentService.deleteEnrollment(enrollmentId);
        return ResponseEntity.noContent().build();
    }
}