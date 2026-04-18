package prod.degworks_and_bs_backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import prod.degworks_and_bs_backend.dto.AddEnrollmentsRequest;
import prod.degworks_and_bs_backend.dto.CreateScheduleRequest;
import prod.degworks_and_bs_backend.model.Schedule;
import prod.degworks_and_bs_backend.service.ScheduleService;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    /**
     * Create a new schedule for a student and semester
     */
    @PostMapping
    public ResponseEntity<Schedule> createSchedule(
            @Valid @RequestBody CreateScheduleRequest request
    ) {
        Schedule schedule = scheduleService.createSchedule(
                request.getEmplid(),
                request.getSemester()
        );
        return ResponseEntity.ok(schedule);
    }

    /**
     * Get a student's schedule for a semester
     */
    @GetMapping
    public ResponseEntity<Schedule> getSchedule(
            @RequestParam Integer emplid,
            @RequestParam String semester
    ) {
        return ResponseEntity.ok(
                scheduleService.getSchedule(emplid, semester)
        );
    }

    /**
     * Add enrollments to an existing schedule
     */
    @PutMapping("/{scheduleId}/enrollments")
    public ResponseEntity<Schedule> addEnrollments(
            @PathVariable Integer scheduleId,
            @Valid @RequestBody AddEnrollmentsRequest request
    ) {
        Schedule updated = scheduleService.addEnrollmentsToSchedule(
                scheduleId,
                request.getEnrollmentIds()
        );
        return ResponseEntity.ok(updated);
    }
}