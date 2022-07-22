package com.mywebsite.webbanhang.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mywebsite.webbanhang.model.Provider;
import com.mywebsite.webbanhang.model.Role;
import com.mywebsite.webbanhang.model.User;
import com.mywebsite.webbanhang.model.dto.UserRegistrationDto;
import com.mywebsite.webbanhang.repository.RoleRepository;
import com.mywebsite.webbanhang.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	private UserRepository userRepository;
	private RoleRepository roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
		super();
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	@Override
	public User save(UserRegistrationDto registrationDto, long id) {
		Role role = roleRepository.findById(id).get();
		Set<Role> setRole = new HashSet<Role>();
		setRole.add(role);
		User user = new User(registrationDto.getFirstName(), 
				registrationDto.getLastName(), registrationDto.getEmail(),
				passwordEncoder.encode(registrationDto.getPassword()), setRole);
		
		return userRepository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		User user = userRepository.getUserByUsername(username);
		if(user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));		
	}
	
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

	@Override
	public List<User> listAllUser() {
		return userRepository.findAll();
	}

	@Override
	public void deleteAccountById(long id) {
		userRepository.deleteById(id);
	}

	@Override
	public User getAccountById(long id) {
		return userRepository.findById(id).get();
	}

	@Override
	public User updateAccountById(User user) {
		return userRepository.save(user);
	}

	@Override
	public Page<User> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
			Sort.by(sortField).descending();
		
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.userRepository.findAll(pageable);
	}

	@Override
	public User getUserByEmail(String email) {
		return userRepository.getUserByUsername(email);
	}

	public void processOAuthPostLogin(String email, String firstName, String lastName) {
        User existUser = userRepository.getUserByUsername(email);
         
        if (existUser == null) {
            User newUser = new User();
            newUser.setProvider(Provider.GOOGLE);        
            newUser.setFirstname(firstName);
			newUser.setLastname(lastName);
			newUser.setEmail(email);
            userRepository.save(newUser);        
        }
		
		return;
    }
	
}
