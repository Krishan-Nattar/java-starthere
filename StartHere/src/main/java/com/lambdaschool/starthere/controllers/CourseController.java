package com.lambdaschool.starthere.controllers;

//import com.lambdaschool.school.model.Course;
//import com.lambdaschool.school.model.ErrorDetail;
//import com.lambdaschool.school.service.CourseService;
//import com.lambdaschool.school.view.CountStudentsInCourses;
import com.lambdaschool.starthere.models.Course;
import com.lambdaschool.starthere.models.ErrorDetail;
import com.lambdaschool.starthere.services.CourseService;
import com.lambdaschool.starthere.view.CountStudentsInCourses;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.ArrayList;

@RestController
@RequestMapping(value = "/courses")
public class CourseController
{

    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    private CourseService courseService;

    @ApiOperation(value="return all courses using paging and sorting", response = Course.class, responseContainer = "List")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve(0..n)"),//these are all just text fields printed. They can be anything
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). " +
                    "Default sort order is ascending. " +
                    "Multiple sort criteria are supported.")

    })
    @GetMapping(value = "/courses", produces = {"application/json"})
    public ResponseEntity<?> listAllCourses(@PageableDefault(page = 0,size=3) Pageable pageable, HttpServletRequest request)
    {
        logger.info(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");

        ArrayList<Course> myCourses = courseService.findAll(pageable);
        return new ResponseEntity<>(myCourses, HttpStatus.OK);
    }

    @ApiOperation(value="returns a count of students in courses", response = CountStudentsInCourses.class, responseContainer = "List")
    @GetMapping(value = "/studcount", produces = {"application/json"})
    public ResponseEntity<?> getCountStudentsInCourses(HttpServletRequest request)
    {
        logger.info(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");

        ArrayList<CountStudentsInCourses> countList = courseService.getCountStudentsInCourse();
        return new ResponseEntity<>(countList, HttpStatus.OK);
    }

    @ApiOperation(value = "Delete a course", notes = "The course id entered will be deleted", response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Course deleted successfully", response = void.class),
            @ApiResponse(code = 500, message = "Error deleting course", response = ErrorDetail.class)
    })
    @DeleteMapping("/courses/{courseid}")
    public ResponseEntity<?> deleteCourseById(@ApiParam(value = "Course Id", required = true, example = "1")
            @PathVariable long courseid, HttpServletRequest request)
    {
        logger.info(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");

        courseService.delete(courseid);
        return new ResponseEntity<>(HttpStatus.OK);
    }









    @ApiOperation(value = "Add a new course", response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Course Created Successfully", response = void.class),
            @ApiResponse(code = 500, message = "Error creating Course", response = ErrorDetail.class)
    })
    @PostMapping(value = "/course/add",
            consumes = {"application/json"},
            produces = {"application/json"})
    public ResponseEntity<?> addNewCourse(@Valid
                                           @RequestBody
                                                   Course newCourse, HttpServletRequest request) throws URISyntaxException
    {
        logger.info(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");

        courseService.save(newCourse);

//        The course data will contain course name and an instructor object (of an already existing instructor)
//
//        No students will be added to the course via this method

        return new ResponseEntity<>(HttpStatus.CREATED);
    }











}
