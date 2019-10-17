package com.lambdaschool.starthere.services;

//import com.lambdaschool.school.model.Course;
//import com.lambdaschool.school.view.CountStudentsInCourses;
import com.lambdaschool.starthere.models.Course;
import com.lambdaschool.starthere.view.CountStudentsInCourses;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;

public interface CourseService
{
    ArrayList<Course> findAll(Pageable pageable);

    ArrayList<CountStudentsInCourses> getCountStudentsInCourse();

    void delete(long id);

    Course findCourseById(long id);

    void save(Course course);
}
