package com.examly.springapp;

import com.examly.springapp.controller.ApiController;
import com.examly.springapp.model.Course;
import com.examly.springapp.model.Lesson;
import com.examly.springapp.service.ApiService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class SpringappApplicationTests {

	@InjectMocks
	private ApiController apiController;

	@Mock
	private ApiService apiService;

	private MockMvc mockMvc;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(apiController).build();
	}

	@Test
	public void testCreateCourse() throws Exception {
		// Create a JSON object representing the request body
		String requestBody = "{\"courseId\": 1, \"title\": \"Test Course\", \"description\": \"Test Description\", \"instructorName\": \"Test Instructor\"}";

		// Perform the POST request
		mockMvc.perform(MockMvcRequestBuilders.post("/course/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
	}

	@Test
	public void testCreateLesson() throws Exception {
		// Create a JSON object representing the request body
		String requestBody = "{\"lessonId\": 1, \"title\": \"Test Lesson\", \"content\": \"Test Content\"}";
		int courseId = 1;

		when(apiService.createLesson(eq(courseId), any(Lesson.class))).thenReturn(true);

		// Perform the POST request
		mockMvc.perform(MockMvcRequestBuilders.post("/course/" + courseId + "/lesson")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
	}

	@Test
	public void testGetAllCoursesWithExpectedResponse() throws Exception {
		// Create a JSON object representing the expected response
		String expectedResponseBody = "{\"courseId\": 1, \"title\": \"Test Course\", \"description\": \"Test Description\", \"instructorName\": \"Test Instructor\"}";
		ObjectMapper objectMapper = new ObjectMapper();

		// Set up the mock service to return the expected JSON response
		when(apiService.getAllCourses()).thenReturn(Collections.singletonList(
				objectMapper.readValue(expectedResponseBody, Course.class)));

		// Perform the GET request and verify the response
		mockMvc.perform(MockMvcRequestBuilders.get("/course/"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testGetAllCourses() throws Exception {
		// Define the expected JSON response body as an array of Course objects
		String expectedResponseBody = "[{\"courseId\": 1, \"title\": \"Course 1\", \"description\": \"Description 1\", \"instructorName\": \"Instructor 1\"},"
				+ "{\"courseId\": 2, \"title\": \"Course 2\", \"description\": \"Description 2\", \"instructorName\": \"Instructor 2\"}]";

		// Create an instance of ObjectMapper
		ObjectMapper objectMapper = new ObjectMapper();

		// Create a list of Course objects to represent the expected result
		List<Course> expectedCourses = objectMapper.readValue(expectedResponseBody, new TypeReference<List<Course>>() {
		});

		// Set up the mock service to return the expected list of Course objects
		when(apiService.getAllCourses()).thenReturn(expectedCourses);

		// Perform the GET request
		mockMvc.perform(MockMvcRequestBuilders.get("/course/"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testGetLessonWithCourseDetails() throws Exception {
		// Define the expected JSON response body
		String expectedResponseBody = "{\"lessonId\": 1, \"title\": \"Test Lesson\", \"content\": \"Test Content\"}";

		// Create an instance of ObjectMapper
		ObjectMapper objectMapper = new ObjectMapper();

		// Set up the mock service to return the expected JSON response
		when(apiService.getLessonWithCourseDetails(eq(1)))
				.thenReturn(objectMapper.readValue(expectedResponseBody, Lesson.class));

		// Perform the GET request
		mockMvc.perform(MockMvcRequestBuilders.get("/course/lesson/1"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testCourseModelClassExists() {
		checkClassExists("com.examly.springapp.model.Course");
	}

	@Test
	public void testLessonModelClassExists() {
		checkClassExists("com.examly.springapp.model.Lesson");
	}

	@Test
	public void testCourceRepoClassExists() {
		checkClassExists("com.examly.springapp.Repository.CourseRepo");
	}

	public void testServiceClassExists() {
		checkClassExists("com.examly.springapp.controller.ApiController");
	}

	@Test
	public void testControllerClassExists() {
		checkClassExists("com.examly.springapp.service.ApiService");
	}

	// Add more test cases as needed

	// Helper methods for checking class and field existence
	private void checkClassExists(String className) {
		try {
			Class.forName(className);
		} catch (ClassNotFoundException e) {
			assertTrue(false, "Class " + className + " does not exist.");
		}
	}

	@Test

	public void controllerfolder() {
		String directoryPath = "src/main/java/com/examly/springapp/controller"; // Replace with the path to your
																				// directory
		File directory = new File(directoryPath);
		assertTrue(directory.exists() && directory.isDirectory());
	}

	@Test
	public void controllerfile() {
		String filePath = "src/main/java/com/examly/springapp/controller/ApiController.java";
		File file = new File(filePath);
		assertTrue(file.exists() && file.isFile());
	}

	@Test
	public void testModelFolder() {
		String directoryPath = "src/main/java/com/examly/springapp/model"; // Replace with the path to your directory
		File directory = new File(directoryPath);
		assertTrue(directory.exists() && directory.isDirectory());
	}

	@Test
	public void testModelFile() {
		String filePath = "src/main/java/com/examly/springapp/model/Course.java";
		File file = new File(filePath);
		assertTrue(file.exists() && file.isFile());
	}

	@Test
	public void testModelFile2() {
		String filePath = "src/main/java/com/examly/springapp/model/Lesson.java";
		File file = new File(filePath);
		assertTrue(file.exists() && file.isFile());
	}

	@Test
	public void testrepositoryfolder() {
		String directoryPath = "src/main/java/com/examly/springapp/repository"; // Replace with the path to your
																				// directory
		File directory = new File(directoryPath);
		assertTrue(directory.exists() && directory.isDirectory());
	}

	@Test

	public void testrepositoryFile() {

		String filePath = "src/main/java/com/examly/springapp/Repository/CourseRepo.java";
		// Replace with the path to your file
		File file = new File(filePath);
		assertTrue(file.exists() && file.isFile());
	}

	@Test

	public void testrepositoryFile2() {

		String filePath = "src/main/java/com/examly/springapp/Repository/LessonRepo.java";
		// Replace with the path to your file
		File file = new File(filePath);
		assertTrue(file.exists() && file.isFile());
	}

	@Test
	public void testServiceFolder() {
		String directoryPath = "src/main/java/com/examly/springapp/service";
		File directory = new File(directoryPath);

		assertTrue(directory.exists() && directory.isDirectory());
	}

	@Test

	public void testServieFile() {

		String filePath = "src/main/java/com/examly/springapp/service/ApiService.java";

		File file = new File(filePath);

		assertTrue(file.exists() && file.isFile());

	}

}