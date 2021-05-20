package com.lalalala.s1.board.qna;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.lalalala.s1.board.BoardFileVO;
import com.lalalala.s1.board.BoardService;
import com.lalalala.s1.board.BoardVO;
import com.lalalala.s1.util.FileManager;
import com.lalalala.s1.util.Pager;

public class QnaService implements BoardService {
	
	@Autowired
	private QnaMapper qnaMapper;
	@Autowired
	private FileManager fileManager;

	@Override
	public List<BoardVO> getList(Pager pager) throws Exception {
		pager.makeRow();
		pager.makeNum(qnaMapper.getTotalCount(pager));
		return qnaMapper.getList(pager);
	}

	@Override
	public BoardVO getSelect(BoardVO boardVO) throws Exception {
		qnaMapper.setHitUpdate(boardVO);
		return qnaMapper.getSelect(boardVO);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int setInsert(BoardVO boardVO, MultipartFile[] files) throws Exception {
		// 1. qna table insert
		int result = qnaMapper.setInsert(boardVO);
		
		// 2. qna Ref update
		result = qnaMapper.setRefUpdate(boardVO);
		
		// 3. file save
		String filePath= "upload/qna/";
		
		for(MultipartFile multipartFile:files) {
			if(multipartFile.getSize()==0) {
				continue;
			}
			String fileName= fileManager.save(multipartFile, filePath);
			System.out.println(fileName);
			BoardFileVO boardFileVO = new BoardFileVO();
			boardFileVO.setFileName(fileName);
			boardFileVO.setOriName(multipartFile.getOriginalFilename());
			boardFileVO.setNum(boardVO.getNum());
			qnaMapper.setFileInsert(boardFileVO);
		}
		
		return result;
	}

	@Override
	public int setUpdate(BoardVO boardVO) throws Exception {
		return qnaMapper.setUpdate(boardVO);
	}

	@Override
	public int setDelete(BoardVO boardVO) throws Exception {
		// 1. files table의 fileName 조회
		// 2. qna table에서 글 삭제
		// 3. HDD에 파일들을 삭제
		return qnaMapper.setDelete(boardVO);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int setReplyInsert(BoardVO boardVO, MultipartFile [] files) throws Exception {
		// boardVO.num = 부모의 글 번호
		
		// 1. step update
		int result = qnaMapper.setRefUpdate(boardVO);
		
		// 2. reply insert
		result = qnaMapper.setReplyInsert(boardVO);
		
		// 3. file save
		String filePath= "upload/qna/";
		
		for(MultipartFile multipartFile:files) {
			if(multipartFile.getSize()==0) {
				continue;
			}
			String fileName= fileManager.save(multipartFile, filePath);
			System.out.println(fileName);
			BoardFileVO boardFileVO = new BoardFileVO();
			boardFileVO.setFileName(fileName);
			boardFileVO.setOriName(multipartFile.getOriginalFilename());
			boardFileVO.setNum(boardVO.getNum());
			qnaMapper.setFileInsert(boardFileVO);
		}
		
		return result;
		
	}

}
