package com.example.dusTmq.repository.board;

import com.example.dusTmq.domain.board.BoardDetailVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardDetailVO, Long >, IBoardRepository {

//Query문에서 update,delete을  쓸대 @Modifying 어노테이션을 줘야 하이버네이트에서 인식하고 기능을 수행한다.
    @Modifying
    @Query("delete from BoardDetailVO  b where b.id in :ids")
    void deleteAllByIdInQuery(@Param("ids") List<Long> ids);
}
