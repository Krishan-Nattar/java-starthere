package com.lambdaschool.starthere.services;

//import com.lambdaschool.school.model.Student;
import com.lambdaschool.starthere.models.Student;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudentService
{
    List<Student> findAll(Pageable pageable);

    Student findStudentById(long id);

    List<Student> findStudentByNameLike(String name);

    void delete(long id);

    Student save(Student student);

    Student update(Student student, long id);

    void insertStudentIntoCourse(long studid, long courseid);

    void deleteStudentFromCourse(long studid, long courseid);
}
