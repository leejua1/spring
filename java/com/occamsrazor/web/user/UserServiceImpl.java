package com.occamsrazor.web.user;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JOptionPane;

import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	private Map<String, Object> map;  //배열이다
	public final static String FILE_PATH="C:\\Users\\bit30\\spring-workspace\\occamsrazor\\src\\main\\resources\\static\\user\\";
	public UserServiceImpl() {
		map = new HashMap<>();
	}
	@Override
	public void add(User user) {
		map.put(user.getUserid(),user);
	}
	@Override
	public int count() {
		return map.size();
	}
	@Override
	public User login(User user) {
		User returnUser = null;
		if (map.containsKey(user.getUserid())) {
			User u = detail(user.getUserid());
			if(user.getPasswd().equals(u.getPasswd())) {
				returnUser = u;
			}
		}
		return returnUser;
	}
	@Override
	public User detail(String userid) {
		return (User) map.get(userid);
	}
	@Override
	public boolean update(User user) {
		map.replace(user.getUserid(), user);
		return true;
	}
	@Override
	public boolean remove(String userid) {
		map.remove(userid);
		return true;
	}
	@Override
	public List<User> list() {
		List<User> list = new ArrayList<>();
		@SuppressWarnings("rawtypes")
		Set set = map.entrySet(); //map에 있는 entry 반환 map도 순서가 없어서 순서가 없는 데이터 집합인 set에 담아야 한다
		@SuppressWarnings("rawtypes")
		Iterator it = set.iterator(); //Set에 포함된 객체에 접근할 수 있는 방법 제공 entry에 대한 정보를 알고있다
		while(it.hasNext()) { //Next가없을 때까지 반복
			@SuppressWarnings("unchecked")
			Map.Entry<String, User> e =  (Entry<String, User>) it.next(); //순서대로 리턴시킨다.
			list.add(e.getValue());//list라서 index가 생성된다
		}
		
		return list; //js에서 for문을 쓰기 때문에 list로 반환한다
	}
	@Override
	public void saveFile(User user) {
		
		try {
			File file = new File(FILE_PATH+"list.txt");
			@SuppressWarnings("resource")
			BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));//FileWriter(Object object,boolean append) 오버라이딩 하기 위해 boolean타입의 append 위치에 true를 입력한다.
					String message = user.toString();
					writer.write(message);
					writer.newLine();
					writer.flush(); 
			
			
		} catch (Exception e) {
			System.out.println("파일 입력 시 에러 발생");
		}
		
	}
	@Override
	public List<User> readFile() {
		List<User> userlist = new ArrayList<>();
		List<String> list = new ArrayList<>();
			try {
				File file = new File(FILE_PATH+"list.txt");
				BufferedReader reader = new BufferedReader(new FileReader(file));
				String message = "";
				while((message = reader.readLine()) !=null ) {
							list.add(message);
						}
						reader.close();
			} catch (Exception e) {
				System.out.println("파일 읽기에서 에러 발생");
			}
			User u = new User();
			for (int i = 0; i < list.size(); i++) {
				String[] arr = list.get(i).split(",");
				u.setUserid(arr[0]);
				u.setPasswd(arr[1]);
				u.setName(arr[2]);
				u.setSsn(arr[3]);
				u.setAddr(arr[4]);
				userlist.add(u);
			}
			
		return userlist;
	}
	
	




}
