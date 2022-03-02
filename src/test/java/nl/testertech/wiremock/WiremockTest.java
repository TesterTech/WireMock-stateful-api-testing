package nl.testertech.wiremock;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static io.restassured.RestAssured.given;

@WireMockTest(httpPort = 9999)
public class WiremockTest {

    private RequestSpecification requestSpec;

    @BeforeEach
    public void createRequestSpec() {
        requestSpec = new RequestSpecBuilder().
                setBaseUri("http://192.168.122.81").
                setPort(7000).
                build();
    }

    @Test
    public void toDoListScenario() {
        given().
                spec(requestSpec).
                when().
                get("/").
                then().
                assertThat().
                statusCode(200);
    }

    @Test
    public void getServiceInfo() {

        String serviceBody = given()
                .spec(requestSpec)
                .when()
                .get("/info")
                .then()
                .extract().body().asString();

        Assertions.assertTrue(serviceBody.equalsIgnoreCase("Yes! Up and running."));

    }

}
