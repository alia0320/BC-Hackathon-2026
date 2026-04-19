package prod.degworks_and_bs_backend.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import prod.degworks_and_bs_backend.exception.ApiException;
import prod.degworks_and_bs_backend.model.Schedule;
import prod.degworks_and_bs_backend.model.StudentEnrollment;
import prod.degworks_and_bs_backend.repository.ScheduleRepository;
import prod.degworks_and_bs_backend.repository.StudentEnrollmentRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final StudentEnrollmentRepository enrollmentRepository;

    /**
     * Create a new schedule for a student and semester
     */
    public Schedule createSchedule(Integer emplid, String semester) {

        validateSemester(semester);

        String normalizedSemester = semester.trim();

        if (scheduleRepository.existsByEmplidAndSemester(emplid, normalizedSemester)) {
            throw new ApiException(HttpStatus.BAD_REQUEST,
                    "Schedule already exists for student " + emplid +
                            " in semester " + normalizedSemester
            );
        }

        Schedule schedule = new Schedule();
        schedule.setEmplid(emplid);
        schedule.setSemester(normalizedSemester);

        return scheduleRepository.save(schedule);
    }

    /**
     * Attach enrollments to a schedule with semester validation
     */
    public Schedule addEnrollmentsToSchedule(
            Integer scheduleId,
            List<Integer> enrollmentIds
    ) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() ->
                        new ApiException(HttpStatus.NOT_FOUND, "Schedule not found")
                );

        List<StudentEnrollment> enrollments =
                enrollmentRepository.findAllById(enrollmentIds);

        if (enrollments.size() != enrollmentIds.size()) {
            throw new ApiException(
                    HttpStatus.BAD_REQUEST,
                    "One or more enrollments were not found"
            );
        }

        for (StudentEnrollment enrollment : enrollments) {

            // Rule: enrollment must belong to same student
            if (!enrollment.getEmplid().equals(schedule.getEmplid())) {
                throw new ApiException(
                        HttpStatus.BAD_REQUEST,
                        "Enrollment does not belong to the same student"
                );
            }

            // Rule: semester must match
            if (!enrollment.getSemester().equalsIgnoreCase(schedule.getSemester())) {
                throw new ApiException(
                        HttpStatus.BAD_REQUEST,
                        "Enrollment semester mismatch: expected " +
                                schedule.getSemester() +
                                " but found " +
                                enrollment.getSemester()
                );
            }
        }

        schedule.getEnrollments().addAll(enrollments);
        return scheduleRepository.save(schedule);
    }

    /**
     * Get a student's schedule for a semester
     */
    public Schedule getSchedule(Integer emplid, String semester) {
        return scheduleRepository
                .findByEmplidAndSemester(emplid, semester.trim())
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
    }

    /**
     * Basic semester validation
     */
    private void validateSemester(String semester) {
        if (semester == null || semester.isBlank()) {
            throw new RuntimeException("Semester cannot be blank");
        }
    }
}