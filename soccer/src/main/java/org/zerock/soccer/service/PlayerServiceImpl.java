package org.zerock.soccer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.soccer.dto.PageRequestDTO;
import org.zerock.soccer.dto.PageResultDTO;
import org.zerock.soccer.dto.PlayerDTO;
import org.zerock.soccer.entity.Player;
import org.zerock.soccer.entity.PlayerImage;
import org.zerock.soccer.entity.PlayerMember;
import org.zerock.soccer.repository.PlayerImageRepository;
import org.zerock.soccer.repository.PlayerRepository;
import org.zerock.soccer.repository.ReviewRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService{
    private final PlayerRepository playerRepository;
    private final PlayerImageRepository playerImageRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    @Override
    public Long register(PlayerDTO playerDTO){
        Map<String, Object> entityMap = dtoToEntity(playerDTO);
        Player player = (Player) entityMap.get("player");
        List<PlayerImage> playerImageList = (List<PlayerImage>) entityMap.get("imgList");
        playerRepository.save(player);
        playerImageList.forEach(movieImage -> {
            playerImageRepository.save(movieImage);
        });
        return player.getPno();
    }

    @Override
    public PageResultDTO<PlayerDTO, Object[]> getList(PageRequestDTO pageRequestDTO){
        Function<Object[], PlayerDTO> fn = (arr -> entitiesToDTO(
                (Player) arr[0],
                (List<PlayerImage>) (Arrays.asList((PlayerImage) arr[1])),
                (Double) arr[2],
                (Long) arr[3])
        );
        Page<Object[]> result = playerRepository.searchPage(
                pageRequestDTO.getType(),
                pageRequestDTO.getKeyword(),
                pageRequestDTO.getPageable(Sort.by("pno").descending())
        );
        return new PageResultDTO<>(result, fn);
    }

    @Override
    public PlayerDTO getPlayer(Long pno){
        List<Object[]> result = playerRepository.getPlayerWithAll(pno);
        Player player = (Player) result.get(0)[0];
        List<PlayerImage> playerImageList = new ArrayList<>();
        result.forEach(arr -> {
            PlayerImage playerImage = (PlayerImage) arr[1];
            playerImageList.add(playerImage);
        });
        Double avg = (Double) result.get(0)[2];
        Long reviewCnt = (Long) result.get(0)[3];
        return entitiesToDTO(player, playerImageList,avg,reviewCnt);
    }

    @Transactional
    @Override
    public void remove(Long pno){
        playerImageRepository.deleteByMno(pno);
        reviewRepository.deleteByPno(pno);
        playerRepository.deleteById(pno);
    }
    @Transactional
    @Override
    public void modify(PlayerDTO playerDTO){
        Player player = playerRepository.getOne(playerDTO.getPno());
        player.changeTeam(playerDTO.getTeam());
        player.changeHistory(playerDTO.getHistory());
        playerRepository.save(player);
    }
}
