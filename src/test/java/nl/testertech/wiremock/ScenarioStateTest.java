package nl.testertech.wiremock;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.Map.Entry;

import static io.restassured.RestAssured.given;

@WireMockTest(httpPort = 7000)
public class ScenarioStateTest {

    public static final String URI = "http://127.0.0.1";
    private static RequestSpecification requestSpec;
    private static LinkedHashMap<Object, Object> scenarioMapWithState;

    @BeforeEach
    public void createRequestSpec() {
        requestSpec = new RequestSpecBuilder().
                setBaseUri(URI).
                setPort(7777).
                build();
        cleanTheStateRestcall();
    }
    @Test
    public void makeACleanState() {
        loadScenarioStateFromRest();
        Assertions.assertEquals("clean state", scenarioMapWithState.get("state"));
    }
    @Test
    public void cleanStateGivesNoItems() {
        Assertions.assertEquals("[]",
                given().spec(requestSpec).
                        when().get("/").
                        then().extract().body().asString()
        );
    }
    @Test
    public void newItemGivesAFilledState() {
        final String requestBody = "{\"title\": \"todo-todo ... todo, todo-todo-todo.... \"}";
        given().spec(requestSpec).
                request().body(requestBody).
                when().post("/").
                then().extract().body().asString();
        loadScenarioStateFromRest();
        Assertions.assertEquals("filled state", scenarioMapWithState.get("state"));
    }
    private void cleanTheStateRestcall() {
        Assertions.assertEquals("Cleaned the state",
                given().spec(requestSpec).
                        when().delete("/").
                        then().extract().body().asString()
        );
    }
    private static void loadScenarioStateFromRest() {
        Map<String, String> scenarioMaps =
                given().spec(requestSpec).
                        when().get("/__admin/scenarios").
                        then().assertThat().extract().jsonPath().getMap("$");
        scenarioMapWithState = getScenarioMap(scenarioMaps);
    }
    private static LinkedHashMap<Object, Object> getScenarioMap(Map<String, String> scenarioMaps) {
        Set<Entry<String, String>> entrySet = scenarioMaps.entrySet();
        var o = (Entry) entrySet.toArray()[0];
        ArrayList<Object> arrayList = new ArrayList<>((Collection<?>) o.getValue());
        LinkedHashMap<Object, Object> scenarioMap = new LinkedHashMap<>();
        for (Object value : arrayList) scenarioMap.putAll((LinkedHashMap) value);
        return scenarioMap;
    }

}
