package user.system;

import org.junit.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static user.system.setups.RestSetups.createUserWithTask;
import static user.system.utils.Utils.getResourceAsString;


public class CheckSchemas extends Base{
    @Test
    public void shouldRespondWithOkAndMatchSchemaForRequiredFields() {
        String requestBody = getResourceAsString("request-bodies/required-fields");
        requestBody = String.format(requestBody, getRandomEmail(), getRandomName());

        createUserWithTask(requestBody)
                .then()
                .statusCode(200)
                .assertThat()
                .body(matchesJsonSchemaInClasspath("json-schemas/required-fields"));
    }

    @Test
    public void shouldRespondWithOkAndMatchSchemaForAllFields() {
        String requestBody = getResourceAsString("request-bodies/all-fields");
        requestBody = String.format(requestBody, getRandomEmail(), getRandomName());

        createUserWithTask(requestBody)
                .then()
                .statusCode(200)
                .assertThat()
                .body(matchesJsonSchemaInClasspath("json-schemas/all-fields"));
    }
}
