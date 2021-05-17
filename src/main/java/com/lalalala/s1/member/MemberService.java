package com.lalalala.s1.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lalalala.s1.board.BoardFileVO;
import com.lalalala.s1.util.FileManager;

@Service
public class MemberService {
	
	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private FileManager fileManager;

	public int setJoin(MemberVO memberVO, MultipartFile multipartFile) throws Exception {
		// 1. member table에 저장
		int result = memberMapper.setJoin(memberVO);
		
		// 2. HDD 저장
		String filePath= "upload/member/";
		
		if(multipartFile.getSize() != 0) {
			String fileName= fileManager.save(multipartFile, filePath);
			System.out.println(fileName);
			MemberFileVO memberFileVO = new MemberFileVO();
			memberFileVO.setUsername(memberVO.getUsername());
			memberFileVO.setFileName(fileName);
			memberFileVO.setOriName(multipartFile.getOriginalFilename());

			// 3. memberFile table에 저장
			memberMapper.setJoinFile(memberFileVO);
		}

		return result;
	}
	
	public MemberVO getLogin(MemberVO memberVO) throws Exception {
		return memberMapper.getLogin(memberVO);
	}
}
