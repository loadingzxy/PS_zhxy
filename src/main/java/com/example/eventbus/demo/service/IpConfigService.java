package com.example.eventbus.demo.service;

import com.example.eventbus.demo.dao.entity.IpConfig;
import com.example.eventbus.demo.service.model.IpConfigDto;

import java.util.List;

public interface IpConfigService {

    public String saveIpConfig(IpConfigDto ipConfig);

    public List<IpConfigDto> queryIpConfig(IpConfigDto ipConfigDto);
}
