package ru.netology;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Data;

import java.util.Locale;

@Data
public class DataGenerator {

    private static Faker faker = new Faker(new Locale("en"));

    private DataGenerator() {
    }

    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    private static void makeRegistration(RegistrationUser registrationUser) {
        RestAssured.given()
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(registrationUser) // передаём в теле объект, который будет преобразован в JSON
                .when()
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }

    public static RegistrationUser generateUserStatusActive() {
        String login = faker.name().firstName();
        String password = faker.internet().password();
        makeRegistration(new RegistrationUser(login, password, "active"));
        return new RegistrationUser(login, password, "active");

    }

    public static RegistrationUser generateUserStatusBlocked() {
        String login = faker.name().firstName();
        String password = faker.internet().password();
        makeRegistration(new RegistrationUser(login, password, "blocked"));
        return new RegistrationUser(login, password, "blocked");
    }

    public static RegistrationUser generateUserInvalidLogin() {
        String password = faker.internet().password();
        String status = "active";
        makeRegistration(new RegistrationUser("Anastasiia", password, status));
        return new RegistrationUser("InvalidLogin", password, status);
    }

    public static RegistrationUser generateNewUserInvalidPassword() {
        String login = faker.name().firstName();
        String status = "active";
        makeRegistration(new RegistrationUser(login, "fgf5//56dhjj", status));
        return new RegistrationUser(login, "InvalidPassword", status);
    }
}

