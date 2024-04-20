package com.project.professor.allocation.pedrinho.service;

import com.project.professor.allocation.pedrinho.entity.Department;
import com.project.professor.allocation.pedrinho.exception.NotFoundException;
import com.project.professor.allocation.pedrinho.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public Department save(Department department) {
        return this.saveInternal(department);
    }

    public Department update(Department department, Long departmentId) {
    	if (Objects.isNull(departmentId) || !departmentRepository.existsById(departmentId)) {
    		throw new NotFoundException(String.format("Department not found with id :: %d", departmentId));
    	}

        department.setId(departmentId);
        return this.saveInternal(department);
    }

    public void deleteById(Long departmentId) {
        Department department = this.findById(departmentId);
        this.departmentRepository.deleteById(department.getId());
    }

    public void deleteAll() {
        this.departmentRepository.deleteAllInBatch();
    }

    public Department findById(Long department) {
        return this.departmentRepository
                .findById(department)
                .orElseThrow(() -> new NotFoundException(String.format("Department not found with id :: %d", department)));

    }

    public List<Department> findAll(String name) {
        if (Objects.nonNull(name)) {
            return this.departmentRepository.findByNameContainingIgnoreCase(name);
        } else {
            return this.departmentRepository.findAll();
        }
    }

    private Department saveInternal(Department department) {
        if (Objects.isNull(department.getId())) {
            return this.departmentRepository.save(department);
        } else {
            Department dpt = this.findById(department.getId());
            dpt.setName(department.getName());
            return this.departmentRepository.save(dpt);
        }
    }
}