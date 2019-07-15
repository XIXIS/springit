package com.xixis.springit.service;

import com.xixis.springit.domain.Link;
import com.xixis.springit.domain.User;
import com.xixis.springit.repository.LinkRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LinkService {

  private LinkRepository linkRepository;
  private UserService userService;

  public LinkService(LinkRepository linkRepository, UserService userService){
    this.linkRepository = linkRepository;
    this.userService = userService;
  }

  public List<Link> findAll(){
    return linkRepository.findAll();
  }

  public Link createLink(Link link) {

    Optional<User> user = userService.findByEmail(
        ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail()
    );
    user.ifPresent(link::setUser);
    return linkRepository.save(link);

  }

  public Optional<Link> findLink(Long linkId) {
    return linkRepository.findById(linkId);
  }

  public void deleteLink(Long linkId) {
      linkRepository.deleteById(linkId);
  }



}
