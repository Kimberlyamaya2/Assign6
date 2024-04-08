import java.util.*;

class Election {
    private Map<String, Integer> candidateVotes;
    private PriorityQueue<Map.Entry<String, Integer>> maxHeap;

    public Election() {
        candidateVotes = new HashMap<>();
        maxHeap = new PriorityQueue<>((a, b) -> b.getValue() - a.getValue());
    }

    public void initializeCandidates(LinkedList<String> candidates) {
        for (String candidate : candidates) {
            candidateVotes.put(candidate, 0);
        }
    }

    public void castVote(String candidate) {
        if (candidateVotes.containsKey(candidate)) {
            candidateVotes.put(candidate, candidateVotes.get(candidate) + 1);
        } else {
            System.out.println("Invalid candidate!");
        }
        updateHeap();
    }

    public void castRandomVote() {
        Random random = new Random();
        List<String> candidates = new ArrayList<>(candidateVotes.keySet());
        String randomCandidate = candidates.get(random.nextInt(candidates.size()));
        castVote(randomCandidate);
    }

    public void rigElection(String candidate) {
        int remainingVotes = getTotalVotes() - candidateVotes.get(candidate);
        candidateVotes.put(candidate, getTotalVotes());
        updateHeap();
        System.out.println("Election rigged in favor of " + candidate);
    }

    public List<String> getTopKCandidates(int k) {
        List<String> topCandidates = new ArrayList<>();
        int count = 0;
        while (!maxHeap.isEmpty() && count < k) {
            topCandidates.add(maxHeap.poll().getKey());
            count++;
        }
        maxHeap.clear();
        return topCandidates;
    }

    public void auditElection() {
        for (Map.Entry<String, Integer> entry : candidateVotes.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
    }

    private void updateHeap() {
        maxHeap.clear();
        for (Map.Entry<String, Integer> entry : candidateVotes.entrySet()) {
            maxHeap.offer(entry);
        }
    }

    private int getTotalVotes() {
        int total = 0;
        for (int votes : candidateVotes.values()) {
            total += votes;
        }
        return total;
    }
}

class ElectionSystem {
    public static void main(String[] args) {
        LinkedList<String> candidates = new LinkedList<>(Arrays.asList(
                "Marcus Fenix", "Dominic Santiago", "Damon Baird", "Cole Train", "Anya Stroud"));

        Election election = new Election();
        election.initializeCandidates(candidates);

      //  int p = 5;
        //extra credit
        Random random = new Random();
        int numCandidates = candidates.size();
        int p = random.nextInt(10) + 1;


       // election.castVote("Cole Train");
     //   election.castVote("Cole Train");
     //   election.castVote("Marcus Fenix");
      //  election.castVote("Anya Stroud");
      //  election.castVote("Anya Stroud");

        //EC
        System.out.println("Number of candidates: " + numCandidates);
        System.out.println("Candidates: " + candidates);
        System.out.println("Number of electorate: " + p);

        // EC
        for (int i = 0; i < p; i++) {
            election.castRandomVote();
        }

        System.out.println("Top 3 candidates after 5 votes: " + election.getTopKCandidates(3));

        election.rigElection("Marcus Fenix");

        System.out.println("Top 3 candidates after rigging the election: " + election.getTopKCandidates(3));

        System.out.println("Audit of election:");
        election.auditElection();
    }
}
