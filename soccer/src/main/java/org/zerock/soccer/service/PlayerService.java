package org.zerock.soccer.service;

import org.zerock.soccer.dto.PageRequestDTO;
import org.zerock.soccer.dto.PageResultDTO;
import org.zerock.soccer.dto.PlayerDTO;
import org.zerock.soccer.dto.PlayerImageDTO;
import org.zerock.soccer.entity.Player;
import org.zerock.soccer.entity.PlayerImage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface PlayerService {
    Long register(PlayerDTO playerDTO);
    PageResultDTO<PlayerDTO, Object[]> getList(PageRequestDTO requestDTO);
    PlayerDTO getPlayer(Long pno);
    void remove(Long pno);

    void modify(PlayerDTO playerDTO);

    default Map<String, Object> dtoToEntity(PlayerDTO playerDTO){
        Map<String, Object> entityMap = new HashMap<>();
        Player player = Player.builder()
                .pno(playerDTO.getPno())
                .name(playerDTO.getName())
                .contury(playerDTO.getContury())
                .team(playerDTO.getTeam())
                .history(playerDTO.getHistory())
                .build();
        entityMap.put("player", player);
        List<PlayerImageDTO> imageDTOList = playerDTO.getImageDTOList();
        if(imageDTOList != null && imageDTOList.size() > 0){
            List<PlayerImage> playerImageList = imageDTOList.stream().map(movieImageDTO -> {
                PlayerImage playerImage = PlayerImage.builder()
                        .path(movieImageDTO.getPath())
                        .imgName(movieImageDTO.getImgName())
                        .uuid(movieImageDTO.getUuid())
                        .player(player)
                        .build();
                return playerImage;
            }).collect(Collectors.toList());
            entityMap.put("imgList", playerImageList);
        }
        return entityMap;
    }

    default PlayerDTO entitiesToDTO(Player player, List<PlayerImage> playerImages, Double avg, Long reviewCnt){
        PlayerDTO playerDTO = PlayerDTO.builder()
                .pno(player.getPno())
                .name(player.getName())
                .contury(player.getContury())
                .team(player.getTeam())
                .history(player.getHistory())
                .regDate(player.getRegDate())
                .modDate(player.getModDate())
                .build();
        List<PlayerImageDTO> playerImageDTOList = playerImages.stream().map(playerImage -> {
            return PlayerImageDTO.builder().imgName(playerImage.getImgName())
                    .path(playerImage.getPath())
                    .uuid(playerImage.getUuid())
                    .build();
        }).collect(Collectors.toList());
        playerDTO.setImageDTOList(playerImageDTOList);
        playerDTO.setAvg(avg);
        playerDTO.setReviewCnt(reviewCnt.intValue());
        return playerDTO;
    }
}
