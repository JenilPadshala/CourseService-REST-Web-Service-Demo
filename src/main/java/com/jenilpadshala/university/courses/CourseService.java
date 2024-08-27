package com.jenilpadshala.university.courses;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/courses")
public class CourseService {

    private static final List<Course> courses = new ArrayList<>();

    static {
        courses.add(new Course("CS101", "Introduction to Computer Science", "Basics of computer science."));
        courses.add(new Course("MATH101", "Calculus I", "Introduction to calculus."));
        courses.add(new Course("ENG101", "English Literature", "Study of English literature."));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Course> getCourses() {
        return courses;
    }

    @GET
    @Path("/{courseId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Course getCourseById(@PathParam("courseId") String courseId) {
        return courses.stream()
                .filter(course -> course.getCourseId().equalsIgnoreCase(courseId))
                .findFirst()
                .orElse(null);
    }

    @GET
    @Path("/search/{courseTitle}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Course> searchCoursesByTitle(@PathParam("courseTitle") String courseTitle) {
        List<Course> result = new ArrayList<>();
        for (Course course : courses) {
            if (course.getCourseTitle().toLowerCase().contains(courseTitle.toLowerCase())) {
                result.add(course);
            }
        }
        return result;
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCourse(Course newCourse) {
        // Check for duplicate course ID
        for (Course course : courses) {
            if (course.getCourseId().equalsIgnoreCase(newCourse.getCourseId())) {
                return Response.status(Response.Status.CONFLICT)
                        .entity("Course with ID " + newCourse.getCourseId() + " already exists.")
                        .build();
            }
        }

        courses.add(newCourse);  // Add the new course to the list
        return Response.status(Response.Status.CREATED)
                .entity(newCourse)
                .build();  // Return a 201 Created response with the new course
    }
    
    @DELETE
    @Path("/{courseId}")
    @Produces(MediaType.APPLICATION_JSON)  
    public Response deleteCourse(@PathParam("courseId") String courseId) {
        Course courseToDelete = courses.stream()
                .filter(course -> course.getCourseId().equalsIgnoreCase(courseId))
                .findFirst()
                .orElse(null);

        if (courseToDelete != null) {
            courses.remove(courseToDelete);
            return Response.status(Response.Status.OK)
                    .entity("Course with ID " + courseId + " has been deleted.")
                    .build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Course with ID " + courseId + " not found.")
                    .build();
        }
    }
    
    @PUT
    @Path("/{courseId}")
    @Consumes(MediaType.APPLICATION_JSON)  // Set to consume JSON
    @Produces(MediaType.APPLICATION_JSON)  // Set to produce JSON
    public Response updateCourseTitle(@PathParam("courseId") String courseId, Course updatedCourse) {
        Course courseToUpdate = courses.stream()
                .filter(course -> course.getCourseId().equalsIgnoreCase(courseId))
                .findFirst()
                .orElse(null);

        if (courseToUpdate != null) {
            courseToUpdate.setCourseTitle(updatedCourse.getCourseTitle());
            return Response.status(Response.Status.OK)
                    .entity(courseToUpdate)
                    .build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Course with ID " + courseId + " not found.")
                    .build();
        }
    }
}
