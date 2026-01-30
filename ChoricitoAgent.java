public class ChoricitoAgent {

    private final Memory memory;
    private final LLMClient llm;

    public ChoricitoAgent() {
        this.memory = new Memory();
        this.llm = new LLMClient();
    }

    public String think() {
        String prompt = """
        Ask a playful, friendly question to start a conversation.
        Avoid repetition.
        Keep it short.
        """;

        String output = llm.generate(Personality.SYSTEM_PROMPT, prompt);
        memory.remember(output);
        return output;
    }
}
