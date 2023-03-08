package com.pi.Centrale_Achat.repositories;

import com.pi.Centrale_Achat.entities.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.HashSet;

public interface ChatRepo extends JpaRepository<Chat, Integer> {
    HashSet<Chat> getChatByFirstUserName(String username);

    HashSet<Chat> getChatBySecondUserName(String username);

    HashSet<Chat> getChatByFirstUserNameAndSecondUserName(String firstUserName, String secondUserName);

    HashSet<Chat> getChatBySecondUserNameAndFirstUserName(String firstUserName, String secondUserName);
}
