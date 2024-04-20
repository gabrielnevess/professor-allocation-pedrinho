package com.project.professor.allocation.pedrinho.service;

import com.project.professor.allocation.pedrinho.entity.Course;
import com.project.professor.allocation.pedrinho.exception.NotFoundException;
import com.project.professor.allocation.pedrinho.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;

    public Course save(Course course) {
        return this.saveInternal(course);
    }

    public Course update(Course course, Long courseId) {
        if (Objects.isNull(courseId) || !this.courseRepository.existsById(courseId)) {
            throw new NotFoundException(String.format("Course not found with id :: %d", courseId));
        }

        course.setId(courseId);
        return this.saveInternal(course);
    }

    public void deleteById(Long courseId) {
        Course course = this.findById(courseId);
        this.courseRepository.deleteById(course.getId());
    }

    public void deleteAll() {
        this.courseRepository.deleteAllInBatch();
    }

    public Course findById(Long course) {
        return this.courseRepository
                .findById(course)
                .orElseThrow(() -> new NotFoundException(String.format("Course not found with id :: %d", course)));

    }

    public List<Course> findAll(String name) {
        if (Objects.nonNull(name)) {
            return this.courseRepository.findByNameContainingIgnoreCase(name);
        }
        return this.courseRepository.findAll();
    }

    private Course saveInternal(Course course) {
        if (Objects.isNull(course.getId())) {
            return this.courseRepository.save(course);
        }

        Course cs = this.findById(course.getId());
        cs.setName(course.getName());
        return this.courseRepository.save(cs);
    }
}
