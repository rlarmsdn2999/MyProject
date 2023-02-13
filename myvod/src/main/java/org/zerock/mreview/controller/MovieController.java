package org.zerock.mreview.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.mreview.dto.MovieDTO;
import org.zerock.mreview.dto.PageRequestDTO;
import org.zerock.mreview.entity.Member;
import org.zerock.mreview.repository.MovieRepository;
import org.zerock.mreview.security.dto.ClubAuthMemberDTO;
import org.zerock.mreview.service.MovieService;

@Controller
@RequestMapping("/movie")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;
    private final MovieRepository movieRepository;

    @GetMapping("/register")
    public void register(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMember){

    }

    @PostMapping("/register")
    public String register(MovieDTO movieDTO, RedirectAttributes redirectAttributes){
        Long mno = movieService.register(movieDTO);
        redirectAttributes.addFlashAttribute("msg",mno);
        return "redirect:/movie/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){
        model.addAttribute("result", movieService.getList(pageRequestDTO));
    }

    @GetMapping({"/read", "/modify"})
    public void read(long mno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model,
                     @AuthenticationPrincipal ClubAuthMemberDTO clubAuthMember){
        MovieDTO movieDTO = movieService.getMovie(mno);
        model.addAttribute("dto", movieDTO);
    }
    @PostMapping("/remove")
    public String remove(long mno, RedirectAttributes redirectAttributes){
        movieService.remove(mno);
        redirectAttributes.addFlashAttribute("msg", mno);
        return "redirect:/movie/list";
    }
    @PostMapping("/modify")
    public String modify(MovieDTO dto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, RedirectAttributes redirectAttributes){
        movieService.modify(dto);
        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("mno", dto.getMno());
        return "redirect:/movie/read";
    }
    //https://github.com/Woongi9/MovieReviewCommunity
    //AIzaSyBqIyCY-u1xpQ2lnz06oyZsnfFBsCtn6-4
    //clientid = 203990318198-fkjjs7pnubior1qtqjabagiflpocn66g.apps.googleusercontent.com
    //password = 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
}
