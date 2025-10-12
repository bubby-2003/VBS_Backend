package com.cts.service.impl; 
import java.util.List; 
import java.util.stream.Collectors; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service; 
import com.cts.dto.AuthRequestDTO; 
import com.cts.dto.AuthResponseDTO; 
import com.cts.entity.Auth; 
import com.cts.exception.MissingFieldException; 
import com.cts.exception.ResourceNotFoundException; 
import com.cts.mapper.AuthMapper;  
import com.cts.repository.AuthRepository; 
import com.cts.service.AuthService; 
 
@Service 
public class AuthServiceImpl implements AuthService { 
	@Autowired 
	private AuthRepository authRepository;
	
	@Override 
	public List<AuthResponseDTO> getAll() { 
	 return authRepository.findAll().stream() 
	 .map(AuthMapper::toDTO) 
	 .collect(Collectors.toList()); 
	 }
	
	@Override 
	 public AuthResponseDTO getByEmail(String email) { 
	 Auth auth = authRepository.findById(email) 
	  .orElseThrow(() -> new ResourceNotFoundException("Auth not found with email: " + email)); 
	  return AuthMapper.toDTO(auth); 
	 } 
	
	@Override 
	  public String create(AuthRequestDTO authDto) { 
	  String defaultRole = "USER";  

	 try {
	 if (authRepository.existsById(authDto.getEmail())) { 
	 throw new MissingFieldException("Registration failed: Email already exists"); 
	 } 

	 Auth auth = AuthMapper.toEntity(authDto); 
	 auth.setRole(defaultRole); 

	 authRepository.save(auth); 
	 return "Registered successfully"; 
	  } catch (MissingFieldException e) { 
	 throw e; 
	  } catch (Exception e) { 
	  throw new RuntimeException("Unable to register: " + e.getMessage()); 
	 } 
	} 

	 @Override 
	  public AuthResponseDTO update(String email, AuthRequestDTO authDto) { 
	  Auth existing = authRepository.findById(email) 
	  .orElseThrow(() -> new ResourceNotFoundException("Auth not found with email: " + email));
	  if (authDto.getPassword() != null && !authDto.getPassword().trim().isEmpty()) { 
	  existing.setPassword(authDto.getPassword()); 
	 }
	  Auth updated = authRepository.save(existing); 
	  return AuthMapper.toDTO(updated); 
	}
	 
	 @Override
	 public void delete(String email) { 
	  Auth existing = authRepository.findById(email) 
	  .orElseThrow(() -> new ResourceNotFoundException("Auth not found with email: " + email)); 
	 authRepository.delete(existing); 
	 } 
	@Override 
	public String login(AuthRequestDTO authDto) { 
		 try{ 
		 Auth auth = authRepository.findByEmailAndPasswordAndRole(authDto.getEmail(), authDto.getPassword() , authDto.getRole()) 
		  .orElseThrow(() -> new ResourceNotFoundException("Invalid credentials")); 
		  String token = "TOKEN_FOR_" + auth.getEmail() + "_ROLE_" + auth.getRole(); 
		  return token; 
		  } catch (ResourceNotFoundException e) { 
		  return "Invalid credentials"; 
		  } catch (Exception e) { 
		  return "Login failed: " + e.getMessage(); 
		 } 
  }
}