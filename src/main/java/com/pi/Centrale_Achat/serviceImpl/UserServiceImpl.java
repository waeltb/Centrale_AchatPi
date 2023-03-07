package com.pi.Centrale_Achat.serviceImpl;


import com.pi.Centrale_Achat.repositories.RoleeRepo;
import com.pi.Centrale_Achat.repositories.UserRepo;
import com.pi.Centrale_Achat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepo userRepository;
    @Autowired
    RoleeRepo roleRepository;

//    @Autowired
//    UniversREP universREP;

    @Autowired
    PasswordEncoder encoder;

//    @Override
//    public User addUser(User c) {
//        return userRepository.save(c);
//    }

//    @Override
//    public void deleteUser(Long id) {
//        userRepository.deleteById(id);
//    }

//    @Override
//    public User updateUser(User c) {
//        return userRepository.save(c);
//    }
//
//    @Override
//    public User retrieveUser(Long id) {
//        return userRepository.findById(id).orElse(null);
//    }
//
//    @Override
//    public List<User> retrieveAllUsers() {
//        return (List<User>) userRepository.findAll();
//    }

//    public User updateUser(int userId, User updatedUser) {
//        User user = userRepository.findById_user(userId);
//        user.setNom(updatedUser.getNom());
//        user.setPrenom(updatedUser.getPrenom());
//        user.setEmail(user.getEmail());
//        user.setUsername(user.getUsername());
//        user.setPassword(encoder.encode(updatedUser.getPassword()));
//        user.setDateNaissance(updatedUser.getDateNaissance());
//        User savedUser = userRepository.save(user);
//        return savedUser;
//    }

//@Override
//    public University adduni (University u) {
//        return universREP.save(u);
//    }


}
