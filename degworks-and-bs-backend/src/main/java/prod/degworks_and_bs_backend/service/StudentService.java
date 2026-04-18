package prod.degworks_and_bs_backend.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import prod.degworks_and_bs_backend.exception.ApiException;
import prod.degworks_and_bs_backend.model.Student;
import prod.degworks_and_bs_backend.model.StudentEnrollment;
import prod.degworks_and_bs_backend.repository.StudentEnrollmentRepository;
import prod.degworks_and_bs_backend.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentEnrollmentRepository studentEnrollmentRepository;

    // Create or update a student
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    // Get all students
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Get a student by emplid
    public Optional<Student> getStudentById(Integer emplid) {
        return studentRepository.findById(emplid);
    }

    // Delete a student
    public void deleteStudent(Integer emplid) {
        studentRepository.deleteById(emplid);
    }

    // Find by email
    public Optional<Student> getStudentByEmail(String email) {
        return studentRepository.findBySchoolEmail(email);
    }

    // Check if email exists
    public boolean emailExists(String email) {
        return studentRepository.existsBySchoolEmail(email);
    }

    // Update GPA and total credits
    // Update GPA based on completed enrollments
    public Student updateGPA(Integer emplid) {
        Student student = studentRepository.findById(emplid)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND,"Student not found"));

        List<StudentEnrollment> completedCourses =
                studentEnrollmentRepository.findByEmplidAndCompleted(emplid, true);

        if (completedCourses.isEmpty()) {
            student.setGPA(0);
            return studentRepository.save(student);
        }

        double totalQualityPoints = 0;
        int totalCredits = 0;

        for (StudentEnrollment e : completedCourses) {
            double gradeValue = GradeUtils.convertToPoints(e.getGrade());
            totalQualityPoints += gradeValue * e.getCredits();
            totalCredits += e.getCredits();
        }

        student.setGPA(totalQualityPoints / totalCredits);
        student.setCredits(totalCredits);

        return studentRepository.save(student);
    }
}
