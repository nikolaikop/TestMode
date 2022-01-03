package ru.netology.rest;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGeneration {

    static Faker faker = new Faker(new Locale("ru"));

    static ClientData nameData(String status) {

        return new ClientData(faker.name().lastName(), faker.internet().password(), status);
    }

    static String wrongLogin() {
        return faker.name().username();
    }

    static String wrongPw() {
        return faker.internet().password();
    }

    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    @BeforeAll
    static void setUpAll(ClientData usr) {
        // сам запрос
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(usr) // передаём в теле
                // объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }
}
