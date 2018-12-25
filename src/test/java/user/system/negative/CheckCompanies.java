package user.system.negative;

import org.junit.Test;
import user.system.Base;

import static org.hamcrest.CoreMatchers.equalTo;
import static user.system.setups.RestSetups.createUserWithTask;
import static user.system.utils.Utils.getResourceAsString;


public class CheckCompanies extends Base {
    private static final String PATH_TO_JSON = "request-bodies/companies/";

    @Test
    public void shouldRespondWithErrorForMoreThanThreeCompanies() {
        String requestBody = getResourceAsString(PATH_TO_JSON + "unacceptable-count");
        requestBody = String.format(requestBody, getRandomEmail(), getRandomName());

        createUserWithTask(requestBody)
                .then()
                .statusCode(200)
                .body("type", equalTo("error"))
                .body("message", equalTo("параметр companies в нем размер массива должен быть не больше 3"));
    }

    @Test
    public void shouldRespondWithErrorIfCompanyDoesNotExist() {
        String requestBody = getResourceAsString(PATH_TO_JSON + "not-exists");
        requestBody = String.format(requestBody, getRandomEmail(), getRandomName());

        createUserWithTask(requestBody)
                .then()
                .statusCode(200)
                .body("type", equalTo("error"))
                .body("message", equalTo("Компании не существует"));
    }

    @Test
    public void shouldRespondWithErrorForInvalidType() {
        String requestBody = getResourceAsString(PATH_TO_JSON + "invalid");
        requestBody = String.format(requestBody, getRandomEmail(), getRandomName());

        createUserWithTask(requestBody)
                .then()
                .statusCode(200)
                .body("type", equalTo("error"))
                .body("message", equalTo("Параметр companies должен быть массивом интеджеров"));
    }

    @Test
    // Test is fallen. Backend has no check for int value range.
    public void shouldRespondWithErrorForValueBiggerThanInt() {
        String requestBody = getResourceAsString(PATH_TO_JSON + "bigger-than-int");
        requestBody = String.format(requestBody, getRandomEmail(), getRandomName());

        createUserWithTask(requestBody)
                .then()
                .log().all()
                .statusCode(200)
                .body("type", equalTo("error"))
                .body("message", equalTo("Параметр companies должен быть массивом интеджеров"));
    }

    @Test
    // Test is fallen. Backend has no check for arr emptiness.
    public void shouldRespondWithErrorForEmptyArray() {
        String requestBody = getResourceAsString(PATH_TO_JSON + "empty");
        requestBody = String.format(requestBody, getRandomEmail(), getRandomName());

        createUserWithTask(requestBody)
                .then()
                .log().all()
                .statusCode(200)
                .body("type", equalTo("error"))
                .body("message", equalTo("Параметр companies должен быть массивом интеджеров"));
    }

    @Test
    // Test is fallen. Backend has no check for company existence.
    public void shouldRespondWithErrorIfOneOfCompanyDoesNotExists() {
        String requestBody = getResourceAsString(PATH_TO_JSON + "one-of-not-exist");
        requestBody = String.format(requestBody, getRandomEmail(), getRandomName());

        createUserWithTask(requestBody)
                .then()
                .log().all()
                .statusCode(200)
                .body("type", equalTo("error"))
                .body("message", equalTo("Одной из указанных компаний не существует"));
    }
}
