package site.metacoding.red.domain.boards;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Likes {
	private Integer id;
	private Integer usersId;
	private Integer boardsId;
	private Timestamp createdAt;
}
