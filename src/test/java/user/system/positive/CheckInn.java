package user.system.positive;

import org.junit.Test;
import user.system.Base;

import static org.hamcrest.CoreMatchers.equalTo;
import static user.system.setups.RestSetups.createUserWithTask;
import static user.system.utils.Utils.getResourceAsString;


public class CheckInn extends Base {
    private static final String PATH_TO_JSON = "request-bodies/inn/";

    @Test
    // Test is failed because field inn is missing
    public void shouldRespondWithInn() {
        String requestBody = getResourceAsString(PATH_TO_JSON + "valid");
        requestBody = String.format(requestBody, getRandomEmail(), getRandomName());

        createUserWithTask(requestBody)
                .then()
                .statusCode(200)
                .log().all()
                .body("inn", equalTo("111111111111"));
    }
}
