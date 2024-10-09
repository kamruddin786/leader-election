package com.kamruddin.leader_election;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    @Autowired
    private LeaderElectionService leaderElectionService;

    @Scheduled(fixedRate = 5000) // Check every 5 seconds
    public void maintainLeadership() {
        if (leaderElectionService.attemptLeadership()) {
            System.out.println("This instance is the leader. " + System.getenv("HOSTNAME"));
            // Perform leader-specific tasks here
        } else {
            System.out.println("This instance is not the leader. " + System.getenv("HOSTNAME"));
        }
    }
}
