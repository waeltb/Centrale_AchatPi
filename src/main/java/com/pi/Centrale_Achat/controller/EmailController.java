package com.pi.Centrale_Achat.controller;
import com.pi.Centrale_Achat.dto.Email;
import com.pi.Centrale_Achat.entities.Bill;
import com.pi.Centrale_Achat.entities.User;
import com.pi.Centrale_Achat.repositories.BillRepo;
import com.pi.Centrale_Achat.repositories.UserRepo;
import com.pi.Centrale_Achat.serviceImpl.MailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/api/mail")
@RequiredArgsConstructor
public class EmailController {
    private final MailServiceImpl mailService;
    private final BillRepo billRepo;

    @PostMapping("/send-email/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public void sendEmail(@AuthenticationPrincipal UserDetails userDetails,
                          @RequestBody Email emailMessage, @PathVariable("id") int id ) throws MessagingException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        Bill bill = billRepo.findById(id).orElse(null);
        if (bill != null && (currentUserName.equals(bill.getOrder().getUser().getUsername()))) {
            mailService.sendEmail(userDetails, emailMessage,id );
        } else {
            throw new AccessDeniedException("You are not authorized to delete this order");
        }
    }


}
