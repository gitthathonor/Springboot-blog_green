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
	//좋아요(insert)
	public void insertLike(Likes likes);
	//좋아요취소(delete)
	//LikeDto(select)
	public LikeDto showLikeCount(@Param("usersId") Integer usersId, @Param("boardsId") Integer boardsId);
}
