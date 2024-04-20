package com.project.professor.allocation.pedrinho.repository;

import com.project.professor.allocation.pedrinho.entity.Allocation;
import com.project.professor.allocation.pedrinho.entity.Course;
import com.project.professor.allocation.pedrinho.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AllocationRepository extends JpaRepository<Allocation, Long> {
    List<Allocation> findByProfessor(Professor professor);
    List<Allocation> findByCourse(Course course);
}
