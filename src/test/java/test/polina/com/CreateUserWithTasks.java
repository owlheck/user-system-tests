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
        String requestBody = getResourceAsString("request-bodies/requiredFields");
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
        String requestBody = getResourceAsString("request-bodies/invalidEmail");

        createUserWithTask(requestBody)
                .then()
                .statusCode(200)
                .body("type", equalTo("error"))
                .body("message", equalTo("email неправильный!"));
    }

    @Test
    public void shouldRespondWithErrorForAlreadyRegisteredEmail() {
        String requestBody = getResourceAsString("request-bodies/duplicateEmail");
        String email = getRandomEmail();
        requestBody = String.format(requestBody, email, getRandomName());

        createUserWithTask(requestBody)
                .then()
                .statusCode(200)
                .body("email", equalTo(email));

        createUserWithTask(requestBody)
                .then()
                .statusCode(200)
                .body("type", equalTo("error"))
                .body("message", equalTo("Пользователь с таким email уже существует "));

    }

    @Test
    public void shouldRespondWithErrorForRequestWithoutEmail() {
        String requestBody = Utils.getResourceAsString("request-bodies/withoutEmail");

        createUserWithTask(requestBody)
                .then()
                .statusCode(200)
                .body("type", equalTo("error"))
                .body("message", equalTo("Параметр email является обязательным!"));
    }

    @Test
    public void shouldRespondWithErrorForRequestWithIncorrectName() {
        String requestBody = getResourceAsString("request-bodies/invalidName");
        requestBody = String.format(requestBody, getRandomEmail());

        createUserWithTask(requestBody)
                .then()
                .statusCode(200)
                .body("type", equalTo("error"))
                .body("message", equalTo("Значение 23 некорректное,должна быть только строка"));
    }

    @Test
    public void shouldRespondWithErrorForRequestWithAlreadyRegisteredName() {
        String requestBody = getResourceAsString("request-bodies/duplicateName");
        String name = getRandomName();
        requestBody = String.format(requestBody, getRandomEmail(), name);

        System.out.println(requestBody);
        createUserWithTask(requestBody)
                .then()
                .statusCode(200)
                .body("name", equalTo(name));

        requestBody = getResourceAsString("request-bodies/duplicateName");
        requestBody = String.format(requestBody, getRandomEmail(), name);

        System.out.println(requestBody);

        createUserWithTask(requestBody)
                .then()
                .statusCode(200)
                .body("type", equalTo("error"))
                .body("message", equalTo(String.format(" имя %s уже есть в БД", name)));
    }

    @Test
    public void shouldRespondWithErrorForRequestWithoutName() {
        String requestBody = getResourceAsString("request-bodies/withoutName");
        requestBody = String.format(requestBody, getRandomEmail());

        createUserWithTask(requestBody)
                .then()
                .statusCode(200)
                .body("type", equalTo("error"))
                .body("message", equalTo("Параметр name является обязательным!"));
    }
}
