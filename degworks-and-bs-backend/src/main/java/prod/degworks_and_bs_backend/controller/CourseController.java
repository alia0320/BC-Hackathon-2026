package prod.degworks_and_bs_backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prod.degworks_and_bs_backend.model.Course;
import prod.degworks_and_bs_backend.service.CourseService;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    /**
     * Get all courses (public)
     */
    @GetMapping
    public ResponseEntity<List<Course>> getCourses() {
        return ResponseEntity.ok(courseService.getCourses());
    }

    /**
     * Search courses by code or title (public)
     */
    @GetMapping("/search")
    public ResponseEntity<List<Course>> searchCourses(
            @RequestParam String keyword
    ) {
        return ResponseEntity.ok(courseService.searchCourses(keyword));
    }

    /**
     * Create a new course (admin)
     */
    @PostMapping
    public ResponseEntity<Course> createCourse(
            @Valid @RequestBody Course course
    ) {
        return ResponseEntity.ok(courseService.createCourse(course));
    }

    /**
     * Update an existing course (admin)
     */
    @PutMapping("/{code}")
    public ResponseEntity<Course> updateCourse(
            @PathVariable String code,
            @Valid @RequestBody Course updatedCourse
    ) {
        return courseService.updateCourse(code, updatedCourse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete a course (admin)
     */
    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteCourse(
            @PathVariable String code
    ) {
        boolean deleted = courseService.deleteCourse(code.toUpperCase());
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}