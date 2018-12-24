package user.system;

import com.github.javafaker.Faker;
import org.junit.Before;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    protected String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
