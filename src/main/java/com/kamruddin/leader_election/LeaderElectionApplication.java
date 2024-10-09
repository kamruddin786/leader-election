package com.kamruddin.leader_election;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@SpringBootApplication
@EnableScheduling
@EnableMongoRepositories
public class LeaderElectionApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeaderElectionApplication.class, args);
	}

}


@RestController
@RequestMapping("/app")
class MainController{

//	private final LeaderContext  leaderContext;

//    MainController(LeaderContext leaderContext) {
//        this.leaderContext = leaderContext;
//    }

    @GetMapping("/details")
	public ResponseEntity<?> getDetails() {
		System.out.println("I am -> " + System.getenv("HOSTNAME"));
		return ResponseEntity.ok(Collections.singletonMap("status", "ok"));
	}
}
