package com.project.professor.allocation.pedrinho.service;

import com.project.professor.allocation.pedrinho.entity.Allocation;
import com.project.professor.allocation.pedrinho.entity.Course;
import com.project.professor.allocation.pedrinho.entity.Professor;
import com.project.professor.allocation.pedrinho.exception.NotFoundException;
import com.project.professor.allocation.pedrinho.repository.AllocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AllocationService {
    private final AllocationRepository allocationRepository;
    private final ProfessorService professorService;
    private final CourseService courseService;

    public Allocation save(Allocation allocation) {
        return this.saveInternal(allocation);
    }

    public Allocation update(Allocation allocation, Long allocationId) {
        if (Objects.isNull(allocationId) || !this.allocationRepository.existsById(allocationId)) {
            throw new NotFoundException(String.format("Allocation not found with id :: %d", allocationId));
        }

        allocation.setId(allocationId);
        return this.saveInternal(allocation);
    }

    public List<Allocation> findByCourse(Long courseId) {
        Course course = new Course();
        course.setId(courseId);
        return this.allocationRepository.findByCourse(course);
    }

    public List<Allocation> findByProfessor(Long professorId) {
        Professor professor = new Professor();
        professor.setId(professorId);
        return this.allocationRepository.findByProfessor(professor);
    }

    public void deleteById(Long allocationId) {
        Allocation allocation = this.findById(allocationId);
        this.allocationRepository.deleteById(allocation.getId());
    }

    public void deleteAll() {
        allocationRepository.deleteAllInBatch();
    }

    public Allocation findById(Long allocationId) {
        return this.allocationRepository
                .findById(allocationId)
                .orElseThrow(() -> new NotFoundException(String.format("Allocation not found with id :: %d", allocationId)));

    }

    public List<Allocation> findAll() {
        return this.allocationRepository.findAll();
    }

    private Allocation saveInternal(Allocation allocation) {
        Professor professor = this.professorService.findById(allocation.getProfessor().getId());
        Course course = this.courseService.findById(allocation.getCourse().getId());

        if (!isEndHourGreaterThanStartHour(allocation) || hasCollision(allocation)) {
            throw new RuntimeException();
        }

        if (Objects.isNull(allocation.getId())) {
            allocation.setProfessor(professor);
            allocation.setCourse(course);
            this.allocationRepository.save(allocation);
            return allocation;
        }

        allocation.setEndHour(allocation.getEndHour());
        allocation.setStartHour(allocation.getStartHour());
        allocation.setDayOfWeek(allocation.getDayOfWeek());
        allocation.setProfessor(professor);
        allocation.setCourse(course);
        this.allocationRepository.save(allocation);
        return allocation;
    }

    private boolean isEndHourGreaterThanStartHour(Allocation allocation) {
        return Objects.nonNull(allocation) && Objects.nonNull(allocation.getStartHour()) && Objects.nonNull(allocation.getEndHour())
                && allocation.getEndHour().compareTo(allocation.getStartHour()) > 0;
    }

    private boolean hasCollision(Allocation newAllocation) {
        List<Allocation> currentAllocations = allocationRepository.findByProfessor(newAllocation.getProfessor());
        return currentAllocations.stream()
                .anyMatch(currentAllocation -> hasCollision(currentAllocation, newAllocation));
    }

    private boolean hasCollision(Allocation currentAllocation, Allocation newAllocation) {
        return !currentAllocation.getId().equals(newAllocation.getId())
                && currentAllocation.getDayOfWeek() == newAllocation.getDayOfWeek()
                && currentAllocation.getStartHour().compareTo(newAllocation.getEndHour()) < 0
                && newAllocation.getStartHour().compareTo(currentAllocation.getEndHour()) < 0;
    }
}
