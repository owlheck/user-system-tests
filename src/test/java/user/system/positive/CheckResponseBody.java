package user.system.positive;

import org.junit.Test;
import user.system.Base;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static user.system.setups.RestSetups.createUserWithTask;
import static user.system.utils.Utils.getResourceAsString;

public class CheckResponseBody extends Base {
    @Test
    public void shouldRespondWithOkAndMatchSchemaForRequiredFields() {
        String requestBody = getResourceAsString("request-bodies/required-fields");
        String name = getRandomName();
        String email = getRandomEmail();

        requestBody = String.format(requestBody, email, name);

        createUserWithTask(requestBody)
                .then()
                .statusCode(200)
                .body("name", equalTo(name))
                .body("email", equalTo(email))
                .body("by_user",  equalTo("manager@mail.ru"))
                .body("companies", equalTo(null))
                .body("date", equalTo(getCurrentDate()))
                .body("role", hasItems("user"))
                .body("tasks.name", hasItems("First Task", "Second Task"));
    }


    @Test
    public void shouldRespondWithOkAndMatchSchemaForAllFields() {
        String requestBody = getResourceAsString("request-bodies/all-fields");
        String name = getRandomName();
        String email = getRandomEmail();

        requestBody = String.format(requestBody, email, name);

        createUserWithTask(requestBody)
                .then()
                .log().all()
                .statusCode(200)
                .body("name", equalTo(name))
                .body("name1", equalTo("TestName"))
                .body("surname1", equalTo("TestSurname"))
                .body("fathername1", equalTo("TestFathername"))
                .body("hobby", equalTo("PS Gaming, swimming"))
                .body("cat", equalTo("TestCat"))
                .body("dog", equalTo("TestDog"))
                .body("parrot", equalTo("TestParrot"))
                .body("cavy", equalTo("TestCavy"))
                .body("hamster", equalTo("TestHamster"))
                .body("squirrel", equalTo("TestSquirrel"))
                .body("phone", equalTo("8-(800)-20-12"))
                .body("adres", equalTo("Address 1"))
                .body("gender", equalTo("f"))
                .body("email", equalTo(email))
                .body("by_user",  equalTo("manager@mail.ru"))
                .body("companies.name", hasItems("TestCompany3", "TestCompany1", "TestCompany"))
                .body("date", equalTo(getCurrentDate()))
                .body("role", hasItems("user"))
                .body("tasks.name", hasItems("First Task", "Second Task"));
    }
}
