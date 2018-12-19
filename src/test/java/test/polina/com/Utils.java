package test.polina.com;

import com.github.javafaker.Faker;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.io.IOException;
import java.net.URL;

import static io.restassured.RestAssured.given;


public class Utils {
    static Faker faker = new Faker();

    public static void setBaseURI(String baseURI) {
        RestAssured.baseURI = baseURI;
    }

    public static void setBasePath(String basePath) {
        RestAssured.basePath = basePath;
    }

    public static String getResourceAsString(String Location) {
        URL url = Resources.getResource(Location);

        try {
            return Resources.toString(url, Charsets.UTF_8);
        } catch (IOException ex) {
            return "";
        }
    }

    public static Response createUserWithTask(String body) {
        return given()
                .contentType("application/json")
                .body(body)
                .when()
                .post("/createuserwithtasks");
    }

    public static String getRandomEmail() {
        return faker.internet().emailAddress();
    }

    public static String getRandomName() {
        return faker.address().firstName();
    }
}
