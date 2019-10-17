package com.lambdaschool.starthere.services;

//import com.lambdaschool.school.exceptions.ResourceNotFoundException;
//import com.lambdaschool.school.model.Course;
//import com.lambdaschool.school.model.Student;
//import com.lambdaschool.school.repository.CourseRepository;
//import com.lambdaschool.school.repository.StudentRepository;
import com.lambdaschool.starthere.exceptions.ResourceNotFoundException;
import com.lambdaschool.starthere.models.Course;
import com.lambdaschool.starthere.models.Student;
import com.lambdaschool.starthere.repository.CourseRepository;
import com.lambdaschool.starthere.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

//import javax.persistence.ResourceNotFoundException;

@Service(value = "studentService")
public class StudentServiceImpl implements StudentService
{
    @Autowired
    private StudentRepository studrepos;

    @Autowired
    private CourseRepository courserepos;

    @Override
    public List<Student> findAll(Pageable pageable)
    {
        List<Student> list = new ArrayList<>();
        studrepos.findAll(pageable).iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Student findStudentById(long id) throws ResourceNotFoundException
    {
        return studrepos.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Long.toString(id)));
    }

    @Override
    public List<Student> findStudentByNameLike(String name)
    {
        List<Student> list = new ArrayList<>();
        studrepos.findByStudnameContainingIgnoreCase(name).iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public void delete(long id) throws ResourceNotFoundException
    {
        if (studrepos.findById(id).isPresent())
        {
            studrepos.deleteById(id);
        } else
        {
            throw new ResourceNotFoundException(Long.toString(id));
        }
    }

    @Transactional
    @Override
    public Student save(Student student)
    {
        Student newStudent = new Student();

        newStudent.setStudname(student.getStudname());

        return studrepos.save(newStudent);
    }

    @Override
    public Student update(Student student, long id)
    {
        Student currentStudent = studrepos.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Long.toString(id)));

        if (student.getStudname() != null)
        {
            currentStudent.setStudname(student.getStudname());
        }

        return studrepos.save(currentStudent);
    }

    public void insertStudentIntoCourse(long studid, long courseid)
    {
        Student currentStudent = studrepos.findById(studid)
                .orElseThrow(() -> new ResourceNotFoundException(Long.toString(studid)));

        Course currentCourse = courserepos.findById(courseid)
                .orElseThrow(() -> new ResourceNotFoundException(Long.toString(studid)));

        for(Course c: currentStudent.getCourses())
        {
            if(c.getCourseid()== currentCourse.getCourseid())
            {

                throw new ResourceNotFoundException("Student already enrolled");
            }
        }
        studrepos.insertStudentIntoCourse(studid, courseid);
    }

    public void deleteStudentFromCourse(long studid, long courseid)
    {
        Student currentStudent = studrepos.findById(studid)
                .orElseThrow(() -> new ResourceNotFoundException(Long.toString(studid)));

        Course currentCourse = courserepos.findById(courseid)
                .orElseThrow(() -> new ResourceNotFoundException(Long.toString(studid)));

        for(Course c: currentStudent.getCourses())
        {
            if(c.getCourseid()== currentCourse.getCourseid())
            {
                studrepos.deleteStudentFromCourse(studid, courseid);
                return;
            }
        }
        throw new ResourceNotFoundException("Student Not enrolled");

    }
}
