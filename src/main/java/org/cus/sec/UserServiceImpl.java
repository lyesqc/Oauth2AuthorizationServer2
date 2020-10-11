package org.cus.sec;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService {
	
	@Autowired
    private BCryptPasswordEncoder bcpe;
	
	@Autowired
	EventPublisherService  enventPublisher;
	/*
	@Autowired
	private UserDao userDao;
*/
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		List<GrantedAuthority> listAuth = AuthorityUtils.createAuthorityList( new String[]{"ROLE_ADIM"});
		User user = new User( "user1",bcpe.encode("password1"),getAuthority()); //userDao.findByUsername(userId);
		enventPublisher.publish();
		if(user == null){
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return user ;//new org.springframework.security.core.userdetails.User(user.getUsername(), bcpe.encode(user.getPassword()), getAuthority());
	}

	private List getAuthority() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}

	
}