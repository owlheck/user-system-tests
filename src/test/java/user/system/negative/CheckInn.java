package user.system.negative;

import org.junit.Test;
import user.system.Base;

import static org.hamcrest.CoreMatchers.equalTo;
import static user.system.setups.RestSetups.createUserWithTask;
import static user.system.utils.Utils.getResourceAsString;


public class CheckInn extends Base {
    private static final String PATH_TO_JSON = "request-bodies/inn/";

    @Test
    public void shouldRespondWithErrorForLengthLessThan12() {
        String requestBody = getResourceAsString(PATH_TO_JSON + "invalid/length-less-than-12");
        requestBody = String.format(requestBody, getRandomEmail(), getRandomName());

        createUserWithTask(requestBody)
                .then()
                .statusCode(200)
                .body("type", equalTo("error"))
                .body("message", equalTo(" Значение 11111 ИНН ФЛ должен содержать 12 цифр"));
    }

    @Test
    public void shouldRespondWithErrorForLengthMoreThan12() {
        String requestBody = getResourceAsString(PATH_TO_JSON + "invalid/length-more-than-12");
        requestBody = String.format(requestBody, getRandomEmail(), getRandomName());

        createUserWithTask(requestBody)
                .then()
                .statusCode(200)
                .body("type", equalTo("error"))
                .body("message", equalTo(" Значение 111111111111111 ИНН ФЛ должен содержать 12 цифр"));
    }

    @Test
    // Test is failed because field inn is missing
    public void shouldRespondWithErrorForTypeInt() {
        String requestBody = getResourceAsString(PATH_TO_JSON + "invalid/int");
        requestBody = String.format(requestBody, getRandomEmail(), getRandomName());

        createUserWithTask(requestBody)
                .then()
                .statusCode(200)
                .log().all()
                .body("type", equalTo("error"))
                .body("message", equalTo("Тип поля inn должен быть строкой"));
    }
}
