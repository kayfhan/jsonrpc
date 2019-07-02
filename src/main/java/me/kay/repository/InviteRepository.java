package me.kay.repository;

import me.kay.entity.Invite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InviteRepository extends MongoRepository<Invite, String> {

    Page<Invite> findByInviter(String Inviter, PageRequest pageRequest);
}
