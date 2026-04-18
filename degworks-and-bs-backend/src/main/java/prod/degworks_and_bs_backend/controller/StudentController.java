package prod.degworks_and_bs_backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import prod.degworks_and_bs_backend.dto.StudentCreateRequest;
import prod.degworks_and_bs_backend.dto.StudentResponse;
import prod.degworks_and_bs_backend.model.Student;
import prod.degworks_and_bs_backend.service.StudentService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    /**
     * Create a student (DTO-protected)
     */
    @PostMapping
    public ResponseEntity<StudentResponse> createStudent(
            @Valid @RequestBody StudentCreateRequest request
    ) {
        Student student = new Student();
        student.setEmplid(request.getEmplid());
        student.setName(request.getName());
        student.setSchoolEmail(request.getSchoolEmail());
        student.setPassword(request.getPassword());

        Student saved = studentService.saveStudent(student);
        return ResponseEntity.ok(toResponse(saved));
    }

    /**
     * Get all students (admin)
     */
    @GetMapping
    public ResponseEntity<List<StudentResponse>> getAllStudents() {
        return ResponseEntity.ok(
                studentService.getAllStudents()
                        .stream()
                        .map(this::toResponse)
                        .collect(Collectors.toList())
        );
    }

    /**
     * Get student by ID
     */
    @GetMapping("/{emplid}")
    public ResponseEntity<StudentResponse> getStudentById(
            @PathVariable Integer emplid
    ) {
        return studentService.getStudentById(emplid)
                .map(student -> ResponseEntity.ok(toResponse(student)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete student (admin)
     */
    @DeleteMapping("/{emplid}")
    public ResponseEntity<Void> deleteStudent(
            @PathVariable Integer emplid
    ) {
        studentService.deleteStudent(emplid);
        return ResponseEntity.noContent().build();
    }

    /**
     * Recalculate GPA (admin)
     */
    @PutMapping("/{emplid}/gpa")
    public ResponseEntity<StudentResponse> recalculateGPA(
            @PathVariable Integer emplid
    ) {
        Student updated = studentService.updateGPA(emplid);
        return ResponseEntity.ok(toResponse(updated));
    }

    /**
     * Internal mapper (entity → response DTO)
     */
    private StudentResponse toResponse(Student student) {
        return new StudentResponse(
                student.getEmplid(),
                student.getName(),
                student.getGPA(),
                student.getCredits()
        );
    }
}

