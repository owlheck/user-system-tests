package user.system.positive;

import org.junit.Test;
import user.system.Base;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static user.system.setups.RestSetups.createUserWithTask;
import static user.system.utils.Utils.getResourceAsString;


public class CheckCompanies extends Base {
    private static final String PATH_TO_JSON = "request-bodies/companies/";

    @Test
    public void shouldRespondWithOneCompany() {
        String requestBody = getResourceAsString(PATH_TO_JSON + "add-one");
        requestBody = String.format(requestBody, getRandomEmail(), getRandomName());

        createUserWithTask(requestBody)
                .then()
                .statusCode(200)
                .body("companies.name", hasItem("TestCompany"))
                .body("companies.id", hasItem(1285));
    }
}
