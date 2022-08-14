package com.example.eventbus.demo.service;

import com.example.eventbus.demo.service.model.IpSqlMsgDto;

import java.util.List;

public interface IpSqlMsgService {

    public String createIndexSqlMsg();

    public String saveIpSqlMsgDoc(IpSqlMsgDto ipSqlMsgDto);

    public String bulkSaveIpSqlMsgDoc(List<IpSqlMsgDto> ipSqlMsgDto);
}
