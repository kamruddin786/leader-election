package com.kamruddin.leader_election;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "leader")
public class Leader {
    @Id
    private String id;
    private String leaderId;
    private long timestamp;
}
