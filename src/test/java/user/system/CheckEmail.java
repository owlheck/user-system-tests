package user.system;

import org.junit.Test;
import user.system.utils.Utils;

import static org.hamcrest.CoreMatchers.equalTo;
import static user.system.setups.RestSetups.createUserWithTask;
import static user.system.utils.Utils.getResourceAsString;


public class CheckEmail extends Base {
    private static final String PATH_TO_JSON = "request-bodies/email/";

    @Test
    public void shouldRespondWithErrorForInvalidEmail() {
        String requestBody = getResourceAsString(PATH_TO_JSON +"invalid");

        createUserWithTask(requestBody)
                .then()
                .statusCode(200)
                .body("type", equalTo("error"))
                .body("message", equalTo("email неправильный!"));
    }

    @Test
    public void shouldRespondWithErrorForAlreadyRegisteredEmail() {
        String requestBody = getResourceAsString(PATH_TO_JSON + "duplicate");
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
        String requestBody = Utils.getResourceAsString(PATH_TO_JSON +"without");

        createUserWithTask(requestBody)
                .then()
                .statusCode(200)
                .body("type", equalTo("error"))
                .body("message", equalTo("Параметр email является обязательным!"));
    }
}
