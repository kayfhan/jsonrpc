package me.kay.service.impl;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import me.kay.api.InviteApi;
import me.kay.entity.Invite;
import me.kay.repository.InviteRepository;
import me.kay.service.ClientMessageService;
import me.kay.service.InviteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@AutoJsonRpcServiceImpl
public class InviteServiceImpl implements InviteService {

    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    private ClientMessageService clientMessageService;
    @Autowired
    private InviteRepository inviteRepository;

    @Override
    public Invite getInvite(String address) {
        /**
         * curl -H "Content-Type:application/json" -d '{"id":"1","jsonrpc":"2.0","method":"getInvite",
         * "params":{"address":"TUshe5oxyeFqRfvvdyBNUwmPXjKEJbqUsT"}}' http://localhost:9090/rpc
         */
        System.out.println("---------------mongoTemplate: " + address + " .");
        Invite invite = mongoTemplate.findOne(new Query(Criteria.where("inviter").is(address)), Invite.class, "invite");
        System.out.println(invite.toString());

        clientMessageService.sendToTopic("/topic/invite", invite);

        return invite;
    }

    @Override
    public Page<Invite> findAllInvite(String inviter, PageRequest pageRequest) {

        Sort sort = Sort.by(Sort.Direction.DESC, "totalBets");
        Pageable pageable = PageRequest.of(pageRequest.getPageNumber(), pageRequest.getPageSize(), sort);

        System.out.println("---------------mongoTemplate: " + inviter + " .");
        Invite invite = new Invite();
        //创建匹配器，即如何使用查询条件
        ExampleMatcher matcher = ExampleMatcher.matching() //构建对象
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING) //改变默认字符串匹配方式：模糊查询
                .withIgnoreCase(true) //改变默认大小写忽略方式：忽略大小写
                .withMatcher("inviter", ExampleMatcher.GenericPropertyMatchers.contains()) //采用“包含匹配”的方式查询
                .withIgnorePaths("pageNum", "pageSize");  //忽略属性，不参与查询

        //创建实例
        Example<Invite> example = Example.of(invite, matcher);
        Page<Invite> invitePage = inviteRepository.findAll(example, pageable);

        return invitePage;
    }

    public Page<Invite> findAll(String inviter, PageRequest pageRequest){
        Sort sort = Sort.by(Sort.Direction.DESC, "totalBets");
        Pageable pageable = PageRequest.of(pageRequest.getPageNumber(), pageRequest.getPageSize(), sort);

        Integer pageNum = pageRequest.getPageNumber();
        Integer pageSize = pageRequest.getPageSize();

        List<AggregationOperation> operations = new ArrayList<>();
        if (!StringUtils.isEmpty(inviter)) {
            //Pattern pattern = Pattern.compile("^.*" + studentReqVO.getName() + ".*$", Pattern.CASE_INSENSITIVE);
            Criteria criteria = Criteria.where("inviter").is(inviter);
            operations.add(Aggregation.match(criteria));
        }
        long totalCount;
        //获取满足添加的总页数
        if (null != operations && operations.size() > 0) {
            Aggregation aggregationCount = Aggregation.newAggregation(operations);  //operations为空，会报错
            AggregationResults<Invite> resultsCount = mongoTemplate.aggregate(aggregationCount, "invite", Invite.class);
            totalCount = resultsCount.getMappedResults().size();
        } else {
            List<Invite> list = mongoTemplate.findAll(Invite.class);
            totalCount = list.size();
        }

        operations.add(Aggregation.skip((long) pageNum * pageSize));
        operations.add(Aggregation.limit(pageSize));
        operations.add(Aggregation.sort(Sort.Direction.DESC, "totalBets"));
        Aggregation aggregation = Aggregation.newAggregation(operations);
        AggregationResults<Invite> results = mongoTemplate.aggregate(aggregation, "invite", Invite.class);

        //查询结果集
        Page<Invite> studentPage = new PageImpl(results.getMappedResults(), pageable, totalCount);
        return studentPage;
    }
}
