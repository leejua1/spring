package com.occamsrazor.web.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.occamsrazor.web.util.Messenger;

@RestController
@RequestMapping("/user")
public class UserController {
		@Autowired UserService userService;
		
		@PostMapping("/join")
		public Messenger add(@RequestBody User user) {
	int current = userService.count();
	//userService.add(user);
	userService.saveFile(user);
			//return userService.count()==current+1? Messenger.SUCCESS: Messenger.FAIL;
			return Messenger.SUCCESS;
		}
		
		@GetMapping("/list")
		public List<User> list(){
	//		return userService.list();
			return userService.readFile();
		}
	
	@PostMapping("/login")
	public Map<String,Object> login(@RequestBody User user) {
		Map<String,Object> returnMap = new HashMap<>();
		User logInedUser= userService.login(user);
		if (logInedUser != null) {
			returnMap.put("user", logInedUser);
			returnMap.put("messenger", Messenger.SUCCESS);
		}else {
			returnMap.put("messenger",Messenger.FAIL );
		}
		return returnMap;
	}
	
	@GetMapping("/detail/{userid}")
	public User detail(@PathVariable String userid) {
		return userService.detail(userid);
	}
	
	@PutMapping("/update")
	public Messenger update(@RequestBody User user) {
		return userService.update(user) ? Messenger.SUCCESS : Messenger.FAIL ;
	}
	
	@DeleteMapping("/remove/{userid}") 
	public Messenger remove(@PathVariable String userid) {
	return userService.remove(userid) ? Messenger.SUCCESS : Messenger.FAIL ;

	}
}
