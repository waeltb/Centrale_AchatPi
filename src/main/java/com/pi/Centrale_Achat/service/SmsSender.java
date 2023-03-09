package com.pi.Centrale_Achat.service;

import com.pi.Centrale_Achat.entities.SmsRequest;

public interface SmsSender {

    void sendSms(SmsRequest smsRequest);
}
