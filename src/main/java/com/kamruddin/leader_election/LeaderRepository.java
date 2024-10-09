package com.kamruddin.leader_election;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface LeaderRepository extends MongoRepository<Leader, String> {
}
