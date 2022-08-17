package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.Assert;
import utils.Endpoints;

import static io.restassured.RestAssured.given;
import static utils.JsonInputParser.data;

public class UserStepdefs {
    User user;
    Response response;
    @Given("the user details")
    public void theUserDetails() {
        JSONObject testData = (JSONObject) data.get("createUser");

          user = new User( (String) testData.get("name"),
               (String) testData.get("address"),(long) testData.get("marks"));
    }
    @When("creating the user")
    public void creatingTheUser() {
            response = given()
                .body(user)
                .when().post("/addUser")
                .then().statusCode(200).extract().response();
    }
    @Then("the user is created")
    public void theUserIsCreated() {
        JsonPath jsonPath=new JsonPath(response.asString());
        Assert.assertEquals(jsonPath.getString("name"),"GokulRaj");
    }

    @Given("the request body")
    public void theRequestBody() {
        Response response = given()
                .when().get("/users")
                .then().statusCode(200).extract().response();
    }

    @Then("getting users list")
    public void gettingUsersList() {

    }
    int userId;
    @Given("creating a user")
    public void creatingAUser() {
        JSONObject testData = (JSONObject) data.get("createUser");
       User user1 = new User( (String) testData.get("name"),
                (String) testData.get("address"),(long) testData.get("marks"));
       Response response1 = given()
                .body(user1)
                .when().post("/addUser")
                .then().statusCode(200).extract().response();
        JsonPath jsonPath=new JsonPath(response1.asString());
         userId= jsonPath.getInt("id");
    }
    Response response2;
    @When("updating marks field in the user")
    public void updatingMarksFieldInTheUser() {
        JSONObject testData = (JSONObject) data.get("updateUser");
        User user2 = new User(userId,(String) testData.get("name"),
                (String) testData.get("address"),(long) testData.get("marks"));
        response2 = given()
                .body(user2)
                .when().put("/updateuser")
                .then().statusCode(200).extract().response();
    }
    @Then("the user is updated")
    public void theUserIsUpdated() {
        JsonPath jsonPath=new JsonPath(response2.asString());
        Assert.assertEquals(jsonPath.getString("marks"),"90");
    }
    User user3;

    Response response3;
    Response delResponse;
    int ID;
    @Given("creating a user data")
    public void creatingAUserData() {
        JSONObject testData = (JSONObject) data.get("createUser");
         user3 = new User( (String) testData.get("name"),
                (String) testData.get("address"),(long) testData.get("marks"));
        response3 = given()
                .body(user3)
                .when().post("/addUser")
                .then().statusCode(200).extract().response();
        JsonPath jsonPath=new JsonPath(response3.asString());
        ID= jsonPath.getInt("id");
    }

    @When("deleting the user")
    public void deletingTheUser() {
         delResponse = given()
                .body(user3)
                .when().delete("/user/"+ID)
                .then().statusCode(200).extract().response();
    }
    @Then("the user is deleted")
    public void theUserIsDeleted() {
        Assert.assertEquals(delResponse.asString(),"User got deleted");
    }
    User user4;
    Response response4;
    @Given("the user data without name")
    public void theUserDataWithoutName() {
        JSONObject testData = (JSONObject) data.get("createUser");
        user4 = new User( null,
                (String) testData.get("address"),(long) testData.get("marks"));
    }

    @When("creating the user without name")
    public void creatingTheUserWithoutName() {
        response4 = given()
                .body(user4)
                .when().post("/addUser")
                .then().statusCode(400).extract().response();
    }
    @Then("getting error message Name is required")
    public void gettingErrorMessageNameIsRequired() {
        JsonPath jsonPath=new JsonPath(response4.asString());
        Assert.assertEquals(jsonPath.getString("message"),"Name is required");
    }
    User user5;
    Response response5;
    @Given("the user data without address")
    public void theUserDataWithoutAddress() {
        JSONObject testData = (JSONObject) data.get("createUser");
        user5 = new User( (String) testData.get("name"),null
                ,(long) testData.get("marks"));
    }

    @When("creating the user without address")
    public void creatingTheUserWithoutAddress() {
        response5 = given()
                .body(user5)
                .when().post("/addUser")
                .then().statusCode(400).extract().response();
    }

    @Then("getting error message Address is required")
    public void gettingErrorMessageAddressIsRequired() {
        JsonPath jsonPath=new JsonPath(response5.asString());
        Assert.assertEquals(jsonPath.getString("message"),"Address is required");
    }
    int userID;
    @Given("the user data")
    public void theUserData() {
        JSONObject testData = (JSONObject) data.get("createUser");
        User user6 = new User( (String) testData.get("name"),
                (String) testData.get("address"),(long) testData.get("marks"));
        Response response1 = given()
                .body(user6)
                .when().post("/addUser")
                .then().statusCode(200).extract().response();
        JsonPath jsonPath=new JsonPath(response1.asString());
       int userID= jsonPath.getInt("id");

    }
    Response response7;
    @When("updating the user with invalid endpoint")
    public void updatingTheUserWithInvalidEndpoint() {
        JSONObject testData = (JSONObject) data.get("updateUser");
        User user7 = new User(userID,(String) testData.get("name"),
                (String) testData.get("address"),(long) testData.get("marks"));
        response7 = given()
                .body(user7)
                .when().put("/addUser")
                .then().statusCode(405).extract().response();
    }

    @Then("getting error message method not allowed")
    public void gettingErrorMessageMethodNotAllowed() {
        JsonPath jsonPath=new JsonPath(response7.asString());
        Assert.assertEquals(jsonPath.getString("error"),"Method Not Allowed");
    }
    User user8;
    User user9;
    Response response8;
    @Given("multiple users")
    public void multipleUsers() {
        JSONArray userArray = (JSONArray) data.get("multipleUsers");
        JSONObject testData0 = (JSONObject)  userArray.get(0);
        JSONObject testData1=(JSONObject) userArray.get(1);
         user8 = new User((String) testData0.get("name"),
                (String) testData0.get("address"),(long) testData0.get("marks"));
         user9 = new User((String) testData1.get("name"),
                (String) testData1.get("address"),(long) testData1.get("marks"));
    }

    @When("creating multiple users")
    public void creatingMultipleUsers() {
        User[] array=new User[2];
        array[0]=user8;
        array[1]=user9;
         response8 = given()
                .body(array)
                .when().post("/addUsers")
                .then().statusCode(200).extract().response();
    }

    @Then("multiple users are created")
    public void multipleUsersAreCreated() {
        JsonPath jsonPath=new JsonPath(response8.asString());
        Assert.assertEquals(jsonPath.getString("name[0]"),"Gokulraj");
        Assert.assertEquals(jsonPath.getString("name[1]"),"GokulrajSL");
    }
    Response response9;
    @Given("the request with specific userId")
    public void theRequestWithSpecificUserId() {
         response9 = given()
                .when().get("/user/5")
                .then().statusCode(200).extract().response();
    }

    @Then("specific user is displayed")
    public void specificUserIsDisplayed() {
        JsonPath jsonPath=new JsonPath(response9.asString());
        Assert.assertEquals(jsonPath.getString("id"),"5");
    }
}

