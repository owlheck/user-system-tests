package user.system.negative;

import org.junit.Test;
import user.system.Base;

import static org.hamcrest.CoreMatchers.equalTo;
import static user.system.setups.RestSetups.createUserWithTask;
import static user.system.utils.Utils.getResourceAsString;


public class CheckGender extends Base {
    private static final String PATH_TO_JSON = "request-bodies/gender/";

    @Test
    // Test is failed because there is no handling on backend for value in gender field
    public void shouldRespondWithErrorIsGenderIsNotMorF() {
        String requestBody = getResourceAsString(PATH_TO_JSON + "invalid/not-m-or-f");
        requestBody = String.format(requestBody, getRandomEmail(), getRandomName());

        createUserWithTask(requestBody)
                .then()
                .statusCode(200)
                .log().all()
                .body("type", equalTo("error"))
                .body("message", equalTo("Значение поле gender может быть только m или f"));
    }

    @Test
    // Test is failed because there is no handling on backend for type of gender field
    public void shouldRespondWithErrorForIntType() {
        String requestBody = getResourceAsString(PATH_TO_JSON + "invalid/int");
        requestBody = String.format(requestBody, getRandomEmail(), getRandomName());

        createUserWithTask(requestBody)
                .then()
                .statusCode(200)
                .log().all()
                .body("type", equalTo("error"))
                .body("message", equalTo("Значение поле gender может быть только строкой"));
    }
}
