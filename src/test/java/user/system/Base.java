package user.system;

import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static user.system.config.Config.*;
import static user.system.setups.RestSetups.*;
import static user.system.utils.Utils.*;


public class Base {
    private static final Faker FAKER = new Faker();

    @Before
    public void before() {
        setBaseURI(BASE_URI);
        setBasePath(BASE_PATH);
    }

    @Test
    public void shouldRespondWithOkAndMatchSchema() {
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
                .log()
                .all()
                .statusCode(200)
                .assertThat()
                .body(matchesJsonSchemaInClasspath("json-schemas/all-fields"));
    }

    protected String getRandomEmail() {
        return FAKER.internet().emailAddress();
    }

    protected String getRandomName() {
        return FAKER.address().firstName();
    }
}
