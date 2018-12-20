package user.system;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static user.system.setups.RestSetups.createUserWithTask;
import static user.system.utils.Utils.getResourceAsString;


public class CheckTasks extends Base{
    @Test
    public void shouldRespondWithErrorForSixTasks() {
        String requestBody = getResourceAsString("request-bodies/tasks/unacceptable-count");
        requestBody = String.format(requestBody, getRandomEmail(), getRandomName());

        createUserWithTask(requestBody)
                .then()
                .statusCode(200)
                .body("type", equalTo("error"))
                .body("message", equalTo("Вы не можете добавить больше 5 тасков"));
    }

    @Test
    public void shouldRespondWithErrorForInvalidType() {
        String requestBody = getResourceAsString("request-bodies/tasks/invalid");
        requestBody = String.format(requestBody, getRandomEmail(), getRandomName());

        createUserWithTask(requestBody)
                .then()
                .statusCode(200)
                .body("type", equalTo("error"))
                .body("message", equalTo("tasks должен состоять из массива,который содержит массивы (title=>'задача','description=>'описание')"));
    }

    @Test
    public void shouldRespondWithErrorForTaskWithoutName() {
        String requestBody = getResourceAsString("request-bodies/tasks/task-without-name");
        requestBody = String.format(requestBody, getRandomEmail(), getRandomName());

        createUserWithTask(requestBody)
                .then()
                .statusCode(200)
                .body("type", equalTo("error"))
                .body("message", equalTo("tasks.title не может быть пустым"));
    }

    @Test
    public void shouldRespondWithErrorForTaskWithoutDescription() {
        String requestBody = getResourceAsString("request-bodies/tasks/task-without-description");
        requestBody = String.format(requestBody, getRandomEmail(), getRandomName());

        createUserWithTask(requestBody)
                .then()
                .statusCode(200)
                .log()
                .all()
                .body("type", equalTo("error"))
                .body("message", equalTo("tasks.description не может быть пустым"));
    }
}
