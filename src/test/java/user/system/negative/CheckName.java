package user.system.negative;

import org.junit.Test;
import user.system.Base;

import static org.hamcrest.CoreMatchers.equalTo;
import static user.system.setups.RestSetups.createUserWithTask;
import static user.system.utils.Utils.getResourceAsString;


public class CheckName extends Base {
    private static final String PATH_TO_JSON = "request-bodies/name/";

    @Test
    public void shouldRespondWithErrorForRequestWithIncorrectName() {
        String requestBody = getResourceAsString(PATH_TO_JSON + "invalid");
        requestBody = String.format(requestBody, getRandomEmail());

        createUserWithTask(requestBody)
                .then()
                .statusCode(200)
                .body("type", equalTo("error"))
                .body("message", equalTo(" Значение 23 некорректное,должна быть только строка"));
    }

    @Test
    public void shouldRespondWithErrorForRequestWithAlreadyRegisteredName() {
        String requestBody = getResourceAsString(PATH_TO_JSON + "duplicate");
        String name = getRandomName();
        requestBody = String.format(requestBody, getRandomEmail(), name);

        createUserWithTask(requestBody)
                .then()
                .statusCode(200)
                .body("name", equalTo(name));

        requestBody = getResourceAsString(PATH_TO_JSON + "duplicate");
        requestBody = String.format(requestBody, getRandomEmail(), name);

        createUserWithTask(requestBody)
                .then()
                .statusCode(200)
                .body("type", equalTo("error"))
                .body("message", equalTo(String.format(" имя %s уже есть в БД", name)));
    }

    @Test
    public void shouldRespondWithErrorForRequestWithoutName() {
        String requestBody = getResourceAsString(PATH_TO_JSON + "without");
        requestBody = String.format(requestBody, getRandomEmail());

        createUserWithTask(requestBody)
                .then()
                .statusCode(200)
                .body("type", equalTo("error"))
                .body("message", equalTo("Параметр name является обязательным!"));
    }
}
