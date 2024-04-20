package com.project.professor.allocation.pedrinho.controller;

import com.project.professor.allocation.pedrinho.entity.Allocation;
import com.project.professor.allocation.pedrinho.service.AllocationService;
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

@Tag(name = "Allocations")
@RestController
@RequestMapping("/allocations")
@RequiredArgsConstructor
public class AllocationController {
    private final AllocationService allocationService;

    @Operation(summary = "Find all allocations")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<Allocation> findAll() {
        return this.allocationService.findAll();
    }

    @Operation(summary = "Find a allocation")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    @GetMapping(path = "/{allocation_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Allocation findById(@PathVariable(name = "allocation_id") Long id) {
        return this.allocationService.findById(id);
    }

    @Operation(summary = "Find allocations by professor")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    })
    @GetMapping(path = "/professor/{professor_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<Allocation> findByProfessor(@PathVariable(name = "professor_id") Long id) {
        return this.allocationService.findByProfessor(id);
    }

    @Operation(summary = "Find allocations by course")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    })
    @GetMapping(path = "/course/{course_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<Allocation> findByCourse(@PathVariable(name = "course_id") Long id) {
        return this.allocationService.findByCourse(id);
    }

    @Operation(summary = "Save a allocation")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Allocation save(@RequestBody Allocation allocation) {
    	return this.allocationService.save(allocation);
    }

    @Operation(summary = "Update a allocation")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    @PutMapping(path = "/{allocation_id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Allocation update(@PathVariable(name = "allocation_id") Long id,
                                             @RequestBody Allocation allocation) {
    	return this.allocationService.update(allocation, id);
    }

    @Operation(summary = "Delete a allocation")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    })
    @DeleteMapping(path = "/{allocation_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable(name = "allocation_id") Long id) {
        this.allocationService.deleteById(id);
    }

    @Operation(summary = "Delete all allocations")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No Content"),
    })
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAll() {
        this.allocationService.deleteAll();
    }
}