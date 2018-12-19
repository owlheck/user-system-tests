package test.polina.com;

import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.CoreMatchers.equalTo;
import static test.polina.com.Utils.*;

public class CreateUserWithTasks {
    int[] companyIds = {1285, 1289, 1290, 1291, 1292};

    @Before
    public void before() {
        setBaseURI("http://users.bugred.ru");
        setBasePath("/tasks/rest");
    }

    @Test
    public void shouldRespondWithOkAndMatchSchema() {
        String requestBody = Utils.getResourceAsString("request-bodies/requiredFields");
        requestBody = String.format(requestBody, Utils.getRandomEmail(), Utils.getRandomName());

        given()
                .contentType("application/json")
                .body(requestBody)
        .when()
                .post("/createuserwithtasks")
        .then()
                .log()
                .all()
                .statusCode(200)
                .assertThat()
                .body(matchesJsonSchemaInClasspath("json-schemas/requiredFields"));
    }

    @Test
    public void shouldRespondWithErrorForInvalidEmail() {
        String requestBody = Utils.getResourceAsString("request-bodies/invalidEmail");

        Utils.createUserWithTask(requestBody)
                .then()
                .statusCode(200)
                .body("type", equalTo("error"))
                .body("message", equalTo("email неправильный!"));
    }

    @Test
    public void shouldRespondWithErrorForAlreadyRegisteredEmail() {
        String requestBody = Utils.getResourceAsString("request-bodies/duplicateEmail");

        Utils.createUserWithTask(requestBody)
                .then()
                .statusCode(200)
                .body("type", equalTo("error"))
                .body("message", equalTo("Пользователь с таким email уже существует "));
    }
}
