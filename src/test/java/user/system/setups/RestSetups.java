package user.system.setups;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class RestSetups {
    public static Response createUserWithTask(String body) {
        return given()
                .contentType("application/json")
                .body(body)
                .when()
                .post("/createuserwithtasks");
    }

    public static void setBaseURI(String baseURI) {
        RestAssured.baseURI = baseURI;
    }

    public static void setBasePath(String basePath) {
        RestAssured.basePath = basePath;
    }
}
