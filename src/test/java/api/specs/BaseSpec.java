package api.specs;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;

public class BaseSpec {

    public static RequestSpecification commonRequestSpec = with()
            .filter(new AllureRestAssured())
            .log().uri()
            .log().headers()
            .log().body()
            .contentType(ContentType.JSON)
            .baseUri("https://reqres.in")
            .basePath("/api");

    public static ResponseSpecification commonResponseSpec = new ResponseSpecBuilder()
            .log(LogDetail.STATUS)
            .log(LogDetail.BODY)
            .expectStatusCode(200)
            .build();

    public static ResponseSpecification commonResponseSpecWithoutExpStatusCode = new ResponseSpecBuilder()
            .log(LogDetail.STATUS)
            .log(LogDetail.BODY)
            .build();
}
