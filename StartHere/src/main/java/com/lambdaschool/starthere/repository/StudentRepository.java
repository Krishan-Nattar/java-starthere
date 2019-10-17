package com.lambdaschool.starthere.repository;

//import com.lambdaschool.school.model.Student;
import com.lambdaschool.starthere.models.Student;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface StudentRepository extends PagingAndSortingRepository<Student, Long>
{
    List<Student> findByStudnameContainingIgnoreCase(String name);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO studcourses (studid, courseid) VALUES (:studid, :courseid)", nativeQuery = true)
    void insertStudentIntoCourse(long studid, long courseid);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM studcourses WHERE courseid = :courseid AND studid = :studid", nativeQuery = true)
    void deleteStudentFromCourse(long studid, long courseid);

}
