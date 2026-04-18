package prod.degworks_and_bs_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import prod.degworks_and_bs_backend.model.Course;
import prod.degworks_and_bs_backend.model.Requirement;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {

    @Query("SELECT c FROM Course c WHERE LOWER(c.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(c.code) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Course> searchCourse (String keyword);

    boolean existsByPrerequisitesContaining(String courseCode);

    boolean existsBySatisfiesContaining(Requirement requirement);
}
