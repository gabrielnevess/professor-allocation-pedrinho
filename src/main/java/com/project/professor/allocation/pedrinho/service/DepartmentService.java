package com.project.professor.allocation.pedrinho.service;

import com.project.professor.allocation.pedrinho.entity.Department;
import com.project.professor.allocation.pedrinho.exception.ProfessorAllocationException;
import com.project.professor.allocation.pedrinho.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public Department save(Department department) throws ProfessorAllocationException {
        return this.saveInternal(department);
    }

    public Department update(Department department, Long departmentId) throws ProfessorAllocationException {
        department.setId(departmentId);
        return this.saveInternal(department);
    }

    public void deleteById(Long departmentId) throws ProfessorAllocationException {
        Department department = this.findById(departmentId);
        this.departmentRepository.deleteById(department.getId());
    }

    public Department findById(Long department) throws ProfessorAllocationException {
        return this.departmentRepository
                .findById(department)
                .orElseThrow(() -> new ProfessorAllocationException(HttpStatus.NOT_FOUND, String.format("Departamento não encontrado com o id :: %d", department)));

    }

    public List<Department> findAll(String name) {
        if(Objects.nonNull(name)) {
            return this.departmentRepository.findByNameContainingIgnoreCase(name);
        } else {
            return this.departmentRepository.findAll();
        }
    }

    private Department saveInternal(Department department) throws ProfessorAllocationException {
        if (Objects.isNull(department.getId())) {
            return this.departmentRepository.save(department);
        } else {
            Department dpt = this.findById(department.getId());
            dpt.setName(department.getName());
            return this.departmentRepository.save(dpt);
        }
    }
}