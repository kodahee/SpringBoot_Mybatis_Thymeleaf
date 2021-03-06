package com.lalalala.s1.board.qna;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.lalalala.s1.board.BoardFileVO;
import com.lalalala.s1.board.BoardService;
import com.lalalala.s1.board.BoardVO;
import com.lalalala.s1.util.FileManager;
import com.lalalala.s1.util.Pager;
@Service
public class QnaService implements BoardService{

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
		//1. qna table insert
		int result = qnaMapper.setInsert(boardVO);
		
		//2. qna Ref update
		result = qnaMapper.setRefUpdate(boardVO);
		
		//3. File Save
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
		// TODO Auto-generated method stub
		return qnaMapper.setUpdate(boardVO);
	}

	@Override
	public int setDelete(BoardVO boardVO) throws Exception {
		// TODO Auto-generated method stub
		//1. files table??? fileName ??????
		//2. qna table?????? ??? ?????? 
		//3. HDD??? ???????????? ??????
		return qnaMapper.setDelete(boardVO);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int setReplyInsert(BoardVO boardVO, MultipartFile [] files)throws Exception{
		//boardVO.num = ????????? ?????????
		
		//1. step update
		int result = qnaMapper.setReplyUpdate(boardVO);
		
		//2. reply insert
		result = qnaMapper.setReplyInsert(boardVO);
		
		//3. File Hdd??? ??????
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