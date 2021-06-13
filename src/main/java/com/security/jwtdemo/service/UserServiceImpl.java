package com.security.jwtdemo.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.security.jwtdemo.model.User;

@Service
public class UserServiceImpl implements UserService {

  @Override
  // @PreAuthorize("hasRole('ADMIN')")
  public List<User> getAllUsers() {
    // TODO Auto-generated method stub
    List<User> users = new ArrayList<>();
    User u1 = new User("1", "sam");
    users.add(u1);
    return users;
  }

}
