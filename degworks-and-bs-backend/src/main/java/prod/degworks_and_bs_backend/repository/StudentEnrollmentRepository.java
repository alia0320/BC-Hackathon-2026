package prod.degworks_and_bs_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prod.degworks_and_bs_backend.model.StudentEnrollment;
import prod.degworks_and_bs_backend.service.EnrollmentService;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentEnrollmentRepository extends JpaRepository<StudentEnrollment, Integer> {

    List<StudentEnrollment> findByEmplid(Integer emplid);

    List<StudentEnrollment> findByEmplidAndCompleted(Integer emplid, boolean completed);

    List<StudentEnrollment> findByEmplidAndCompletedTrue(Integer emplid);

    boolean existsByEmplidAndCourseCodeAndSemester(Integer emplid, String courseCode, String semester);

    Optional<StudentEnrollment> findByEmplidAndCourseCodeAndSemester(Integer emplid, String courseCode, String semester);

    List<StudentEnrollment> findByEmplidAndCourseCodeAndCompleted(Integer emplid, String courseCode, boolean completed);


}
