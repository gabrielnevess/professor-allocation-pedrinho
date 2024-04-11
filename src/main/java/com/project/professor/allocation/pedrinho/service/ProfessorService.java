package com.project.professor.allocation.pedrinho.service;

import com.project.professor.allocation.pedrinho.entity.Department;
import com.project.professor.allocation.pedrinho.entity.Professor;
import com.project.professor.allocation.pedrinho.exception.ProfessorAllocationException;
import com.project.professor.allocation.pedrinho.repository.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProfessorService {
    private final ProfessorRepository professorRepository;
    private final DepartmentService departmentService;

    public Professor save(Professor professor) throws ProfessorAllocationException {
        return this.saveInternal(professor);
    }

    public Professor update(Professor professor, Long professorId) throws ProfessorAllocationException {
        professor.setId(professorId);
        return this.saveInternal(professor);
    }

    public List<Professor> findByDepartment(Long departmentId) {
        Department department = new Department();
        department.setId(departmentId);
        return professorRepository.findByDepartment(department);
    }

    public void deleteById(Long professorId) throws ProfessorAllocationException {
        Professor professor = this.findById(professorId);
        this.professorRepository.deleteById(professor.getId());
    }

    public Professor findById(Long professor) throws ProfessorAllocationException {
        return this.professorRepository
                .findById(professor)
                .orElseThrow(() -> new ProfessorAllocationException(HttpStatus.NOT_FOUND, String.format("Professor not found with id :: %d", professor)));
    }

    public List<Professor> findAll(String name) {
        if (Objects.nonNull(name)) {
            return this.professorRepository.findByNameContainingIgnoreCase(name);
        } else {
            return this.professorRepository.findAll();
        }
    }

    private Professor saveInternal(Professor professor) throws ProfessorAllocationException {
        Department department = departmentService.findById(professor.getDepartment().getId());

        if (Objects.isNull(professor.getId())) {
            professor.setDepartment(department);
            this.professorRepository.save(professor);
            return professor;
        } else {
            Professor prof = this.findById(professor.getId());
            prof.setName(professor.getName());
            prof.setDepartment(department);
            this.professorRepository.save(prof);
            return prof;
        }
    }
}
