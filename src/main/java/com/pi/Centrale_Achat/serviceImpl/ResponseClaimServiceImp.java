package com.pi.Centrale_Achat.serviceImpl;

import com.pi.Centrale_Achat.entities.RequestClaim;
import com.pi.Centrale_Achat.entities.ResponseClaim;
import com.pi.Centrale_Achat.repositories.RequestClaimRepo;
import com.pi.Centrale_Achat.repositories.ResponseClaimRepo;
import com.pi.Centrale_Achat.service.IResponseClaimService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;



import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
public class ResponseClaimServiceImp implements IResponseClaimService {

    private final RequestClaimRepo requestClaimRepository;

    private final ResponseClaimRepo responseClaimRepository;

    public ResponseClaim createResponseClaim(ResponseClaim responseClaim){
        return responseClaimRepository.save(responseClaim);
    }


    public ResponseClaim updateResponseClaim(ResponseClaim responseClaim) {
        return  responseClaimRepository.save(responseClaim);
    }


    public void deleteResponseClaim(Integer idResponseClaim)
    {
        responseClaimRepository.deleteById(idResponseClaim);
    }


    public List<ResponseClaim> getAllResponseClaim() {
        return responseClaimRepository.findAll();
    }



    public ResponseClaim assignResponseClaimToRequestClaim(Integer idResponse, Integer idRequestClaim) {

        ResponseClaim e = responseClaimRepository.findById(idResponse).orElse(null);
        RequestClaim d = requestClaimRepository.findById(idRequestClaim).orElse(null);

        e.setRequestClaim(d);
        return responseClaimRepository.save(e);
    }


}
