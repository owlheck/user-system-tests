package user.system;

import org.junit.Test;

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
}
