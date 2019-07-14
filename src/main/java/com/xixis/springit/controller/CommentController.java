package com.xixis.springit.controller;

import com.xixis.springit.domain.CommentListAPIResponse;
import com.xixis.springit.domain.RoleListAPIResponse;
import com.xixis.springit.repository.CommentRepository;
import com.xixis.springit.repository.RoleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {
  private CommentRepository commentRepository;

  public CommentController(CommentRepository commentRepository) {
    this.commentRepository = commentRepository;
  }

  // list

  @GetMapping(value = "/comments", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> listByLink() {
    return new ResponseEntity<>(
        new CommentListAPIResponse(commentRepository.findAll(), "All comments listed"),
        HttpStatus.OK
    );
  }

  @GetMapping(value = "/comments/link/{linkId}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> listByLink(@PathVariable Long linkId) {
    return new ResponseEntity<>(
        new CommentListAPIResponse(commentRepository.findByLinkId(linkId), "All comments for link listed"),
        HttpStatus.OK
    );
  }

}
