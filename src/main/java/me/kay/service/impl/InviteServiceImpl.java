package me.kay.service.impl;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import me.kay.service.InviteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
@AutoJsonRpcServiceImpl
public class InviteServiceImpl implements InviteService {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Invite getInvite(String address) {
        /**
         * curl -H "Content-Type:application/json" -d '{"id":"1","jsonrpc":"2.0","method":"getInvite",
         * "params":{"address":"TUshe5oxyeFqRfvvdyBNUwmPXjKEJbqUsT"}}' http://localhost:9090/rpc
         */
        System.out.println("---------------mongoTemplate: " + address + " .");
        Invite invite = mongoTemplate.findOne(new Query(Criteria.where("inviter").is(address)), Invite.class, "invite");
        System.out.println(invite.toString());
        return invite;
    }
}
