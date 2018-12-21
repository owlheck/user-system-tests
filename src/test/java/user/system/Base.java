package user.system;

import com.github.javafaker.Faker;
import org.junit.Before;

import static user.system.config.Config.*;
import static user.system.setups.RestSetups.*;


public class Base {
    private static final Faker FAKER = new Faker();

    @Before
    public void before() {
        setBaseURI(BASE_URI);
        setBasePath(BASE_PATH);
    }

    protected String getRandomEmail() {
        return FAKER.internet().emailAddress();
    }

    protected String getRandomName() {
        return FAKER.address().firstName();
    }
}
