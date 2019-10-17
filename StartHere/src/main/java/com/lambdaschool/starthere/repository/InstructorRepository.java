package com.lambdaschool.starthere.repository;

//import com.lambdaschool.school.model.Instructor;
import com.lambdaschool.starthere.models.Instructor;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface InstructorRepository extends CrudRepository<Instructor, Long>
{
    ArrayList<Instructor> findInstructorsByInstructnameEquals(String name);
}
