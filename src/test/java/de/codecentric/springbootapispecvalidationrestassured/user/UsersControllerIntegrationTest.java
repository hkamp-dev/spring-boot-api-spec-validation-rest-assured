package de.codecentric.springbootapispecvalidationrestassured.user;

import com.atlassian.oai.validator.OpenApiInteractionValidator;
import com.atlassian.oai.validator.restassured.OpenApiValidationFilter;
import de.codecentric.springbootapispecvalidationrestassured.Application;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = {Application.class})
class UsersControllerIntegrationTest {

    private OpenApiValidationFilter openApiValidationFilter;

    @BeforeEach
    void setUp() {
        var validator = OpenApiInteractionValidator.createFor("/api-specs/user-api.yaml")
                .build();
        openApiValidationFilter = new OpenApiValidationFilter(validator);
        RestAssured.baseURI = "http://localhost/";
        RestAssured.port = 8080;
    }

    @Test
    void getUser() {
        RestAssured.given()
                .filter(openApiValidationFilter)
                .contentType(ContentType.JSON)
                .when()
                .get("/users/{id}", 1)
                .then()
                .statusCode(200);
    }

    @Test
    void userNotPresent() {
        RestAssured.given()
                .filter(openApiValidationFilter)
                .contentType(ContentType.JSON)
                .when()
                .get("/users/{id}", 2)
                .then()
                .statusCode(404);
    }

    @Test
    void putUser() {

        String requestBody = """
                {
                    "name": "Test User",
                    "email": "test@example.com"
                }""";

        RestAssured.given()
                .filter(openApiValidationFilter)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .put("/users/{id}", 1)
                .then()
                .statusCode(200);
    }
}