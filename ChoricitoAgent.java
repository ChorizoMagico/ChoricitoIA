public class ChoricitoAgent {

    private final Memory memory;
    private final OllamaLLMClient llm;
    private final MoltbookClient moltbook;

    public ChoricitoAgent() {
        this.memory = new Memory();
        this.llm = new OllamaLLMClient();
        this.moltbook = new MoltbookClient();
    }

    public void heartbeat() {
        moltbook.checkFeed();
    }

    public void postToMoltbook(String content) {
        String title = "Pregunta honesta 🦞";
        moltbook.createPost(title, content);
    }

    public boolean isReady() {
        return moltbook.isClaimed();
    }

    public String think() {
        String prompt = """
        Ask ONE playful, friendly question.
        Keep it short.
        Avoid insults.
        Prefer curiosity over statements.
        """;

        String output = llm.generate(Personality.SYSTEM_PROMPT, prompt);
        memory.remember(output);
        return output;
    }
}
