package com.client.service;

import com.client.service.dto.ApplicationDTO;
import com.client.service.dto.ApplicationDTOList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import static com.client.service.constant.Path.APPLICATION_LIST;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
    private final RestTemplate restTemplate;

    @Override
    public List<ApplicationDTO> getApplications() {
        ApplicationDTOList list = restTemplate.getForObject(APPLICATION_LIST, ApplicationDTOList.class);
        return list != null ? list.getApplications() : Collections.emptyList();
    }
}