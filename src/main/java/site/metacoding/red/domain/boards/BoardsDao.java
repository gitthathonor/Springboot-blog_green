package site.metacoding.red.domain.boards;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import site.metacoding.red.web.dto.response.boards.LikeDto;
import site.metacoding.red.web.dto.response.boards.MainDto;
import site.metacoding.red.web.dto.response.boards.PagingDto;

public interface BoardsDao {
	public void insert(Boards boards);
	public Boards findById(Integer id);
	public List<MainDto> findAll(@Param("startNum")int startNum, @Param("keyword")String keyword);
	public PagingDto paging(@Param("page")Integer page, @Param("keyword")String keyword);
	public void update(Boards boards);
	public void deleteById(Integer id);
	public void updateNullData(Integer usersId);
	
	// 좋아요 입력
	public void insertLike(@Param("usersId") Integer usersId, @Param("boardsId") Integer boardsId);
	
	// 좋아요 삭제
	public void deleteLike(@Param("usersId") Integer usersId, @Param("boardsId") Integer boardsId);
	
	// 좋아요 총 갯수 보여주기
	public List<LikeDto> likeCount(Integer boardsId);
	
}
