package site.metacoding.red.domain.users;

import java.util.List;

public interface UsersDao {
	public void insert(Users users);
	public List<Users> findAll();
	public Users findById(Integer id);
	public Users findByUsername(String username);
	public void update(Users users);
	public void deleteById(Integer id);
}
