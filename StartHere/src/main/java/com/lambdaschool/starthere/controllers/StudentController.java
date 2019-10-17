package com.lambdaschool.starthere.controllers;
//
//import com.lambdaschool.school.models.Course;
//import com.lambdaschool.school.models.ErrorDetail;
//import com.lambdaschool.school.models.Student;
//import com.lambdaschool.school.service.StudentService;
import com.lambdaschool.starthere.models.Course;
import com.lambdaschool.starthere.models.ErrorDetail;
import com.lambdaschool.starthere.models.Student;
import com.lambdaschool.starthere.services.StudentService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController
{
    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentService studentService;

//    @Autowired
//    private CourseService courseService;

    // Please note there is no way to add students to course yet!

    @ApiOperation(value="return all students using paging and sorting", response = Course.class, responseContainer = "List")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve(0..n)"),//these are all just text fields printed. They can be anything
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). " +
                    "Default sort order is ascending. " +
                    "Multiple sort criteria are supported.")

    })
    @GetMapping(value = "/students", produces = {"application/json"})
    public ResponseEntity<?> listAllStudents(HttpServletRequest request, @PageableDefault(page=0, size=3)
            Pageable pageable)
    {
        logger.info(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");

        List<Student> myStudents = studentService.findAll(pageable);
        return new ResponseEntity<>(myStudents, HttpStatus.OK);
    }

    @ApiOperation(value = "Retrieves a student associated with the student id.", response = Student.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Student Found", response = Student.class),
            @ApiResponse(code = 404, message = "Student Not Found", response = ErrorDetail.class)})
    @GetMapping(value = "/Student/{StudentId}",
                produces = {"application/json"})
    public ResponseEntity<?> getStudentById(@ApiParam(value = "Student Id", required = true, example = "1")
            @PathVariable
                    Long StudentId, HttpServletRequest request)
    {
        logger.info(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");

        Student r = studentService.findStudentById(StudentId);
        return new ResponseEntity<>(r, HttpStatus.OK);
    }


    @ApiOperation(value = "Retrieves a student with a matching name.", response = Student.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Student Found", response = Student.class),
            @ApiResponse(code = 404, message = "Student Not Found", response = ErrorDetail.class)})
    @GetMapping(value = "/student/namelike/{name}",
                produces = {"application/json"})
    public ResponseEntity<?> getStudentByNameContaining(@ApiParam(value = "Student Id", required = true, example = "John")
            @PathVariable String name, HttpServletRequest request)
    {
        logger.info(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");

        List<Student> myStudents = studentService.findStudentByNameLike(name);
        return new ResponseEntity<>(myStudents, HttpStatus.OK);
    }


    @ApiOperation(value = "Creates a new Student", notes = "The newly created student id will be sent in the location header", response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Student Created Successfully", response = void.class),
            @ApiResponse(code = 500, message = "Error creating student", response = ErrorDetail.class)
    })
    @PostMapping(value = "/Student",
                 consumes = {"application/json"},
                 produces = {"application/json"})
    public ResponseEntity<?> addNewStudent(@Valid
                                           @RequestBody
                                                   Student newStudent, HttpServletRequest request) throws URISyntaxException
    {
        logger.info(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");

        newStudent = studentService.save(newStudent);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newStudentURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{Studentid}").buildAndExpand(newStudent.getStudid()).toUri();
        responseHeaders.setLocation(newStudentURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Enrolls a student into a preexisting course", notes = "A student must not already be enrolled for this endpoint to function properly", response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Student enrolled Successfully", response = void.class),
            @ApiResponse(code = 500, message = "Error enrolling student", response = ErrorDetail.class)
    })
    @GetMapping("/Student/{StudentId}/course/{CourseId}")
    public ResponseEntity<?> addStudentToCourse(@ApiParam(value = "Student Id", required = true, example = "1")
            @PathVariable long StudentId, @ApiParam(value = "Course Id", required = true, example = "1")
                                                @PathVariable long CourseId, HttpServletRequest request)
    {
        logger.info(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");
        studentService.insertStudentIntoCourse(StudentId, CourseId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @ApiOperation(value = "Updates student details", response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Student updated Successfully", response = void.class),
            @ApiResponse(code = 500, message = "Error updating student", response = ErrorDetail.class)
    })
    @PutMapping(value = "/Student/{Studentid}")
    public ResponseEntity<?> updateStudent(
            @RequestBody
                    Student updateStudent,
            @PathVariable
                    long Studentid, HttpServletRequest request)
    {
        logger.info(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");

        studentService.update(updateStudent, Studentid);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Delete a student", response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Student deleted successfully", response = void.class),
            @ApiResponse(code = 500, message = "Error deleting student", response = ErrorDetail.class)
    })
    @DeleteMapping("/Student/{Studentid}")
    public ResponseEntity<?> deleteStudentById(@ApiParam(value = "Student id", required = true, example = "1")
            @PathVariable
                    long Studentid, HttpServletRequest request)
    {

        logger.info(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");
        studentService.delete(Studentid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Unenroll a student from a course", notes = "The student must already be enrolled for this endpoint to function", response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Student unenrolled successfully", response = void.class),
            @ApiResponse(code = 500, message = "Error unenrolling student", response = ErrorDetail.class)
    })
    @DeleteMapping("/Student/{StudentId}/course/{CourseId}")
    public ResponseEntity<?> deleteStudentFromCourse(
            @ApiParam(value = "Student id", required = true, example = "1")@PathVariable long StudentId,
            @ApiParam(value = "Course id", required = true, example = "1")@PathVariable long CourseId, HttpServletRequest request)
    {
        logger.info(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");


        studentService.deleteStudentFromCourse(StudentId, CourseId);
        return new ResponseEntity<>("DELETED", HttpStatus.OK);
    }

}