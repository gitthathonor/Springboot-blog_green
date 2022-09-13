package site.metacoding.red.domain.boards;

import java.util.List;

import site.metacoding.red.web.dto.response.boards.MainDto;

public interface BoardsDao {
	public void insert(Boards boards);
	public List<Boards> findAll();
	public List<MainDto> findMainBoards(Integer page, String keyword);
	public Boards findById(Integer id);
	public void update(Boards boards);
	public void deleteById(Integer id);
	public void updateNullData(Integer usersId);
}
