package com.kamruddin.leader_election;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class LeaderElectionService {

    @Autowired
    private MongoOperations mongoOperations;

    private final String leaderId = System.getenv("HOSTNAME");
    private static final long TIMEOUT = 10000; // 10 seconds

    public boolean attemptLeadership() {
        Query query = new Query(Criteria.where("_id").is("leader"));
        Update update = new Update()
                .set("leaderId", leaderId)
                .set("timestamp", Instant.now().toEpochMilli());

        Leader currentLeader = mongoOperations.findOne(query, Leader.class);

        if (currentLeader == null || isLeadershipExpired(currentLeader)) {
            if(currentLeader == null) {
                Leader leader = new Leader();
                leader.setId("leader");
                leader.setLeaderId(leaderId);
                leader.setTimestamp(Instant.now().toEpochMilli());
                mongoOperations.save(leader);
            }
            mongoOperations.findAndModify(
                    query,
                    update,
                    Leader.class
            );
            currentLeader = mongoOperations.findOne(query, Leader.class);
            return currentLeader != null && currentLeader.getLeaderId().equals(leaderId);
        } else {
            return currentLeader.getLeaderId().equals(leaderId);
        }
    }

    private boolean isLeadershipExpired(Leader leader) {
        long currentTime = Instant.now().toEpochMilli();
        return (currentTime - leader.getTimestamp()) > TIMEOUT;
    }
}
