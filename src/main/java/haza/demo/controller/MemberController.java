package haza.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import haza.demo.domain.Member;
import haza.demo.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/member")
@Slf4j
public class MemberController {
	
	@Autowired MemberRepository repository;

	@GetMapping("/login")
	public String login() {
		log.info("[GET] login");
		return "member/login";
	}
	
	@GetMapping("/join")
	public String join() {
		return "member/join";
	}
	
	@PostMapping("/join")
	public String join(@ModelAttribute Member member) {
		repository.memberSave(member);
		return "member/join-result";
	}
	@PostMapping("/join-result")
	public String result() {
		return "member/join-result";
	}
}
