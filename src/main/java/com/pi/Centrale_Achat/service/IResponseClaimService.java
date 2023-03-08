package com.pi.Centrale_Achat.service;

import com.pi.Centrale_Achat.entities.ResponseClaim;

public interface IResponseClaimService {

    ResponseClaim createResponseClaim(ResponseClaim responseClaim);


    ResponseClaim updateResponseClaim(ResponseClaim responseClaim);

    void deleteResponseClaim(Integer idResponseClaim);

    ResponseClaim assignResponseClaimToRequestClaim(Integer idResponse, Integer idRequestClaim);
}
