package com.doit.study;

import com.doit.study.board.domain.Pagination;
import com.doit.study.board.dto.BoardDto;
import com.doit.study.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Controller
@RequestMapping("/")
@Slf4j
public class HomeController {

    private final BoardService boardService;

    @GetMapping("/")
    public String list(@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
                       @RequestParam(value = "pageSize", required = false, defaultValue = "4") int pageSize,
                       Model m, HttpServletRequest request) throws Exception {
//        if(!loginCheck(request))
//            return "redirect:/login/login?toURL="+request.getRequestURL();

        BoardDto boardDto = new BoardDto();
        int totalRecordCount = boardService.getCount();
        Pagination pagination = new Pagination(currentPage, pageSize);
        pagination.setTotalRecordCount(totalRecordCount);

        m.addAttribute("pagination", pagination);
        m.addAttribute("list", boardService.getPage(pagination));
        m.addAttribute("board", boardDto);
        return "/index";
    }
}
