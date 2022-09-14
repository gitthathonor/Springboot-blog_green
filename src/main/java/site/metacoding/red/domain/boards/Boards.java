package site.metacoding.red.domain.boards;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.metacoding.red.web.dto.request.boards.UpdateDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Boards {
	private Integer id;
	private String title;
	private String content;
	private Integer usersId;
	private Timestamp createdAt; // At 시분초 다 표현할 때, Dt 년월일만 표현
	
	public Boards(Integer usersId,String title, String content) {
		this.usersId = usersId;
		this.title = title;
		this.content = content;
	}
	
	public Boards(String title, String content, Integer id) {
		this.title = title;
		this.content = content;
		this.id = id;
	}
	
	public void updateBoards(UpdateDto updateDto) {
		this.title = updateDto.getTitle();
		this.content = updateDto.getContent();
	}
	
}
