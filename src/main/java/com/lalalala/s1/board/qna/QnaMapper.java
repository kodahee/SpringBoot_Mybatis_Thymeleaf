package com.lalalala.s1.board.qna;

import com.lalalala.s1.board.BoardMapper;
import com.lalalala.s1.board.BoardVO;

public interface QnaMapper extends BoardMapper {
	
	public int setReplyInsert(BoardVO boardVO) throws Exception;
	
	public int setReplyUpdate(BoardVO boardVO) throws Exception;
	
	public int setRefUpdate(BoardVO boardVO) throws Exception;

}
