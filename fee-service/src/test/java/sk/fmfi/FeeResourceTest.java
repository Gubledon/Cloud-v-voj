package sk.fmfi.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import sk.fmfi.model.Fee;
import sk.fmfi.resource.dto.TransactionDTO;

import java.math.BigDecimal;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class FeeResourceTest {

    private static final BigDecimal MINIMAL_FEE = BigDecimal.valueOf(0.01);
    private static final BigDecimal FEE = BigDecimal.valueOf(2);
    private static ObjectMapper OBJECT_MAPPER;

    @BeforeAll
    public static void setup() {
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
    }

    @Test
    @TestSecurity
    public void testFetchFeesNotAuthenticatedUser() {
        Response getRequest = given().when()
                .get("/fee");
        assertThat(getRequest.statusCode(), is(401));
    }

    @Test
    @TestSecurity
    public void testCreateFeeNotAuthenticatedUser() {
        Response getRequest = given().when()
                .header("Content-Type", "application/json")
                .post("/fee");
        assertThat(getRequest.statusCode(), is(401));
    }

    @Test
    @TestSecurity(user = "notAuthorizedUser", roles = {"testRole"})
    public void testFetchFeesNotAuthorizedUser() {
        Response getRequest = given().when()
                .get("/fee");
        assertThat(getRequest.statusCode(), is(403));
    }

    @Test
    @TestSecurity(user = "notAuthorizedUser", roles = {"testRole"})
    public void testCreateFeeNotAuthorizedUser() {
        Response getRequest = given().when()
                .header("Content-Type", "application/json")
                .post("/fee");
        assertThat(getRequest.statusCode(), is(403));
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"admin", "user"})
    public void testCreateMinimalFee() {
        String iban = "BG18RZBB91550123456789";

        TransactionDTO transactionDTO = TransactionDTO.builder()
                .iban(iban)
                .amount(BigDecimal.valueOf(100))
                .transactionId("667bb620-04c6-45cb-8746-80cf932c7a01")
                .build();

        assertFeeCreation(transactionDTO);

        List<Fee> fees = assertFeeFetch(iban);
        assertNotNull(fees);
        assertThat(fees.size(), is(1));

        Fee fee = fees.get(0);
        assertEquals(fee.getIban(), iban);
        assertThat(fee.getAmount(), comparesEqualTo(MINIMAL_FEE));
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"admin", "user"})
    public void testCreateFee() {
        String iban = "BE71096123456769";

        TransactionDTO transactionDTO = TransactionDTO.builder()
                .iban(iban)
                .amount(BigDecimal.valueOf(30000))
                .transactionId("8103c587-7fce-4c2d-aa11-7a711a91b7e5")
                .build();

        assertFeeCreation(transactionDTO);

        List<Fee> fees = assertFeeFetch(iban);
        assertNotNull(fees);
        assertThat(fees.size(), is(1));

        Fee fee = fees.get(0);
        assertEquals(fee.getIban(), iban);
        assertThat(fee.getAmount(), comparesEqualTo(FEE));
    }

    private void assertFeeCreation(TransactionDTO transactionDTO) {
        try {
            Response postRequest = given().when()
                    .header("Content-Type", "application/json")
                    .body(OBJECT_MAPPER.writeValueAsString(transactionDTO))
                    .post("/fee");

            assertThat(postRequest.statusCode(), is(200));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error when creating a new fee");
        }
    }

    public List<Fee> assertFeeFetch(String iban) {
        try {
            Response getRequest = given().when()
                    .get("/fee?iban=" + iban);
            assertThat(getRequest.statusCode(), is(200));

            String responseBody = getRequest.body().prettyPrint();
            return OBJECT_MAPPER.readValue(responseBody, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error when fetching fees");
        }
    }
}