package org.phoenix.paywall.service;

import org.phoenix.paywall.model.User;
import org.phoenix.paywall.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class UserService {

  @Autowired
  private UserRepository userAccountRepository;

  private String cleanupStr(String str) {
      if(str == "" || str == null) {
          return str;
      }
      //remove consecutive commas
      str = str.replaceAll(",,", ",");
      //if first character is comma, remove it
      if (str.charAt(0) == ',') {
          str = str.substring(1);
      }
      //if last character is comma, remove it
      if (str.charAt(str.length() - 1) == ',') {
          str = str.substring(0, str.length() - 1);
      }
      return str.trim().toLowerCase();
  }
  
  @Transactional
  public Integer removeAssociation(String username, String partyId) {
    //  System.out.println("Removing association between " + username + " and " + partyId);
      User user = userAccountRepository.findByUsername(username);
      if (user == null) {
          return 0;
      }
      //remove the partyId from the associatedPartyIds list then update the repository
      String[] associatedPartyIds = user.getAssociatedPartyIds().split(",");
      boolean contains = false;
      String newAssociatedPartyIds = "";
      for (String associatedPartyId : associatedPartyIds) {
          if (!associatedPartyId.equals(partyId)) {
              newAssociatedPartyIds += associatedPartyId + ",";
          } else {
              contains = true;
          }
      }
      if (!contains) {
          return 0;
      }
      newAssociatedPartyIds = cleanupStr(newAssociatedPartyIds);
      user.setAssociatedPartyIds(newAssociatedPartyIds);

      String[] addedPartyIds = user.getAddedPartyIds().split(",");
      //check if addedPartyIds contains partyId, if not then add it
      contains = false;
      for (String addedPartyId : addedPartyIds) {
          if (addedPartyId.equals(partyId)) {
              contains = true;
          }
      }
      if (!contains) {
          String comma = ",";
          if (user.getAddedPartyIds().equals("")) {
              comma = "";
          }
          newAssociatedPartyIds = user.getAddedPartyIds() + comma + partyId;
          newAssociatedPartyIds = cleanupStr(newAssociatedPartyIds);
          user.setAddedPartyIds(newAssociatedPartyIds);
      }
      userAccountRepository.save(user);
      return 1;
  }
  
  @Transactional
  public Integer addAssociation(String username, String partyId) {
      User user = userAccountRepository.findByUsername(username);
      if (user == null) {
          return 0;
      }

      String[] addedPartyIds = user.getAddedPartyIds().split(",");
      boolean contains = false;
      String newAddedPartyIds = "";
      for (String addedPartyId : addedPartyIds) {
          if (addedPartyId.equals(partyId)) {
              contains = true;
          } else {
            newAddedPartyIds += addedPartyId + ",";
          }
      }
      if (!contains) {
          return 0;
      }
      newAddedPartyIds = cleanupStr(newAddedPartyIds);
      user.setAddedPartyIds(newAddedPartyIds);
      //add the partyId to the associatedPartyIds if it is not already there
      String[] associatedPartyIds = user.getAssociatedPartyIds().split(",");
      String newAssociatedPartyIds = "";
      contains = false;
      for (String associatedPartyId : associatedPartyIds) {
          if (associatedPartyId.equals(partyId)) {
              contains = true;
          }
          newAssociatedPartyIds += associatedPartyId + ",";
      }
      if (!contains) {
          newAssociatedPartyIds += partyId + ",";
          newAssociatedPartyIds = cleanupStr(newAssociatedPartyIds);
          user.setAssociatedPartyIds(newAssociatedPartyIds);
      } else {
          return 0;
      }
      return 1;
  }
  
}
