package steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.ErrorObject;
import models.ErrorObjectId;
import models.Student;
import org.json.simple.JSONObject;
import org.testng.Assert;
import utils.Endpoints;

import static utils.JsonInputParser.data;

import static io.restassured.RestAssured.given;

public class MyStepdefs {
    ObjectMapper objectMapper = new ObjectMapper();
    Student stud;
    Response response;

    @Given("the body")
    public void theBody() {

        JSONObject testData = (JSONObject) data.get("createStudent");

        stud = new Student((long) testData.get("id"), (String) testData.get("name"),
                (long) testData.get("age"), (String) testData.get("email"));
    }

    @When("creating the student data")
    public void creatingTheStudentData() {
        response = given()
                .body(stud)
                .when().post(Endpoints.STUDENT_ENDPOINT)
                .then().statusCode(200).extract().response();
    }
    @Then("student data is created")
    public void studentDataIsCreated() throws JsonProcessingException {
        Student responseStud = objectMapper.readValue(response.asString(), Student.class);
        Assert.assertEquals(stud.getEmail(), responseStud.getEmail());
        Assert.assertEquals(stud.getName(), responseStud.getName());
        Assert.assertEquals(stud.getId(), responseStud.getId());
        Assert.assertEquals(stud.getAge(), responseStud.getAge());
    }
    JsonPath jsonPath;
    @Given("the request")
    public void theRequest() {
        Response response = given()
                .when().get(Endpoints.STUDENT_ENDPOINT)
                .then().statusCode(200).extract().response();
    }

    @Then("getting student data")
    public void gettingStudentData() {
    }

    Student student;
    Response putResponse;
    int studId;
    @Given("creating student data")
    public void creatingStudentData() throws JsonProcessingException {
        JSONObject testData = (JSONObject) data.get("createStudent");
        Student student = new Student((long) testData.get("id"), (String) testData.get("name"),
                (long) testData.get("age"), (String) testData.get("email"));
        response = given()
                .body(student)
                .when().post(Endpoints.STUDENT_ENDPOINT)
                .then().statusCode(200).extract().response();
       JsonPath jsonPath=new JsonPath(response.asString());
        studId= jsonPath.getInt("id");
    }

    @When("updating email field in student data")
    public void updatingEmailFieldInStudentData() {
        JSONObject testData = (JSONObject) data.get("updateStudent");
         student = new Student((long) testData.get("id"), (String) testData.get("name"),
                (long) testData.get("age"), (String) testData.get("email"));
        putResponse = given()
                .body(student)
                .when().put(Endpoints.STUDENT_ENDPOINT+"/"+studId)
                .then().statusCode(200).extract().response();
    }
    @Then("student data is updated")
    public void studentDataIsUpdated() throws JsonProcessingException {
        Student putResponseStud = objectMapper.readValue(putResponse.asString(),Student.class);
        Assert.assertEquals(student.getEmail(),putResponseStud.getEmail());
    }
    Response delResponse;
    int studID;
    @Given("creating student")
    public void creatingStudent() {
        JSONObject testData = (JSONObject) data.get("createStudent");
        Student student = new Student((long) testData.get("id"), (String) testData.get("name"),
                (long) testData.get("age"), (String) testData.get("email"));
        response = given()
                .body(student)
                .when().post(Endpoints.STUDENT_ENDPOINT)
                .then().statusCode(200).extract().response();
        JsonPath jsonPath=new JsonPath(response.asString());
        studID= jsonPath.getInt("id");
    }

    @When("deleting student")
    public void deletingStudent() {
         delResponse = given()
                .when().delete(Endpoints.STUDENT_ENDPOINT+"/"+studID)
                .then().statusCode(200).extract().response();
    }
    @Then("student is deleted")
    public void studentIsDeleted() {
        Assert.assertEquals(delResponse.asString(),"id559is deleted successfully");
    }
    Student student1;
    Response  response1;
    @Given("the student data")
    public void theStudentData() {
        JSONObject testData = (JSONObject) data.get("createStudent2");
         student1 = new Student((long) testData.get("id"), (String) testData.get("name"),
                (long) testData.get("age"),(String) testData.get("email"));
    }

