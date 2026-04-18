package prod.degworks_and_bs_backend.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import prod.degworks_and_bs_backend.model.Course;
import prod.degworks_and_bs_backend.repository.CourseRepository;
import org.springframework.data.domain.Pageable;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class CourseService {
    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getCourses () {
        return courseRepository.findAll();
    }

    public Page<Course> getCourses(Pageable pageable) {
        return courseRepository.findAll(pageable);
    }

    public List<Course> searchCourses(String keyword) {
        return courseRepository.searchCourse(keyword);
    }

    //admin only
    public Course createCourse(Course course) {

        // Normalize course code
        course.setCode(course.getCode().toUpperCase());

        // Validate prerequisites
        assertValidPrerequisites(course);

        if (course.getCredits() < 3 || course.getCredits() > 4) {
            course.setCredits(3);
        }

        return courseRepository.save(course);
    }



    //admin only
    public Optional<Course> updateCourse(String id, Course updatedCourse) {

        return courseRepository.findById(id.toUpperCase())
                .map(existing -> {

                    updatedCourse.setCode(existing.getCode()); // code immutable


                    assertValidPrerequisites(updatedCourse);

                    existing.setTitle(updatedCourse.getTitle());
                    existing.setCredits(updatedCourse.getCredits());
                    existing.setPrerequisites(
                            updatedCourse.getPrerequisites()
                                    .stream()
                                    .map(String::toUpperCase)
                                    .toList()
                    );                    existing.setSatisfies(updatedCourse.getSatisfies());

                    return courseRepository.save(existing);
                });
    }

    private void assertValidPrerequisites(Course course) {
        List<String> prerequisites = course.getPrerequisites();

        // 1. No prerequisites → allowed
        if (prerequisites == null || prerequisites.isEmpty()) {
            return;
        }

        Set<String> seen = new HashSet<>();

        String courseCode = course.getCode().toUpperCase();

        for (String prereq : prerequisites) {

            if (prereq == null || prereq.isBlank()) {
                throw new RuntimeException("Prerequisite course code cannot be blank");
            }

            String prereqCode = prereq.toUpperCase();

            if (!seen.add(prereqCode)) {
                throw new RuntimeException(
                        "Duplicate prerequisite detected: " + prereqCode
                );
            }

            // 2. Course cannot be its own prerequisite
            if (courseCode.equals(prereqCode)) {
                throw new RuntimeException(
                        "Course cannot list itself as a prerequisite"
                );
            }

            // 3. Prerequisite must exist
            if (!courseRepository.existsById(prereqCode)) {
                throw new RuntimeException(
                        "Prerequisite course does not exist: " + prereqCode
                );
            }
        }
    }

    //admin only
    public boolean deleteCourse(String id) {

        if (!courseRepository.existsById(id)) {
            return false;
        }

        if (courseRepository.existsByPrerequisitesContaining(id)) {
            throw new RuntimeException(
                    "Cannot delete course used as a prerequisite"
            );
        }

        courseRepository.deleteById(id);
        return true;
    }

}
