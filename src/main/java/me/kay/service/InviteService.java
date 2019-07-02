package me.kay.service;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;
import me.kay.entity.Invite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

@Repository
@JsonRpcService("/rpc")
public interface InviteService {

    // TUshe5oxyeFqRfvvdyBNUwmPXjKEJbqUsT
    Invite getInvite(@JsonRpcParam(value = "address") String address);

    Page<Invite> findAllInvite(String address, PageRequest pageRequest);

    Page<Invite> findAll(String inviter, PageRequest pageRequest);
}
