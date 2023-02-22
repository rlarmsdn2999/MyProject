package org.zerock.soccer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.soccer.dto.PageRequestDTO;
import org.zerock.soccer.dto.PlayerDTO;
import org.zerock.soccer.security.dto.ClubAuthMemberDTO;
import org.zerock.soccer.service.PlayerService;

@Controller
@RequestMapping("/soccer")
@RequiredArgsConstructor
public class SoccerController {
    private final PlayerService playerService;

    @GetMapping("/")
    public String index(){
        return "/soccer/list";
    }

    @GetMapping("/register")
    public void register(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMember){

    }

    @PostMapping("/register")
    public String register(PlayerDTO playerDTO, RedirectAttributes redirectAttributes){
        Long pno = playerService.register(playerDTO);
        redirectAttributes.addFlashAttribute("msg",pno);
        return "redirect:/soccer/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){
        model.addAttribute("result", playerService.getList(pageRequestDTO));
    }

    @GetMapping({"/read", "/modify"})
    public void read(long pno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model){
        PlayerDTO playerDTO = playerService.getPlayer(pno);
        model.addAttribute("dto", playerDTO);
    }
    @PostMapping("/remove")
    public String remove(long pno, RedirectAttributes redirectAttributes){
        playerService.remove(pno);
        redirectAttributes.addFlashAttribute("msg", pno);
        return "redirect:/soccer/list";
    }
    @PostMapping("/modify")
    public String modify(PlayerDTO dto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, RedirectAttributes redirectAttributes){
        playerService.modify(dto);
        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("type", requestDTO.getType());
        redirectAttributes.addAttribute("keyword", requestDTO.getKeyword());
        redirectAttributes.addAttribute("pno", dto.getPno());
        return "redirect:/soccer/read";
    }
}
