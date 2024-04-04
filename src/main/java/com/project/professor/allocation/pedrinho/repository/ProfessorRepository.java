package com.project.professor.allocation.pedrinho.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.professor.allocation.pedrinho.entity.Department;
import com.project.professor.allocation.pedrinho.entity.Professor;

import java.util.List;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    List<Professor> findByNameContainingIgnoreCase(String name);
    List<Professor> findByDepartment(Department department);
}
