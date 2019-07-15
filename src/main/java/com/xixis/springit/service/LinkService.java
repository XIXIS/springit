package com.xixis.springit.service;

import com.xixis.springit.domain.Link;
import com.xixis.springit.repository.LinkRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LinkService {

  private LinkRepository linkRepository;

  public LinkService(LinkRepository linkRepository){
    this.linkRepository = linkRepository;
  }

  public List<Link> findAll(){
    return linkRepository.findAll();
  }

  public Link createLink(Link link) {

    return linkRepository.save(link);

  }

  public Optional<Link> findLink(Long linkId) {
    return linkRepository.findById(linkId);
  }

  public void deleteLink(Long linkId) {
      linkRepository.deleteById(linkId);
  }



}
