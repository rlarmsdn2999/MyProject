package org.zerock.mreview.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.mreview.dto.EnterDTO;
import org.zerock.mreview.dto.MovieDTO;
import org.zerock.mreview.dto.PageRequestDTO;
import org.zerock.mreview.security.dto.ClubAuthMemberDTO;
import org.zerock.mreview.service.EnterService;

@Controller
@RequestMapping("/enter")
@RequiredArgsConstructor
public class EnterController {
    private final EnterService enterService;

    @GetMapping("/register")
    public void register(){

    }

    @PostMapping("/register")
    public String register(EnterDTO enterDTO, RedirectAttributes redirectAttributes){
        Long mno = enterService.register(enterDTO);
        redirectAttributes.addFlashAttribute("msg",mno);
        return "redirect:/enter/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){
        model.addAttribute("result", enterService.getList(pageRequestDTO));
    }

    @GetMapping({"/read", "/modify"})
    public void read(long mno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model,
                     @AuthenticationPrincipal ClubAuthMemberDTO clubAuthMember){
        EnterDTO enterDTO = enterService.getEnter(mno);
        model.addAttribute("dto", enterDTO);
    }
    @PostMapping("/remove")
    public String remove(long mno, RedirectAttributes redirectAttributes){
        enterService.remove(mno);
        redirectAttributes.addFlashAttribute("msg", mno);
        return "redirect:/enter/list";
    }
    @PostMapping("/modify")
    public String modify(EnterDTO dto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, RedirectAttributes redirectAttributes){
        enterService.modify(dto);
        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("mno", dto.getMno());
        return "redirect:/enter/read";
    }
}
