package org.zerock.soccer.repository.searchRepository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.soccer.entity.*;

import java.util.List;
import java.util.stream.Collectors;

public class SearchRepositoryImpl extends QuerydslRepositorySupport implements SearchRepository{
    public SearchRepositoryImpl() {
        super(Player.class);
    }
    @Override
    public Page<Object[]> searchPage(String type, String keyword, Pageable pageable){
        QPlayer player = QPlayer.player;
        QReview review = QReview.review;
        QPlayerImage playerImage = QPlayerImage.playerImage;
        JPQLQuery<Player> query = from(player);
        query.leftJoin(playerImage).on(playerImage.player.pno.eq(player.pno)).groupBy(playerImage);
        query.leftJoin(review).on(review.player.eq(player));
        JPQLQuery<Tuple> tuple = query.select(player, playerImage, review.grade.avg().coalesce(0.0), review.count());
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        BooleanExpression expression = player.pno.gt(0L);
        booleanBuilder.and(expression);
        if(type != null){
            String[] typeArr = type.split("");
            BooleanBuilder conditionBuilder = new BooleanBuilder();
            for (String t:typeArr){
                switch (t){
                    case "t":
                        conditionBuilder.or(player.name.contains(keyword));
                        break;
                    case "w":
                        conditionBuilder.or(player.team.contains(keyword));
                        break;
                    case "c":
                        conditionBuilder.or(player.contury.contains(keyword));
                        break;
                }
            }
            booleanBuilder.and(conditionBuilder);
        }
        tuple.where(booleanBuilder);
        Sort sort = pageable.getSort();
        sort.stream().forEach(order -> {
            Order direction = order.isAscending()? Order.ASC: Order.DESC;
            String prop = order.getProperty();
            PathBuilder orderByExpression = new PathBuilder(Player.class, "player");
            tuple.orderBy(new OrderSpecifier(direction, orderByExpression.get(prop)));
        });
        tuple.groupBy(player);
        tuple.offset(pageable.getOffset());
        tuple.limit(pageable.getPageSize());
        List<Tuple> result = tuple.fetch();
        long count = tuple.fetchCount();
        return new PageImpl<Object[]>(
                result.stream().map(t -> t.toArray()).collect(Collectors.toList()),pageable,count);
    }
}
