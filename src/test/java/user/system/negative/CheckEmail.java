package user.system.negative;

import org.junit.Test;
import user.system.Base;
import user.system.utils.Utils;

import static org.hamcrest.CoreMatchers.equalTo;
import static user.system.setups.RestSetups.createUserWithTask;
import static user.system.utils.Utils.getResourceAsString;


public class CheckEmail extends Base {
    private static final String PATH_TO_JSON = "request-bodies/email/";

    @Test
    public void shouldRespondWithErrorForEmailWithDoubleAtSign() {
        String requestBody = getResourceAsString(PATH_TO_JSON + "invalid/double-at-sign");


        createUserWithTask(requestBody)
                .then()
                .statusCode(200)
                .body("type", equalTo("error"))
                .body("message", equalTo("email неправильный!"));
    }

    @Test
    public void shouldRespondWithErrorForEmailWithLeadingDot() {
        String requestBody = getResourceAsString(PATH_TO_JSON + "invalid/leading-dot");


        createUserWithTask(requestBody)
                .then()
                .statusCode(200)
                .body("type", equalTo("error"))
                .body("message", equalTo("email неправильный!"));
    }

    @Test
    public void shouldRespondWithErrorForEmailWithLongLength() {
        String requestBody = getResourceAsString(PATH_TO_JSON + "invalid/long-length");


        createUserWithTask(requestBody)
                .then()
                .statusCode(200)
                .body("type", equalTo("error"))
                .body("message", equalTo("email неправильный!"));
    }

    @Test
    public void shouldRespondWithErrorForEmailWithUnicodeChars() {
        String requestBody = getResourceAsString(PATH_TO_JSON + "invalid/unicode-chars");


        createUserWithTask(requestBody)
                .then()
                .statusCode(200)
                .body("type", equalTo("error"))
                .body("message", equalTo("email неправильный!"));
    }

    @Test
    public void shouldRespondWithErrorForEmailWithoutAtSign() {
        String requestBody = getResourceAsString(PATH_TO_JSON + "invalid/without-at-sign");


        createUserWithTask(requestBody)
                .then()
                .statusCode(200)
                .body("type", equalTo("error"))
                .body("message", equalTo("email неправильный!"));
    }

    @Test
    public void shouldRespondWithErrorForEmailWithoutDot() {
        String requestBody = getResourceAsString(PATH_TO_JSON + "invalid/without-dot");


        createUserWithTask(requestBody)
                .then()
                .statusCode(200)
                .body("type", equalTo("error"))
                .body("message", equalTo("email неправильный!"));
    }

    @Test
    public void shouldRespondWithErrorForEmailWithoutTopLevelDomain() {
        String requestBody = getResourceAsString(PATH_TO_JSON + "invalid/without-top-level-domain");


        createUserWithTask(requestBody)
                .then()
                .statusCode(200)
                .body("type", equalTo("error"))
                .body("message", equalTo("email неправильный!"));
    }

    @Test
    public void shouldRespondWithErrorForEmailWithoutUsername() {
        String requestBody = getResourceAsString(PATH_TO_JSON + "invalid/without-username");


        createUserWithTask(requestBody)
                .then()
                .statusCode(200)
                .body("type", equalTo("error"))
                .body("message", equalTo("email неправильный!"));
    }

    @Test
    public void shouldRespondWithErrorForEmailWithSeveralDots() {
        String requestBody = getResourceAsString(PATH_TO_JSON + "invalid/several-dots");


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
        String requestBody = Utils.getResourceAsString(PATH_TO_JSON + "without");

        createUserWithTask(requestBody)
                .then()
                .statusCode(200)
                .body("type", equalTo("error"))
                .body("message", equalTo("Параметр email является обязательным!"));
    }
}
