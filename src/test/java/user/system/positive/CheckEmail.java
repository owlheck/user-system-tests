package user.system.positive;

import org.junit.Test;
import user.system.Base;
import user.system.utils.Utils;

import static org.hamcrest.CoreMatchers.equalTo;
import static user.system.setups.RestSetups.createUserWithTask;
import static user.system.utils.Utils.getResourceAsString;


public class CheckEmail extends Base {
    private static final String PATH_TO_JSON = "request-bodies/email/";

    @Test
    public void shouldRespondWithEmail() {
        String requestBody = getResourceAsString(PATH_TO_JSON + "valid");
        String email = getRandomEmail();
        requestBody = String.format(requestBody, email, getRandomName());

        createUserWithTask(requestBody)
                .then()
                .statusCode(200)
                .body("email", equalTo(email));
    }
}
