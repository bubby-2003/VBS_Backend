package com.cts.service;
 
import com.cts.entity.Auth;
import java.util.List;
 
public interface AuthService {
    List<Auth> getAll();
    Auth getByEmail(String email);
    String create(Auth auth); // changed from Auth to String
    Auth update(String email, Auth auth);
    void delete(String email);
    String login(String email, String password, String role); // changed from Auth to String
}
