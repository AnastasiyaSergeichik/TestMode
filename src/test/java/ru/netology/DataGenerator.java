package ru.netology;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {

    private static RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setPort(9999)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();


    public static RegistrationUser getNewUser(String status) {
        Faker faker = new Faker(new Locale("ru"));

        RegistrationUser registrationUser = new RegistrationUser(
                faker.name().firstName(),
                faker.internet().password(), status);
        given()
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(registrationUser) // передаём в теле объект, который будет преобразован в JSON
                .when()
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK

        return registrationUser;
    }
    public static RegistrationUser generate (String status) {
        Faker faker = new Faker(new Locale("ru"));
        return new RegistrationUser(
                faker.name().firstName(),
                faker.internet().password(),
                status
        );
    }
}

