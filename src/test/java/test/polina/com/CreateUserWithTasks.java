package test.polina.com;

import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.CoreMatchers.equalTo;
import static test.polina.com.Config.*;
import static test.polina.com.RestSetups.*;
import static test.polina.com.Utils.*;


public class CreateUserWithTasks {
    int[] companyIds = {1285, 1289, 1290, 1291, 1292};
    private static final Faker FAKER = new Faker();

    @Before
    public void before() {
        setBaseURI(BASE_URI);
        setBasePath(BASE_PATH);
    }

    @Test
    public void shouldRespondWithOkAndMatchSchema() {
        String requestBody = getResourceAsString("request-bodies/requiredFields");
        requestBody = String.format(requestBody, getRandomEmail(), getRandomName());

        createUserWithTask(requestBody)
        .then()
                .statusCode(200)
                .assertThat()
                .body(matchesJsonSchemaInClasspath("json-schemas/requiredFields"));
    }

    @Test
    public void shouldRespondWithErrorForInvalidEmail() {
        String requestBody = getResourceAsString("request-bodies/email/invalid");

        createUserWithTask(requestBody)
                .then()
                .statusCode(200)
                .body("type", equalTo("error"))
                .body("message", equalTo("email неправильный!"));
    }

    @Test
    public void shouldRespondWithErrorForAlreadyRegisteredEmail() {
        String requestBody = getResourceAsString("request-bodies/email/duplicate");
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
        String requestBody = Utils.getResourceAsString("request-bodies/email/without");

        createUserWithTask(requestBody)
                .then()
                .statusCode(200)
                .body("type", equalTo("error"))
                .body("message", equalTo("Параметр email является обязательным!"));
    }

    @Test
    public void shouldRespondWithErrorForRequestWithIncorrectName() {
        String requestBody = getResourceAsString("request-bodies/name/invalid");
        requestBody = String.format(requestBody, getRandomEmail());

        createUserWithTask(requestBody)
                .then()
                .statusCode(200)
                .body("type", equalTo("error"))
                .body("message", equalTo(" Значение 23 некорректное,должна быть только строка"));
    }

    @Test
    public void shouldRespondWithErrorForRequestWithAlreadyRegisteredName() {
        String requestBody = getResourceAsString("request-bodies/name/duplicate");
        String name = getRandomName();
        requestBody = String.format(requestBody, getRandomEmail(), name);

        createUserWithTask(requestBody)
                .then()
                .statusCode(200)
                .body("name", equalTo(name));

        requestBody = getResourceAsString("request-bodies/name/duplicate");
        requestBody = String.format(requestBody, getRandomEmail(), name);

        createUserWithTask(requestBody)
                .then()
                .statusCode(200)
                .body("type", equalTo("error"))
                .body("message", equalTo(String.format(" имя %s уже есть в БД", name)));
    }

    @Test
    public void shouldRespondWithErrorForRequestWithoutName() {
        String requestBody = getResourceAsString("request-bodies/name/without");
        requestBody = String.format(requestBody, getRandomEmail());

        createUserWithTask(requestBody)
                .then()
                .statusCode(200)
                .body("type", equalTo("error"))
                .body("message", equalTo("Параметр name является обязательным!"));
    }

    @Test
    public void shouldRespondWithErrorForSixTasks() {
        String requestBody = getResourceAsString("request-bodies/tasks/unacceptable-count");
        requestBody = String.format(requestBody, getRandomEmail(), getRandomName());

        createUserWithTask(requestBody)
                .then()
                .statusCode(200)
                .body("type", equalTo("error"))
                .body("message", equalTo("Вы не можете добавить больше 5 тасков"));
    }

    private String getRandomEmail() {
        return FAKER.internet().emailAddress();
    }

    private String getRandomName() {
        return FAKER.address().firstName();
    }
}
