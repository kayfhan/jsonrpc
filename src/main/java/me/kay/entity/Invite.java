package me.kay.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "invite")
public class Invite {

    public String inviter;
    public double totalBets;
    public long invitees;
    public double totalRewards;
    public double withdrawn;
}
