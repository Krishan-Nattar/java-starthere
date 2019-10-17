package com.lambdaschool.starthere.services;

//import com.lambdaschool.school.exceptions.ResourceNotFoundException;
//import com.lambdaschool.school.model.Course;
//import com.lambdaschool.school.repository.CourseRepository;
//import com.lambdaschool.school.view.CountStudentsInCourses;
import com.lambdaschool.starthere.exceptions.ResourceNotFoundException;
import com.lambdaschool.starthere.models.Course;
import com.lambdaschool.starthere.repository.CourseRepository;
import com.lambdaschool.starthere.view.CountStudentsInCourses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

//import javax.persistence.ResourceNotFoundException;

@Service(value = "courseService")
public class CourseServiceImpl implements CourseService
{
    @Autowired
    private CourseRepository courserepos;

    @Override
    public ArrayList<Course> findAll(Pageable pageable)
    {
        ArrayList<Course> list = new ArrayList<>();
        courserepos.findAll(pageable).iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public ArrayList<CountStudentsInCourses> getCountStudentsInCourse()
    {
//        return courserepos.getCountStudentsInCourse();
        return courserepos.getCountStudentsInCourse();
    }

    @Transactional
    @Override
    public void delete(long id) throws ResourceNotFoundException
    {
        if (courserepos.findById(id).isPresent())
        {
            courserepos.deleteCourseFromStudcourses(id);
            courserepos.deleteById(id);
        } else
        {
            throw new ResourceNotFoundException(Long.toString(id));
        }
    }

    @Override
    public Course findCourseById(long id) {
        return courserepos.findCourseByCourseid(id);
    }

    @Override
    public void save(Course course) {
        courserepos.save(course);
    }
}
