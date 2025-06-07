package Service;

import Entity.User;
import Repository.UserRepository;

public class UserService {
	private UserRepository userRepository = new UserRepository();

	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email);
		}

	 public void updateUserInfo(String email, String phone, String encodedPassword) {
		 userRepository.updateByEmail(email, phone, encodedPassword);
	}
	 
	 public void updateProfileImage(String email, String fileName) {
		    userRepository.updateProfileImage(email, fileName);
		}

	 public void deleteUserByEmail(String email) {
			userRepository.deleteByEmail(email);
		}

}
