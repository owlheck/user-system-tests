package user.system.positive;

import org.junit.Test;
import user.system.Base;

import static org.hamcrest.CoreMatchers.equalTo;
import static user.system.setups.RestSetups.createUserWithTask;
import static user.system.utils.Utils.getResourceAsString;


public class CheckName extends Base {
    private static final String PATH_TO_JSON = "request-bodies/name/";

    @Test
    public void shouldRespondWithName() {
        String requestBody = getResourceAsString(PATH_TO_JSON + "valid");
        String name = getRandomName();
        requestBody = String.format(requestBody, getRandomEmail(), name);

        createUserWithTask(requestBody)
                .then()
                .statusCode(200)
                .body("name", equalTo(name));
    }
}
