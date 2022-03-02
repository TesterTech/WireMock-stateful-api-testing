package nl.testertech.wiremock;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static io.restassured.RestAssured.given;

@WireMockTest(httpPort = 7000)
public class ScenarioStateTest {

    public static final String URI = "http://127.0.0.1";
    private RequestSpecification requestSpec;

    @BeforeEach
    public void createRequestSpec() {
        requestSpec = new RequestSpecBuilder().
                setBaseUri(URI).
                setPort(7777).
                build();
    }

    @Test
    public void setUpScenario() {
        Assertions.assertEquals("Yes! Mock is working",
                String.valueOf(given().spec(requestSpec).
                when().get("/info").
                then().assertThat().extract().body().asString()));
    }

    @Test
    public void makeACleanState() {
        Assertions.assertEquals("Cleaned the state",
                String.valueOf(given().spec(requestSpec).
                        when().delete("/").
                        then().assertThat().extract().body().asString()));

        Map<String, String> scenarioMaps = given().spec(requestSpec).
                when().get("/__admin/scenarios").
                then().assertThat().extract().jsonPath().getMap("$");

        Set<Map.Entry<String, String>> entrySet = scenarioMaps.entrySet();
        ArrayList<Object> arrayList = new ArrayList<>();
        Map.Entry o = (Map.Entry)entrySet.toArray()[0];
        arrayList.addAll((Collection<?>) o.getValue());
        LinkedHashMap<Object, Object> scenarioMap = new LinkedHashMap<>();
        for(int i = 0; i<arrayList.size();i++){
            scenarioMap.putAll((LinkedHashMap)arrayList.get(i));
        }
        Assertions.assertTrue(scenarioMaps.containsKey("scenarios"));
        Assertions.assertEquals("clean state", scenarioMap.get("state"));
    }
}
