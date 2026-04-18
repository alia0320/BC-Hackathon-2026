package prod.degworks_and_bs_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import prod.degworks_and_bs_backend.model.Schedule;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

    /**
     * Get all schedules for a student
     */
    List<Schedule> findByEmplid(Integer emplid);

    /**
     * Get a student's schedule for a specific semester
     */
    Optional<Schedule> findByEmplidAndSemester(Integer emplid, String semester);

    /**
     * Check if a schedule already exists for a student in a semester
     * (used to enforce one-schedule-per-semester rule)
     */
    boolean existsByEmplidAndSemester(Integer emplid, String semester);
}