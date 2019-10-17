package com.lambdaschool.starthere.services;

//import com.lambdaschool.school.repository.InstructorRepository;
import com.lambdaschool.starthere.models.Instructor;
import com.lambdaschool.starthere.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "instructorService")
public class InstructorServiceImpl implements InstructorService
{
    @Autowired
    private InstructorRepository instructrepos;

    @Override
    public void save(Instructor instructor) {
        instructrepos.save(instructor);
    }
}
