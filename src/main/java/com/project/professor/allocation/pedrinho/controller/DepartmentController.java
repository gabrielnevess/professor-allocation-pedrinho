package com.project.professor.allocation.pedrinho.controller;

import com.project.professor.allocation.pedrinho.entity.Department;
import com.project.professor.allocation.pedrinho.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Departments")
@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    @Operation(summary = "Find all departments")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Department>> findAll(@RequestParam(name = "name", required = false) String name) {
        return new ResponseEntity<>(this.departmentService.findAll(name), HttpStatus.OK);
    }

    @Operation(summary = "Find a department")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    @GetMapping(path = "/{department_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Department> findById(@PathVariable(name = "department_id") Long id) {
        return new ResponseEntity<>(this.departmentService.findById(id), HttpStatus.OK);
    }

    @Operation(summary = "Save a department")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Department> save(@RequestBody Department department) {
    	return new ResponseEntity<>(this.departmentService.save(department), HttpStatus.CREATED);
    }

    @Operation(summary = "Update a department")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    @PutMapping(path = "/{department_id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Department> update(@PathVariable(name = "department_id") Long id,
                                             @RequestBody Department department) {
    	return new ResponseEntity<>(this.departmentService.update(department, id), HttpStatus.OK);
    }

    @Operation(summary = "Delete a department")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    })
    @DeleteMapping(path = "/{department_id}")
    public ResponseEntity<Void> deleteById(@PathVariable(name = "department_id") Long id) {
        this.departmentService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Delete all departments")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No Content"),
    })
    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        this.departmentService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
