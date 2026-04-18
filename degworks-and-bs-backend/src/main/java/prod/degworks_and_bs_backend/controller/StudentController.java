package prod.degworks_and_bs_backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prod.degworks_and_bs_backend.model.Student;
import prod.degworks_and_bs_backend.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<Student> createStudent(@Valid @RequestBody Student student) {
        return ResponseEntity.ok(studentService.saveStudent(student));
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/{emplid}")
    public ResponseEntity<Student> getStudentById(@PathVariable Integer emplid) {
        return studentService.getStudentById(emplid)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Student> getStudentByEmail(@PathVariable String email) {
        return studentService.getStudentByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{emplid}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Integer emplid) {
        studentService.deleteStudent(emplid);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{emplid}/gpa")
    public ResponseEntity<Student> recalculateGPA(@PathVariable Integer emplid) {
        return ResponseEntity.ok(studentService.updateGPA(emplid));
    }
}