package prod.degworks_and_bs_backend.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import prod.degworks_and_bs_backend.exception.ApiException;
import prod.degworks_and_bs_backend.model.Course;
import prod.degworks_and_bs_backend.model.Student;
import prod.degworks_and_bs_backend.model.StudentEnrollment;
import prod.degworks_and_bs_backend.repository.CourseRepository;
import prod.degworks_and_bs_backend.repository.StudentEnrollmentRepository;
import prod.degworks_and_bs_backend.repository.StudentRepository;

import javax.print.attribute.IntegerSyntax;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class EnrollmentService {

    private final StudentEnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final StudentService studentService;
    private final CourseRepository courseRepository;

    // Create a new enrollment (admin)
        public StudentEnrollment enrollStudent(Integer emplid, StudentEnrollment enrollment) {

            // 1. Student must exist
            if (!studentRepository.existsById(emplid)) {
                throw new ApiException(HttpStatus.NOT_FOUND, "Student not found");
            }

            // 2. Prevent duplicate enrollment
            if (enrollmentRepository.existsByEmplidAndCourseCodeAndSemester(
                    emplid,
                    enrollment.getCourseCode(),
                    enrollment.getSemester()
            )) {
                throw new ApiException(HttpStatus.BAD_REQUEST, "This enrollment already exists");
            }

            // 3. Academic rules (there is no active checker for whether an equivalent course satisfies the prerequisite)
            // Can be fixed with Course -> List<String> equivalentCourses, then make a checker for this
            // also consider that co-requisites are also a concern (can be resolved with Course -> corequisites)
            //      this will still be a problem for when two courses are co-requisites of each other
            assertRetakeAllowed(emplid, enrollment.getCourseCode());
            assertPrerequisitesSatisfied(emplid, enrollment.getCourseCode());

            // 4. Create enrollment
            enrollment.setEmplid(emplid);
            enrollment.setCompleted(false);
            enrollment.setGrade(null);

            return enrollmentRepository.save(enrollment);
        }

    private void assertRetakeAllowed(Integer emplid, String courseCode) {

        StudentEnrollment previousCompleted =
                enrollmentRepository.findByEmplidAndCompleted(emplid, true)
                        .stream()
                        .filter(e -> e.getCourseCode().equalsIgnoreCase(courseCode))
                        .findFirst()
                        .orElse(null);

        if (previousCompleted == null) {
            return; // never taken or never completed → allowed
        }

        double gradePoints =
                GradeUtils.convertToPoints(previousCompleted.getGrade());

        if (gradePoints >= 2.0) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Enrollment denied: already taken finished course with passing grade"
            );
        }
    }

    private void assertPrerequisitesSatisfied(Integer emplid, String courseCode) {

        // 1. Load course
        Course course = courseRepository.findById(courseCode)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Course not found"));

        List<String> prerequisites = course.getPrerequisites();

        // 2. No prerequisites → allowed
        if (prerequisites == null || prerequisites.isEmpty()) {
            return;
        }

        // 3. Get student's completed enrollments
        List<StudentEnrollment> completedEnrollments =
                enrollmentRepository.findByEmplidAndCompleted(emplid, true);

        // 4. Build set of passed course codes
        Set<String> passedCourses = completedEnrollments.stream()
                .filter(e -> GradeUtils.convertToPoints(e.getGrade()) >= 2.0)
                .map(e -> e.getCourseCode().toUpperCase())
                .collect(Collectors.toSet());

        // 5. Check each prerequisite
        for (String prereq : prerequisites) {
            if (!passedCourses.contains(prereq.toUpperCase())) {
                throw new ApiException(HttpStatus.BAD_REQUEST, "Missing prerequisite: " + prereq
                );
            }
        }
    }

    // Get all enrollments for a student (admin and user)
    // just make sure the student can only see their enrollments
    public List<StudentEnrollment> getEnrollments(Integer emplid) {
        return enrollmentRepository.findByEmplid(emplid);
    }

    // Update grade for a specific enrollment (admin)
    public StudentEnrollment updateGrade(Integer enrollmentId, String newGrade) {
        StudentEnrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND,"Enrollment not found"));

        enrollment.setGrade(newGrade);
        return enrollmentRepository.save(enrollment);
    }

    // Mark a course as completed (admin)
    public StudentEnrollment completeCourse(Integer emplid, String courseCode, String grade, String semester) {
        // 1. Get current enrollment
        StudentEnrollment enrollment = enrollmentRepository
                .findByEmplidAndCourseCodeAndSemester(emplid, courseCode, semester)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND,"Enrollment not found"));

        // 2. Find existing completed attempt (if any)
        StudentEnrollment previousAttempt =
                enrollmentRepository.findByEmplidAndCompleted(emplid, true)
                        .stream()
                        .filter(e -> e.getCourseCode().equalsIgnoreCase(courseCode))
                        .findFirst()
                        .orElse(null);

        // 3. Block retake if previous grade >= C
        if (previousAttempt != null) {
            double previousGradePoints =
                    GradeUtils.convertToPoints(previousAttempt.getGrade());

            if (previousGradePoints >= 2.0) {
                throw new ApiException(HttpStatus.BAD_REQUEST, "Enrollment denied: student already passed this course");
            }
        }

        // 4. Complete current enrollment FIRST
        enrollment.setGrade(grade);
        enrollment.setCompleted(true);
        StudentEnrollment saved = enrollmentRepository.save(enrollment);

        // 5. Demote old failed attempt (if it existed)
        if (previousAttempt != null) {
            previousAttempt.setCompleted(false);
            enrollmentRepository.save(previousAttempt);
        }

        // 6. Recalculate GPA
        studentService.updateGPA(emplid);

        return saved;
    }


    // Delete an enrollment (admin)
    public void deleteEnrollment(Integer enrollmentId) {
        enrollmentRepository.deleteById(enrollmentId);
    }

    // Get only completed courses (admin) [use case might be questionable]
    public List<StudentEnrollment> getCompletedEnrollments(Integer emplid) {
        return enrollmentRepository.findByEmplidAndCompleted(emplid, true);
    }

}

