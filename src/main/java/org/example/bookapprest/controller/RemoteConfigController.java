package org.example.bookapprest.controller;

import lombok.RequiredArgsConstructor;
import org.example.bookapprest.model.remoteconfig.request.RemoteConfigReq;
import org.example.bookapprest.model.remoteconfig.response.RemoteConfigRes;
import org.example.bookapprest.service.RemoteConfigService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/remote-config")
@RequiredArgsConstructor
public class RemoteConfigController {

    private final RemoteConfigService remoteConfigService;

    @PostMapping("/update")
    public ResponseEntity<String> updateRemoteConfig(@RequestBody RemoteConfigReq remoteConfig) {
        try {
            remoteConfigService.updateRemoteConfig(remoteConfig);
            return ResponseEntity.ok("Remote config updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }

    }


    @GetMapping
    public ResponseEntity<String> getRemoteConfig() {
        try {
            String config = remoteConfigService.getRemoteConfig();
            return ResponseEntity.ok(config);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}