    @When("creating student without email")
    public void creatingStudentWithoutEmail() {
        response1 = given()
                .body(student1)
                .when().post(Endpoints.STUDENT_ENDPOINT)
                .then().statusCode(400).extract().response();

    }

    @Then("getting error message")
    public void gettingErrorMessage() throws JsonProcessingException {
        ErrorObject errorObject = objectMapper.readValue(response1.asString(), ErrorObject.class);
        Assert.assertEquals(errorObject.getMessage(),"Email is required");

    }
    Student student2;
    Response response2;
    @Given("the student data without age")
    public void theStudentDataWithoutAge() {
        JSONObject testData = (JSONObject) data.get("createStudent3");
         student2 = new Student((long) testData.get("id"), (String) testData.get("name"),0
             , (String) testData.get("email"));
    }

    @When("creating student without age")
    public void creatingStudentWithoutAge() {
        response2 = given()
                .body(student2)
                .when().post(Endpoints.STUDENT_ENDPOINT)
                .then().statusCode(400).extract().response();
    }

    @Then("getting error message age is required")
    public void gettingErrorMessageAgeIsRequired() throws JsonProcessingException {
        ErrorObject errorObject = objectMapper.readValue(response2.asString(), ErrorObject.class);
        Assert.assertEquals(errorObject.getMessage(),"Age is required");
    }

    Student student3;
    Response response3;
    @Given("the student data without id")
    public void theStudentDataWithoutId() {
        JSONObject testData = (JSONObject) data.get("createStudent");
        student3 = new Student(0, (String) testData.get("name"),(long) testData.get("age")
                , (String) testData.get("email"));
    }

    @When("creating student without id")
    public void creatingStudentWithoutId() {
        response3 = given()
                .body(student3)
                .when().post(Endpoints.STUDENT_ENDPOINT)
                .then().statusCode(500).extract().response();
    }
    @Then("getting Internal server error message")
    public void gettingInternalServerErrorMessage () throws JsonProcessingException {
        ErrorObjectId errorObjectID= objectMapper.readValue(response3.asString(), ErrorObjectId.class);
        Assert.assertEquals(errorObjectID.getError(),"Internal Server Error");
        Assert.assertEquals(errorObjectID.getStatus(),500);
    }
    Student student4;
    Response response4;
    @Given("the student data without name")
    public void theStudentDataWithoutName() {
        JSONObject testData = (JSONObject) data.get("createStudent4");
        student4 = new Student((long) testData.get("id"), (String) testData.get("name"),(long) testData.get("age")
                , (String) testData.get("email"));
    }
    @When("creating student without name")
    public void creatingStudentWithoutName() {
        response4 = given()
                .body(student4)
                .when().post(Endpoints.STUDENT_ENDPOINT)
                .then().statusCode(400).extract().response();
    }
    @Then("getting error message name is required")
    public void gettingErrorMessageNameIsRequired() throws JsonProcessingException {
        ErrorObject errorObject= objectMapper.readValue(response4.asString(), ErrorObject.class);
        Assert.assertEquals(errorObject.getMessage(),"Name is required");
    }
    Student student5;
    Response response5;
    @Given("the student")
    public void theStudent() {
        JSONObject testData = (JSONObject) data.get("createStudent");
        student5 = new Student((long) testData.get("id"), (String) testData.get("name"),(long) testData.get("age")
                , (String) testData.get("email"));
    }
    @When("creating students with invalid endpoint")
    public void creatingStudentsWithInvalidEndpoint() {
        response5 = given()
                .body(student5)
                .when().put(Endpoints.STUDENT_ENDPOINT)
                .then().statusCode(405).extract().response();
    }
    @Then("getting bad request error message")
    public void gettingBadRequestErrorMessage() {
        JsonPath jsonPath =new JsonPath(response5.asString());
        Assert.assertEquals(jsonPath.getString("error"),"Method Not Allowed");
    }

    Response response6;
    @Given("the request with specific student Id")
    public void theRequestWithSpecificStudentId() {
         response6 = given()
                .when().get(Endpoints.STUDENT_ENDPOINT+"/999")
                .then().statusCode(200).extract().response();
    }

    @Then("specific student data is displayed")
    public void specificStudentDataIsDisplayed() {
        JsonPath jsonPath=new JsonPath(response6.asString());
        Assert.assertEquals(jsonPath.getString("id"),"999");
    }
}
