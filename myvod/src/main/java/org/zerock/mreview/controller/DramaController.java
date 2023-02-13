package org.zerock.mreview.controller;

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
import org.zerock.mreview.dto.DramaDTO;
import org.zerock.mreview.dto.MovieDTO;
import org.zerock.mreview.dto.PageRequestDTO;
import org.zerock.mreview.security.dto.ClubAuthMemberDTO;
import org.zerock.mreview.service.DramaService;

@Controller
@RequestMapping("/drama")
@RequiredArgsConstructor
public class DramaController {
    private final DramaService dramaService;

    @GetMapping("/register")
    public void register(){

    }

    @PostMapping("/register")
    public String register(DramaDTO dramaDTO, RedirectAttributes redirectAttributes){
        Long mno = dramaService.register(dramaDTO);
        redirectAttributes.addFlashAttribute("msg",mno);
        return "redirect:/drama/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){
        model.addAttribute("result", dramaService.getList(pageRequestDTO));
    }

    @GetMapping({"/read", "/modify"})
    public void read(long dno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model,
                     @AuthenticationPrincipal ClubAuthMemberDTO clubAuthMember){
        DramaDTO dramaDTO = dramaService.getDrama(dno);
        model.addAttribute("dto", dramaDTO);
    }

    @PostMapping("/remove")
    public String remove(long dno, RedirectAttributes redirectAttributes){
        dramaService.remove(dno);
        redirectAttributes.addFlashAttribute("msg", dno);
        return "redirect:/drama/list";
    }
    @PostMapping("/modify")
    public String modify(DramaDTO dto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, RedirectAttributes redirectAttributes){
        dramaService.modify(dto);
        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("dno", dto.getDno());
        return "redirect:/drama/read";
    }
}
