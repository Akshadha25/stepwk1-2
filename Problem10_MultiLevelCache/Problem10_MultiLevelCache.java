import java.util.*;

class VideoData {
    String videoId;
    String content;

    VideoData(String videoId, String content) {
        this.videoId = videoId;
        this.content = content;
    }
}

public class Problem10_MultiLevelCache {
    private LinkedHashMap<String, VideoData> L1; // memory cache
    private HashMap<String, VideoData> L2; // SSD cache simulation
    private HashMap<String, VideoData> L3; // DB
    private int L1Capacity;

    private int L1Hits = 0, L2Hits = 0, L3Hits = 0;

    public Problem10_MultiLevelCache(int L1Capacity) {
        this.L1Capacity = L1Capacity;
        L1 = new LinkedHashMap<>(L1Capacity, 0.75f, true);
        L2 = new HashMap<>();
        L3 = new HashMap<>();
    }

    public void addVideo(VideoData video) {
        L3.put(video.videoId, video); // all videos in DB
    }

    public VideoData getVideo(String videoId) {
        if (L1.containsKey(videoId)) {
            L1Hits++;
            return L1.get(videoId);
        }
        if (L2.containsKey(videoId)) {
            L2Hits++;
            promoteToL1(videoId, L2.get(videoId));
            return L2.get(videoId);
        }
        if (L3.containsKey(videoId)) {
            L3Hits++;
            VideoData data = L3.get(videoId);
            L2.put(videoId, data);
            promoteToL1(videoId, data);
            return data;
        }
        return null; // not found
    }

    private void promoteToL1(String key, VideoData data) {
        if (L1.size() >= L1Capacity) {
            String eldest = L1.keySet().iterator().next();
            L1.remove(eldest);
        }
        L1.put(key, data);
    }

    public void printStats() {
        int total = L1Hits + L2Hits + L3Hits;
        System.out.println("\nCache Stats:");
        System.out.println("L1 Hits: " + L1Hits);
        System.out.println("L2 Hits: " + L2Hits);
        System.out.println("L3 Hits: " + L3Hits);
        System.out.println("Total Accesses: " + total);
        System.out.println("L1 Hit Rate: " + ((double)L1Hits/total*100) + "%");
    }

    public static void main(String[] args) {
        Problem10_MultiLevelCache cache = new Problem10_MultiLevelCache(2);

        cache.addVideo(new VideoData("v1","Video 1 content"));
        cache.addVideo(new VideoData("v2","Video 2 content"));
        cache.addVideo(new VideoData("v3","Video 3 content"));

        System.out.println("Access v1: " + cache.getVideo("v1").content);
        System.out.println("Access v2: " + cache.getVideo("v2").content);
        System.out.println("Access v1: " + cache.getVideo("v1").content);
        System.out.println("Access v3: " + cache.getVideo("v3").content);

        cache.printStats();
    }
}