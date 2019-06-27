package me.kay.service;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.Value;

@JsonRpcService("/rpc")
public interface InviteService {

    @Value
    @AllArgsConstructor
    @ToString
    class Invite{

        public String inviter;
        public double totalBets;
        public long invitees;
        public double totalRewards;
        public double withdrawn;
    }

    // TUshe5oxyeFqRfvvdyBNUwmPXjKEJbqUsT
    Invite getInvite(@JsonRpcParam(value = "address") String address);
}
