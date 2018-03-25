package com.mw.spike.service;

import com.mw.spike.dao.TestDao;
import com.mw.spike.domain.SpikeOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author WangCH
 * @create 2018-03-08 18:30
 */

@Service
public class TestService {

    @Autowired
    TestDao testDao;

    public List<SpikeOrder> getSpikeOrderTest(int start){
        return testDao.getSpikeOrderTest(start);
    }
}
