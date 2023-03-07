package com.pi.Centrale_Achat.controller;

import com.pi.Centrale_Achat.entities.ERole;
import com.pi.Centrale_Achat.entities.Role;
import com.pi.Centrale_Achat.entities.User;
import com.pi.Centrale_Achat.payload.request.ForgotPassRequest;
import com.pi.Centrale_Achat.payload.request.LoginRequest;
import com.pi.Centrale_Achat.payload.request.ResetPassRequest;
import com.pi.Centrale_Achat.payload.request.SignupRequest;
import com.pi.Centrale_Achat.payload.response.JwtResponse;
import com.pi.Centrale_Achat.payload.response.MessageResponse;
import com.pi.Centrale_Achat.repositories.RoleeRepo;
import com.pi.Centrale_Achat.repositories.UserRepo;
import com.pi.Centrale_Achat.security.jwt.JwtUtils;
import com.pi.Centrale_Achat.serviceImpl.UserDetailsImpl;
import com.pi.Centrale_Achat.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepo userRepository;

    @Autowired
    RoleeRepo roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired

    UserServiceImpl userService;





    ////////////
    //Enregistrement d'un compte
    ////////////

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest, HttpServletRequest request) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = null;
        try {
            user = new User(signUpRequest.getNom(), signUpRequest.getPrenom(), signUpRequest.getUsername(),
                    signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()),
                    new SimpleDateFormat("dd/MM/yyyy").parse(signUpRequest.getDateNaissance())
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Génération du code de vérification
        String verificationCode = UUID.randomUUID().toString();

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null || strRoles.isEmpty()) {
            Role clientrole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
                    .orElseGet(() -> roleRepository.save(new Role(ERole.ROLE_CUSTOMER)));
            roles.add(clientrole);
        } else {
            for (String roleName : strRoles) {
                if (ERole.ROLE_OPERATOR.name().equals(roleName)) {

                    Role operateurrole = roleRepository.findByName(ERole.ROLE_OPERATOR)
                            .orElseGet(() -> roleRepository.save(new Role(ERole.ROLE_OPERATOR)));
                    roles.add(operateurrole);
                } else if (ERole.ROLE_CUSTOMER.name().equals(roleName)) {
                    Role clientrole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
                            .orElseGet(() -> roleRepository.save(new Role(ERole.ROLE_CUSTOMER)));
                    roles.add(clientrole);
                } else if (ERole.ROLE_SUPPLIER.name().equals(roleName)) {
                    Role fournisseurrole = roleRepository.findByName(ERole.ROLE_SUPPLIER)
                            .orElseGet(() -> roleRepository.save(new Role(ERole.ROLE_SUPPLIER)));
                    roles.add(fournisseurrole);
                }
                else if (ERole.ROLE_DELIVERY.name().equals(roleName)) {
                    Role livreurrole = roleRepository.findByName(ERole.ROLE_DELIVERY)
                            .orElseGet(() -> roleRepository.save(new Role(ERole.ROLE_DELIVERY)));
                    roles.add(livreurrole);
                }
            }
        }

        user.setRoles(roles);

        user.setVerificationCode(verificationCode);

        userRepository.save(user);

        // Envoi du mail de confirmation avec le code de vérification
        String appUrl = request.getScheme() + "://" + request.getServerName();
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(signUpRequest.getEmail());
        mailMessage.setSubject("Inscription réussie");
        mailMessage.setText("Bonjour " + signUpRequest.getNom() + ",\n\nVotre inscription sur notre site a été effectuée avec succès. Pour vérifier votre compte, veuillez cliquer sur le lien suivant : " + appUrl + "/ToBuySignUp/verify?code=" + verificationCode);
        javaMailSender.send(mailMessage);

        return ResponseEntity.ok(new MessageResponse("Un email de confirmation vous a été envoyé à l'adresse " + signUpRequest.getEmail() + ". Veuillez suivre les instructions pour vérifier votre compte."));

    }



















    ////////////
    //Verfication par code
    ////////////

    @GetMapping("/SignUp/verify")
    public ResponseEntity<?> verifySignUp(@RequestParam String code) {
        User user = userRepository.findByVerificationCode(code);
        if (user == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Code de vérification invalide."));
        }

        user.setVerified(true);
        user.setVerificationCode(null);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("Votre compte a été vérifié avec succès."));
    }



    ////////////
    //Connexion
    ////////////

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);


        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        if (!userDetails.isVerified()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("Error: Account not verified!"));
        }
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        ResponseCookie jwt = jwtUtils.generateJwtCookie(userDetails);
        // Vérifier si le mot de passe correspond au mot de passe stocké dans la base de données
        if (!encoder.matches(loginRequest.getPassword(), userDetails.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("Invalid password!"));
        }


        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwt.toString())
                .body(new JwtResponse(jwt.toString(),
                        userDetails.getId(),
                        userDetails.getUsername(),
                        userDetails.getEmail(),
                        userDetails.getProfile(),
                        roles));
    }





     ////////////
    //Déconnexion
     ////////////
    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("You've been signed out!"));
    }



    ////////////
    // Envoyer un email avec code de vérification (Reset Password)
    ////////////
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPassRequest forgotPassRequest, HttpServletRequest request) {
        if (!userRepository.existsByEmail(forgotPassRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("il n'existe aucun utilisateur avec cet email, vérifiez vos données"));
        } else {
        User user = userRepository.findUserByEmail(forgotPassRequest.getEmail());
        // Générer un code de vérification aléatoire

            String code = UUID.randomUUID().toString();

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(forgotPassRequest.getEmail());
            mailMessage.setText(" Bonjour vous trouvez ci-joint un code de vérification pour réinitialiser votre mot de passe : "+ code);
            javaMailSender.send(mailMessage);

            // Enregistrer le code de vérification dans la base de données
            user.setResetpasswordcode(code);
            user.setVerified(false);

            userRepository.save(user);


        return ResponseEntity.ok().body(new MessageResponse("Verification code sent successfully!"));
    }}

    ////////////
    //Modification de Password
    ////////////

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPassRequest resetPassRequest) {
        // Vérifier si l'utilisateur existe
        if (!userRepository.existsByEmail(resetPassRequest.getEmail()) ){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("il n'existe aucun utilisateur avec cet email, vérifiez vos données"));
        }
        User user = userRepository.findUserByEmail(resetPassRequest.getEmail());


        // Vérifier si le code de vérification est valide
        if (!user.getResetpasswordcode().equals(resetPassRequest.getResetpasswordcode())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Invalid verification code!"));
        }

        // Enregistrer le nouveau mot de passe dans la base de données
        user.setPassword(encoder.encode(resetPassRequest.getPassword()));
        user.setVerified(true);
        userRepository.save(user);

        return ResponseEntity.ok().body(new MessageResponse("Password reset successful!"));
    }






}

