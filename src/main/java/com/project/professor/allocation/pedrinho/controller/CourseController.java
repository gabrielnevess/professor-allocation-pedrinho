package com.project.professor.allocation.pedrinho.controller;

import com.project.professor.allocation.pedrinho.entity.Course;
import com.project.professor.allocation.pedrinho.service.CourseService;
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

@Tag(name = "Courses")
@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @Operation(summary = "Find all courses")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Course>> findAll(@RequestParam(name = "name", required = false) String name) {
        return new ResponseEntity<>(this.courseService.findAll(name), HttpStatus.OK);
    }

    @Operation(summary = "Find a course")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    @GetMapping(path = "/{course_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Course> findById(@PathVariable(name = "course_id") Long id) {
        return new ResponseEntity<>(this.courseService.findById(id), HttpStatus.OK);
    }

    @Operation(summary = "Save a course")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Course> save(@RequestBody Course course) {
    	return new ResponseEntity<>(this.courseService.save(course), HttpStatus.OK);
    }

    @Operation(summary = "Update a course")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    @PutMapping(path = "/{course_id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Course> update(@PathVariable(name = "course_id") Long id,
                                         @RequestBody Course course) {
    	return new ResponseEntity<>(this.courseService.update(course, id), HttpStatus.OK);
    }

    @Operation(summary = "Delete a course")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    })
    @DeleteMapping(path = "/{course_id}")
    public ResponseEntity<Void> deleteById(@PathVariable(name = "course_id") Long id) {
        this.courseService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Delete all courses")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No Content"),
    })
    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        this.courseService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}