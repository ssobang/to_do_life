package haza.demo.controller;

import java.time.LocalDate;
import java.util.List;

import org.h2.store.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import haza.demo.domain.WorkList;
import haza.demo.repository.ListRepository;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/list")
public class ListController {
	
	@Autowired ListRepository repository;
	
	@GetMapping("/daily")
	public String getDaily(Model model) {
		log.info("컨트롤러 실행됨1");
		List<WorkList> work = repository.findAll();
		model.addAttribute("workList", work);
//		model.addAttribute("date", work.get(0).getDate());
		log.info("컨트롤러 실행됨2");
		return "list/daily";
	}
	
	@GetMapping("/date")
	public String Date(@RequestParam String date , Model model) {
		log.info("컨트롤러 실행됨1");
		List<WorkList> work = repository.findByDate(date);
		model.addAttribute("workList", work);
		model.addAttribute("date", date);
		log.info("컨트롤러 실행됨2");
		return "list/daily";
	}
	
	
	
	@GetMapping("/manage")
	public String manage(Model model) {
		log.info("manage 컨트롤러 실행");
		List<WorkList> workTrue = repository.manageTrue();
		List<WorkList> workFalse = repository.manageFalse();
		model.addAttribute("workTrue", workTrue);
		model.addAttribute("workFalse", workFalse);
		log.info("manage 컨트롤러 종료");
		return "list/manage";
	}
	
	@GetMapping("/search")
	public String search(@RequestParam String search, Model model){
		log.info("search컨트롤러실행됨");
		List<WorkList> work = repository.search(search);
		model.addAttribute("search", work);
		model.addAttribute("searchword", search);
		log.info("{}", work);
		log.info("search컨트롤러끝남");
		return "list/searchList";
	}
	
	@PostMapping("/daily")
	public String postDaily() {
		
		return "list/daily";
	}
	
	@GetMapping("/weekly")
	public String weekly(Model model) {
		log.info("weekly 컨트롤러 실행");
		LocalDate today = LocalDate.now();
		
		List<WorkList> work = repository.findAll();
		model.addAttribute("day+3", work);

		log.info("weekly 컨트롤러 종료");
		return "list/weekly";
	}


	// 일정 등록 폼
	@GetMapping("/add")
	public String add() {
	    return "list/addList";
	}
	// 일정 등록
	@PostMapping("/add")
	public String addList(@ModelAttribute WorkList workList, RedirectAttributes attributes) {
	    log.info("addList실행");
	    repository.save(workList);
	    attributes.addAttribute("work", workList.getMemo());
	    return "redirect:/list/daily";
	}
	@GetMapping("/delete/{workNo}")
	public String delete(@PathVariable Long workNo) {
		log.info("delete 컨트롤러 실행됨");
		repository.deleteWork(workNo);
		log.info("delete 컨트롤러 끝남");
		return "redirect:/list/daily";
	}
	@GetMapping("/todo/{workNo}")
	public String todo(@PathVariable Long workNo, Model model) {
		log.info("컨트롤러실행됨");
		WorkList work = repository.findByNo(workNo).get();
		model.addAttribute("work", work);
		log.info("컨트롤러끝남");
		return "list/todo";
	}
	@GetMapping("/edit/{workNo}")
	public String editForm(@PathVariable Long workNo, Model model) {
		log.info("edit컨트롤러실행됨");
		WorkList work = repository.findByNo(workNo).get();
		model.addAttribute("work", work);
		log.info("edit컨트롤러끝남");
		return "list/editList";
	}
	@PostMapping("/edit/{workNo}")
	public String edit(@PathVariable Long workNo, WorkList workList, Model model) {
		log.info("[POST] edit 실행");
		WorkList work = repository.update(workNo, workList);
		model.addAttribute("work", work);
		return "list/todo";
	}
	

	
}
