package com.lambdaschool.starthere;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import com.lambdaschool.starthere.models.*;
import com.lambdaschool.starthere.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Locale;

@Transactional
@Component
public class SeedData implements CommandLineRunner
{
    @Autowired
    RoleService roleService;

    @Autowired
    UserService userService;

    @Autowired
    InstructorService instructorService;

    @Autowired
    CourseService courseService;

    @Autowired
    StudentService studentService;


    @Override
    public void run(String[] args) throws Exception
    {
        Instructor i1 = new Instructor("Sally");
        Instructor i2 = new Instructor("Lucy");
        Instructor i3 = new Instructor("Charlie");

        instructorService.save(i1);

        instructorService.save(i2);

        instructorService.save(i3);

        Course c1 = new Course("Data Science", i1);
        courseService.save(c1);
        Course c2 = new Course("Javascript", i1);
        courseService.save(c2);
        Course c3 = new Course("Node.js", i1);
        courseService.save(c3);
        Course c4 = new Course("Java Back End", i2);
        courseService.save(c4);
        Course c5 = new Course("Mobile IOS", i2);
        courseService.save(c5);
        Course c6 = new Course("Mobile Android", i3);
        courseService.save(c6);

        Student s1 = new Student("John");
        Student s2 = new Student("Julian");
        Student s3 = new Student("Mary");
        Student s4 = new Student("James");
        Student s5 = new Student("Tyler");
        Student s6 = new Student("Kim");
        Student s7 = new Student("Juan");
        Student s8 = new Student("Robby");
        Student s9 = new Student("Roberto");
        Student s10 = new Student("Bob");
        Student s11 = new Student("Liz");
        Student s12 = new Student("June");
        Student s13 = new Student("April");

        studentService.save(s1);
        studentService.save(s2);
        studentService.save(s3);
        studentService.save(s4);
        studentService.save(s5);
        studentService.save(s6);
        studentService.save(s7);
        studentService.save(s8);
        studentService.save(s9);
        studentService.save(s10);
        studentService.save(s11);
        studentService.save(s12);
        studentService.save(s13);

        studentService.insertStudentIntoCourse(10, 4);
        studentService.insertStudentIntoCourse(10, 7);
        studentService.insertStudentIntoCourse(11, 5);
        studentService.insertStudentIntoCourse(12, 6);
        studentService.insertStudentIntoCourse(12, 4);
        studentService.insertStudentIntoCourse(12, 9);


        Role r1 = new Role("admin");
        Role r2 = new Role("user");
        Role r3 = new Role("data");

        roleService.save(r1);
        roleService.save(r2);
        roleService.save(r3);

        // admin, data, user
        ArrayList<UserRoles> admins = new ArrayList<>();
        admins.add(new UserRoles(new User(),
                                 r1));
        admins.add(new UserRoles(new User(),
                                 r2));
        admins.add(new UserRoles(new User(),
                                 r3));
        User u1 = new User("admin",
                           "password",
                           "admin@lambdaschool.local",
                           admins);
        u1.getUseremails()
          .add(new Useremail(u1,
                             "admin@email.local"));
        u1.getUseremails()
          .add(new Useremail(u1,
                             "admin@mymail.local"));

        userService.save(u1);

        // data, user
        ArrayList<UserRoles> datas = new ArrayList<>();
        datas.add(new UserRoles(new User(),
                                r3));
        datas.add(new UserRoles(new User(),
                                r2));
        User u2 = new User("cinnamon",
                           "1234567",
                           "cinnamon@lambdaschool.local",
                           datas);
        u2.getUseremails()
          .add(new Useremail(u2,
                             "cinnamon@mymail.local"));
        u2.getUseremails()
          .add(new Useremail(u2,
                             "hops@mymail.local"));
        u2.getUseremails()
          .add(new Useremail(u2,
                             "bunny@email.local"));
        userService.save(u2);

        // user
        ArrayList<UserRoles> users = new ArrayList<>();
        users.add(new UserRoles(new User(),
                                r2));
        User u3 = new User("barnbarn",
                           "ILuvM4th!",
                           "barnbarn@lambdaschool.local",
                           users);
        u3.getUseremails()
          .add(new Useremail(u3,
                             "barnbarn@email.local"));
        userService.save(u3);

        users = new ArrayList<>();
        users.add(new UserRoles(new User(),
                                r2));
        User u4 = new User("puttat",
                           "password",
                           "puttat@school.lambda",
                           users);
        userService.save(u4);

        users = new ArrayList<>();
        users.add(new UserRoles(new User(),
                                r2));
        User u5 = new User("misskitty",
                           "password",
                           "misskitty@school.lambda",
                           users);
        userService.save(u5);



        // using JavaFaker create a bunch of regular users
        // https://www.baeldung.com/java-faker
        // https://www.baeldung.com/regular-expressions-java

        FakeValuesService fakeValuesService = new FakeValuesService(new Locale("en-US"),
                                                                    new RandomService());
        Faker nameFaker = new Faker(new Locale("en-US"));

        for (int i = 0; i < 100; i++)
        {
            new User();
            User fakeUser;

            users = new ArrayList<>();
            users.add(new UserRoles(new User(),
                                    r2));
            fakeUser = new User(nameFaker.name()
                                         .username(),
                                "password",
                                nameFaker.internet()
                                         .emailAddress(),
                                users);
            fakeUser.getUseremails()
                    .add(new Useremail(fakeUser,
                                       fakeValuesService.bothify("????##@gmail.com")));
            userService.save(fakeUser);
        }
    }
}