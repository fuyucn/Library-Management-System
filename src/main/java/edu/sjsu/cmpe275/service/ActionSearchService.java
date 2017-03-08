package edu.sjsu.cmpe275.service;

import java.util.List;

import edu.sjsu.cmpe275.model.Action;

public interface ActionSearchService {
   

    List<Action> findActionsByUserId(Integer id);
    
   
}