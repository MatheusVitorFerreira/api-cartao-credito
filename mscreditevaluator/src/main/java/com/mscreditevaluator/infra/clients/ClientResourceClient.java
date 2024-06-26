package com.mscreditevaluator.infra.clients;

import com.mscreditevaluator.domain.info.DataClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "msusers", path = "/api/v1/client")
public interface ClientResourceClient {

    @GetMapping("/get-id")
    ResponseEntity<DataClient> getClientById(@RequestParam("idClient") String id);
}
