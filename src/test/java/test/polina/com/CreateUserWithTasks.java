package test.polina.com;

import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
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
    public void shouldRespondWithOk() {
        String requestBody = Utils.getResourceAsString("request-bodies/requiredFields");
        requestBody = String.format(requestBody, Utils.getRandomEmail(), Utils.getRandomName());

        Utils.createUserWithTask(requestBody)
                .then()
                .log()
                .all()
                .statusCode(200)
                .assertThat()
                .body(matchesJsonSchemaInClasspath("json-schemas/requiredFields"));

    }
}
