package me.kay.api;

import lombok.extern.slf4j.Slf4j;
import me.kay.entity.Invite;
import me.kay.service.InviteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api")
public class InviteApi {

    @Autowired
    private InviteService inviteService;

    @RequestMapping(value = "/all_invite/{address}")
    public Page<Invite> findAllInviteByPage(
            @PathVariable(value = "address") String address,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "20") int size,
            @RequestParam(value = "sort", required = false, defaultValue = "-timeStamp") String sort){

        return inviteService.findAllInvite(address, new PageRequest(page, size, Sort.Direction.DESC, sort));
    }

    @RequestMapping(value = "/all/{address}")
    public Page<Invite> findAll(
            @PathVariable(value = "address") String address,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "20") int size,
            @RequestParam(value = "sort", required = false, defaultValue = "-timeStamp") String sort){

        return inviteService.findAll(address, new PageRequest(page, size, Sort.Direction.DESC, sort));
    }
}
