package com.project.professor.allocation.pedrinho.service;

import com.project.professor.allocation.pedrinho.entity.Course;
import com.project.professor.allocation.pedrinho.exception.ProfessorAllocationException;
import com.project.professor.allocation.pedrinho.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;

    public Course save(Course course) throws ProfessorAllocationException {
        return this.saveInternal(course);
    }

    public Course update(Course course, Long courseId) throws ProfessorAllocationException {
        course.setId(courseId);
        return this.saveInternal(course);
    }

    public void deleteById(Long courseId) throws ProfessorAllocationException {
        Course course = this.findById(courseId);
        this.courseRepository.deleteById(course.getId());
    }

    public Course findById(Long course) throws ProfessorAllocationException {
        return this.courseRepository
                .findById(course)
                .orElseThrow(() -> new ProfessorAllocationException(HttpStatus.NOT_FOUND, String.format("Course not found with id :: %d", course)));

    }

    public List<Course> findAll(String name) {
        if (Objects.nonNull(name)) {
            return this.courseRepository.findByNameContainingIgnoreCase(name);
        } else {
            return this.courseRepository.findAll();
        }
    }

    private Course saveInternal(Course course) throws ProfessorAllocationException {
        if (Objects.isNull(course.getId())) {
            return this.courseRepository.save(course);
        } else {
            Course cs = this.findById(course.getId());
            cs.setName(course.getName());
            return this.courseRepository.save(cs);
        }
    }
}
