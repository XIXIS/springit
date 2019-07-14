package com.xixis.springit.controller;

import com.xixis.springit.domain.Link;
import com.xixis.springit.domain.LinkAPIResponse;
import com.xixis.springit.repository.LinkRepository;
import com.xixis.springit.domain.APIResponse;
import com.xixis.springit.validators.LinkValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/links", consumes = MediaType.APPLICATION_JSON_VALUE)
public class LinkController {

  private LinkRepository linkRepository;

  public LinkController(LinkRepository linkRepository) {
    this.linkRepository = linkRepository;
  }

  // list

  @GetMapping("/")
  public List<Link> list() {
    return linkRepository.findAll();
  }

  // CRUD

  @PostMapping(value = "/create")
  public ResponseEntity<?> create(@Valid @RequestBody Link link, BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {
      LinkValidator linkValidator = new LinkValidator(bindingResult);
      String error = linkValidator.validateCreateEditLink();
      return new ResponseEntity<>(
          new APIResponse(error, "Validation Failed!"),
          HttpStatus.UNPROCESSABLE_ENTITY
      );
    }

    Link newLink = linkRepository.save(link);
    return new ResponseEntity<>(
        new LinkAPIResponse(newLink, "Link created successfully"),
        HttpStatus.OK
    );

  }


  // links/{id}
  @GetMapping("/{linkId}")
  public ResponseEntity<?> read(@PathVariable Long linkId) {

    Optional<Link> link = linkRepository.findById(linkId);

    if (link.isPresent()) {
      return new ResponseEntity<>(
          new LinkAPIResponse(link.get(), "Link was found"),
          HttpStatus.OK
      );
    }

    String error = "Link with Id " + linkId + " does not exist";
    return new ResponseEntity<>(
        new LinkAPIResponse(error, "Error finding Link!"),
        HttpStatus.NOT_FOUND
    );

  }


  @PutMapping("/{linkId}")
  public ResponseEntity<?> update(@PathVariable Long linkId, @Valid @RequestBody Link link, BindingResult bindingResult) {

    Optional<Link> optionalLink = linkRepository.findById(linkId);

    if (optionalLink.isPresent()) {

      if (bindingResult.hasErrors()) {

        LinkValidator linkValidator = new LinkValidator(bindingResult);
        String error = linkValidator.validateCreateEditLink();

        return new ResponseEntity<>(
            new LinkAPIResponse(error, "Validation Failed!"),
            HttpStatus.UNPROCESSABLE_ENTITY
        );

      }else {

        Link existingLink = optionalLink.get();
        existingLink.setTitle(link.getTitle());
        existingLink.setUrl(link.getUrl());

        Link updatedLink = linkRepository.save(existingLink);
        return new ResponseEntity<>(
            new LinkAPIResponse(updatedLink, "Link with Id "+linkId+ " was updated successfully"),
            HttpStatus.OK
        );
      }

    }

    String error = "Link with Id " + linkId + " does not exist";
    return new ResponseEntity<>(
        new APIResponse(error, "Error finding Link!"),
        HttpStatus.NOT_FOUND
    );


  }

  @DeleteMapping("/{linkId}")
  public ResponseEntity<?> delete(@PathVariable Long linkId) {

    try {

      linkRepository.deleteById(linkId);
      return new ResponseEntity<>(
          new LinkAPIResponse("Successfully deleted link with Id " + linkId),
          HttpStatus.OK
      );

    } catch (Exception ex) {
      return new ResponseEntity<>(
          new LinkAPIResponse(
              "Link with Id " + linkId + " does not exist",
              "Deletion Failed."
          ),
          HttpStatus.NOT_FOUND
      );
    }

  }

}
