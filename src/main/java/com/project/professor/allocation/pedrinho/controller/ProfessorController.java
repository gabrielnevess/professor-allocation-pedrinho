package com.project.professor.allocation.pedrinho.controller;

import com.project.professor.allocation.pedrinho.entity.Professor;
import com.project.professor.allocation.pedrinho.service.ProfessorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Professors")
@RestController
@RequestMapping("/professors")
@RequiredArgsConstructor
public class ProfessorController {
    private final ProfessorService professorService;

    @Operation(summary = "Find all professors")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<Professor> findAll(@RequestParam(name = "name", required = false) String name) {
        return this.professorService.findAll(name);
    }

    @Operation(summary = "Find a professor")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    @GetMapping(path = "/{professor_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Professor findById(@PathVariable(name = "professor_id") Long id) {
        return this.professorService.findById(id);
    }

    @Operation(summary = "Find all professor by department")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    @GetMapping(path = "/department/{department_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<Professor> findByDepartment(@PathVariable(name = "department_id") Long id) {
        return this.professorService.findByDepartment(id);
    }

    @Operation(summary = "Save a professor")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Professor save(@RequestBody Professor professor) {
        return this.professorService.save(professor);
    }

    @Operation(summary = "Update a professor")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    @PutMapping(path = "/{professor_id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Professor update(@PathVariable(name = "professor_id") Long id,
                            @RequestBody Professor professor) {
        return this.professorService.update(professor, id);
    }

    @Operation(summary = "Delete a professor")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    })
    @DeleteMapping(path = "/{professor_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable(name = "professor_id") Long id) {
        this.professorService.deleteById(id);
    }

    @Operation(summary = "Delete all professors")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No Content"),
    })
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById() {
        this.professorService.deleteAll();
    }
}