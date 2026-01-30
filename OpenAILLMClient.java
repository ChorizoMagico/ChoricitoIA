import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class OpenAILLMClient {

    private static final String API_URL = "http://localhost:11434/v1/chat/completions";
    private final String apiKey;
    private final HttpClient client;

    public OpenAILLMClient() {
        this.apiKey = System.getenv("OPENAI_API_KEY");
        if (apiKey == null || apiKey.isBlank()) {
            throw new RuntimeException("OPENAI_API_KEY no encontrada");
        }
        this.client = HttpClient.newHttpClient();
    }

    public String generate(String systemPrompt, String userPrompt) {
        try {
            String body = """
            {
              "model": "gpt-4.1-mini",
              "temperature": 0.8,
              "max_tokens": 80,
              "messages": [
                {"role": "system", "content": "%s"},
                {"role": "user", "content": "%s"}
              ]
            }
            """.formatted(
                    escape(systemPrompt),
                    escape(userPrompt)
            );

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            return extractContent(response.body());

        } catch (Exception e) {
            e.printStackTrace();
            return "Pregunta honesta 👀: ¿crees que pensar demasiado nos hace más sabios o solo más inseguros?";
        }
    }

    // --- helpers ---

    private String extractContent(String json) {
        // parsing ultra simple (suficiente para prototipo)
        int start = json.indexOf("\"content\":\"") + 11;
        int end = json.indexOf("\"", start);
        return json.substring(start, end)
                .replace("\\n", "\n")
                .replace("\\\"", "\"");
    }

    private String escape(String s) {
        return s.replace("\"", "\\\"");
    }
}
