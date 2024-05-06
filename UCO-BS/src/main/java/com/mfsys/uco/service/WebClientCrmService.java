package com.mfsys.uco.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mfsys.comm.exception.ApplicationException;
import com.mfsys.comm.exception.ApplicationExceptionMapper;
import org.apache.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class WebClientCrmService {
    private final WebClient webClientCrm;


    public WebClientCrmService(WebClient webClientCrm) {
        this.webClientCrm = webClientCrm;
    }
    public Object getUcoAccountBalance(String url,String porOrgacode) {
        return handleResponse(webClientCrm.get().uri(url).accept(MediaType.APPLICATION_JSON)
                        .header("SUS_USERCODE", porOrgacode)
                        .header("POR_ORGACODE", porOrgacode).retrieve().toEntity(Object.class),
                null);
    }
    public Object onboardCustomer(Map onBoardingData, String url, String porOrgaCode) {
        return handleResponse(webClientCrm.post().uri(url).bodyValue(onBoardingData).accept(MediaType.APPLICATION_JSON)
                        .header("SUS_USERCODE", porOrgaCode)
                        .header("POR_ORGACODE", porOrgaCode).retrieve()
                        .toEntity(Object.class),
                porOrgaCode);
    }

    private <T> T handleResponse(Mono<ResponseEntity<T>> responseMono, String porgaCode) {
        try {
            ResponseEntity<T> response = responseMono.block();
            return response.getBody();
        } catch (WebClientResponseException e) {
            ApplicationExceptionMapper.APIError errorDetails = parseErrorDetails(e);
            throw new ApplicationException(porgaCode,errorDetails.getErrorCode(),errorDetails.getArguments());
        }
    }

    private ApplicationExceptionMapper.APIError parseErrorDetails(WebClientResponseException e) {
        String errorCode = null;
        List<String> arguments = null;
        if (e.getResponseBodyAsString() != null) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode errorNode = objectMapper.readTree(e.getResponseBodyAsString());
                errorCode = errorNode.get("errorCode").asText();
                arguments = Arrays.asList(objectMapper.convertValue(errorNode.get("arguments"), String[].class));
            } catch (IOException ex) {
            }
        }
        return new ApplicationExceptionMapper.APIError(errorCode, arguments.toArray());
    }
}






