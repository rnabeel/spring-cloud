package com.mfsys.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mfsys.comm.constant.EurekaRegisteredMicroServicesURI;
import com.mfsys.comm.constant.SecurityURI;
import com.mfsys.comm.exception.ApplicationException;
import com.mfsys.comm.exception.ApplicationExceptionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpStatus;
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
public class WebclientUcoService {
    @LoadBalanced
    private final WebClient webClient;

    @Autowired
    public WebclientUcoService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(EurekaRegisteredMicroServicesURI.UCO_SERVICE_LB).build();
    }
        public Object getCustomeProfiledataOnEmail(String url,String porOrgacode) {
            return handleResponse(webClient.get().uri(url).accept(MediaType.APPLICATION_JSON)
                            .header("SUS_USERCODE", porOrgacode)
                            .header("POR_ORGACODE", porOrgacode).retrieve().toEntity(Object.class),
                    null);
        }

//        public Object fecthCustomerLoginInfo(Map payload, String url, String porOrgaCode) {
//            return handleResponse(webClient.post().uri(url).bodyValue(payload).accept(MediaType.APPLICATION_JSON)
//                            .header("SUS_USERCODE", porOrgaCode)
//                            .header("POR_ORGACODE", porOrgaCode).retrieve()
//                            .toEntity(Object.class),
//                    porOrgaCode);
//        }

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
