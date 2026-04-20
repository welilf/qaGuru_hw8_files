import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.InputStreamReader;
import java.io.Reader;

public class JsonParsingTest {

    private ClassLoader cl = JsonParsingTest.class.getClassLoader();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void jsonFileParsingTest() throws Exception {

        try (Reader reader = new InputStreamReader(cl.getResourceAsStream("got.json"))) {
            JsonNode rootNode = objectMapper.readTree(reader);

            Assertions.assertEquals("Game of Thrones", rootNode.get("title").asText());
            Assertions.assertEquals(8, rootNode.get("seasons").asInt());
            Assertions.assertEquals("Fantasy", rootNode.get("genres").get(0).asText());
            Assertions.assertEquals("Drama", rootNode.get("genres").get(1).asText());
            Assertions.assertEquals("Jon Snow", rootNode.get("characters").get(0).get("name").asText());
            Assertions.assertEquals("Tyrion Lannister", rootNode.get("characters").get(1).get("name").asText());
        }
    }
}





