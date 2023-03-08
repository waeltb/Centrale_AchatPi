package com.pi.Centrale_Achat.serviceImpl;


import com.pi.Centrale_Achat.entities.User;
import com.pi.Centrale_Achat.repositories.RoleeRepo;
import com.pi.Centrale_Achat.repositories.UserRepo;
import com.pi.Centrale_Achat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import static com.sun.mail.imap.protocol.BASE64MailboxDecoder.decode;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepo userRepository;
    @Autowired
    RoleeRepo roleRepository;


    @Autowired
    PasswordEncoder encoder;



    @Override
    public User updateUser(@AuthenticationPrincipal UserDetails userDetails, User updatedUser, String currentPassword) {
        String currentUsername = userDetails.getUsername();
        User user = userRepository.findUserByUsername(currentUsername);
        if (!encoder.matches(decode(currentPassword), user.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }
        user.setNom(updatedUser.getNom());
        user.setPrenom(updatedUser.getPrenom());
        user.setEmail(updatedUser.getEmail());
        user.setAddress(updatedUser.getAddress());
        user.setNumTel(user.getNumTel());
        User savedUser = userRepository.save(user);
        return savedUser;
    }



    @Override
    public User getMyProfile(UserDetails userDetails) {
        String cuurentname = userDetails.getUsername();
        User user = userRepository.findUserByUsername(cuurentname);
        return user;
    }



}
