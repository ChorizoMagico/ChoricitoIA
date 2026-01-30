import java.util.LinkedList;
import java.util.List;

public class Memory {

    private final int MAX_SIZE = 10;
    private final LinkedList<String> recentTopics = new LinkedList<>();

    public void remember(String topic) {
        recentTopics.add(topic);
        if (recentTopics.size() > MAX_SIZE) {
            recentTopics.removeFirst();
        }
    }

    public boolean alreadyTalkedAbout(String topic) {
        return recentTopics.contains(topic);
    }

    public List<String> getRecentTopics() {
        return List.copyOf(recentTopics);
    }
}
