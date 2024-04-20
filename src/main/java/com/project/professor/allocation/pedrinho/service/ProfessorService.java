package com.project.professor.allocation.pedrinho.service;

import com.project.professor.allocation.pedrinho.entity.Department;
import com.project.professor.allocation.pedrinho.entity.Professor;
import com.project.professor.allocation.pedrinho.exception.NotFoundException;
import com.project.professor.allocation.pedrinho.repository.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProfessorService {
    private final ProfessorRepository professorRepository;
    private final DepartmentService departmentService;

    public Professor save(Professor professor) {
        return this.saveInternal(professor);
    }

    public Professor update(Professor professor, Long professorId) {
        if (Objects.isNull(professorId) || !this.professorRepository.existsById(professorId)) {
            throw new NotFoundException(String.format("Professor not found with id :: %d", professorId));
        }

        professor.setId(professorId);
        return this.saveInternal(professor);
    }

    public List<Professor> findByDepartment(Long departmentId) {
        Department department = new Department();
        department.setId(departmentId);
        return this.professorRepository.findByDepartment(department);
    }

    public void deleteById(Long professorId) {
        Professor professor = this.findById(professorId);
        this.professorRepository.deleteById(professor.getId());
    }

    public void deleteAll() {
        this.professorRepository.deleteAllInBatch();
    }

    public Professor findById(Long professor) {
        return this.professorRepository
                .findById(professor)
                .orElseThrow(() -> new NotFoundException(String.format("Professor not found with id :: %d", professor)));
    }

    public List<Professor> findAll(String name) {
        if (Objects.nonNull(name)) {
            return this.professorRepository.findByNameContainingIgnoreCase(name);
        }
        return this.professorRepository.findAll();
    }

    private Professor saveInternal(Professor professor) {
        Department department = departmentService.findById(professor.getDepartment().getId());

        if (Objects.isNull(professor.getId())) {
            professor.setDepartment(department);
            this.professorRepository.save(professor);
            return professor;
        }

        professor.setName(professor.getName());
        professor.setCpf(professor.getCpf());
        professor.setDepartment(department);
        this.professorRepository.save(professor);
        return professor;
    }
}
