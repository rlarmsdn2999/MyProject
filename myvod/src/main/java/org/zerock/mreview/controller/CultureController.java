package org.zerock.mreview.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.mreview.dto.CultureDTO;
import org.zerock.mreview.dto.EnterDTO;
import org.zerock.mreview.dto.MovieDTO;
import org.zerock.mreview.dto.PageRequestDTO;
import org.zerock.mreview.service.CultureService;

@Controller
@RequestMapping("/culture")
@RequiredArgsConstructor
public class CultureController {
    private final CultureService cultureService;
    @GetMapping("/register")
    public void register(){

    }

    @PostMapping("/register")
    public String register(CultureDTO cultureDTO, RedirectAttributes redirectAttributes){
        Long mno = cultureService.register(cultureDTO);
        redirectAttributes.addFlashAttribute("msg",mno);
        return "redirect:/culture/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){
        model.addAttribute("result", cultureService.getList(pageRequestDTO));
    }

    @GetMapping({"/read", "/modify"})
    public void read(long mno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model){
        CultureDTO cultureDTO = cultureService.getCulture(mno);
        model.addAttribute("dto", cultureDTO);
    }
    @PostMapping("/remove")
    public String remove(long mno, RedirectAttributes redirectAttributes){
        cultureService.remove(mno);
        redirectAttributes.addFlashAttribute("msg", mno);
        return "redirect:/culture/list";
    }
    @PostMapping("/modify")
    public String modify(CultureDTO dto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, RedirectAttributes redirectAttributes){
        cultureService.modify(dto);
        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("mno", dto.getMno());
        return "redirect:/culture/read";
    }
}
