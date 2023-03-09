package com.pi.Centrale_Achat.controller;

import com.pi.Centrale_Achat.entities.ResponseClaim;
import com.pi.Centrale_Achat.service.IResponseClaimService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/responseClaim")
public class ResponseClaimController {

    private final IResponseClaimService responseClaimService;

    @PostMapping
    public ResponseClaim createResponseClaim(@RequestBody ResponseClaim responseClaim){
        return responseClaimService.createResponseClaim(responseClaim);
    }

    @PutMapping
    public ResponseClaim updateResponseClaim(@RequestBody ResponseClaim responseClaim){
        return responseClaimService.updateResponseClaim(responseClaim);
    }

    @DeleteMapping("/{idResponseClaim}")
    void deleteResponseClaim(@PathVariable("idResponseClaim") Integer idResponseClaim){
        responseClaimService.deleteResponseClaim(idResponseClaim);
    }


    @PutMapping("/assign/{idResponse}/{idRequestClaim}")
    public void assignResponseClaimToRequestClaim(@PathVariable("idResponse") Integer idResponse, @PathVariable("idRequestClaim") Integer idRequestClaim) {
        responseClaimService.assignResponseClaimToRequestClaim(idResponse,idRequestClaim);
    }




}
