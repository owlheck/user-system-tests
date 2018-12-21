package user.system;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static user.system.setups.RestSetups.createUserWithTask;
import static user.system.utils.Utils.getResourceAsString;


public class CheckCompanies extends Base {
    @Test
    public void shouldRespondWithErrorForMoreThanThreeCompanies() {
        String requestBody = getResourceAsString("request-bodies/companies/unacceptable-count");
        requestBody = String.format(requestBody, getRandomEmail(), getRandomName());

        createUserWithTask(requestBody)
                .then()
                .statusCode(200)
                .body("type", equalTo("error"))
                .body("message", equalTo("параметр companies в нем размер массива должен быть не больше 3"));
    }

    @Test
    public void shouldRespondWithErrorForNotExistsCompany() {
        String requestBody = getResourceAsString("request-bodies/companies/not-exists");
        requestBody = String.format(requestBody, getRandomEmail(), getRandomName());

        createUserWithTask(requestBody)
                .then()
                .statusCode(200)
                .body("type", equalTo("error"))
                .body("message", equalTo("Компании не существует"));
    }

    @Test
    public void shouldRespondWithErrorForInvalid() {
        String requestBody = getResourceAsString("request-bodies/companies/invalid");
        requestBody = String.format(requestBody, getRandomEmail(), getRandomName());

        createUserWithTask(requestBody)
                .then()
                .statusCode(200)
                .body("type", equalTo("error"))
                .body("message", equalTo(""));
    }
}
