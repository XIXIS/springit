package com.xixis.springit.controller;

import com.xixis.springit.domain.*;
import com.xixis.springit.repository.LinkRepository;
import com.xixis.springit.repository.UserRepository;
import com.xixis.springit.repository.VoteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class VoteController {

  private VoteRepository voteRepository;
  private LinkRepository linkRepository;
  private UserRepository userRepository;

  public VoteController(VoteRepository voteRepository, LinkRepository linkRepository, UserRepository userRepository) {
    this.voteRepository = voteRepository;
    this.linkRepository = linkRepository;
    this.userRepository = userRepository;
  }

  // list

  @GetMapping(value = "/votes", consumes = MediaType.APPLICATION_JSON_VALUE)
  public List<Vote> list() {
    return voteRepository.findAll();
  }

  // CRUD
  // @GetMapping("/link/{linkId}/direction/{direction}/user/{userId}/vote-count/{voteCount}" @PathVariable Long userId
  @GetMapping(value = "/votes/link/{linkId}/direction/{direction}/vote-count/{voteCount}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> vote(@PathVariable Long linkId, @PathVariable short direction, @PathVariable int voteCount) {
    Optional<Link> optionalLink = linkRepository.findById(linkId);
    if (optionalLink.isPresent()) {

      Link link = optionalLink.get();

      Vote vote = new Vote(direction, link);
      voteRepository.save(vote);

      int updatedVoteCount = voteCount + direction;
      link.setVoteCount(updatedVoteCount);
      linkRepository.save(link);

      return new ResponseEntity<>(
          new VoteAPIResponse(vote, "Voting was successful"),
          HttpStatus.OK
      );

    }

    return new ResponseEntity<>(
        new VoteAPIResponse("Error occurred when voting", "Voting was successful"),
        HttpStatus.UNPROCESSABLE_ENTITY
    );

  }

}
