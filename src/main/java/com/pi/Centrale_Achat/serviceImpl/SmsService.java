package com.pi.Centrale_Achat.serviceImpl;




import com.pi.Centrale_Achat.entities.SmsRequest;
import com.pi.Centrale_Achat.service.SmsSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
//@Transactional
@Transactional
@Slf4j
public class SmsService {
    private final SmsSender smsSender ;

   @Autowired
    public SmsService(@Qualifier("twilio") TwilioSmsSender smsSender){
        this.smsSender = smsSender;
    }
    public void sendSms(SmsRequest smsRequest ) {

        smsSender.sendSms(smsRequest);
    }

    public void sendSMS(String smsRecipient, String message) {
    }
}
