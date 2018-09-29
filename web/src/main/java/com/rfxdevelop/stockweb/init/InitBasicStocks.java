package com.rfxdevelop.stockweb.init;

import com.rfxdevelop.stockweb.dao.dynamic.IMapper;
import com.rfxdevelop.stockweb.dao.simple.SysMapper;
import com.rfxdevelop.stockweb.model.BaseData;
import com.rfxdevelop.stockweb.model.DbColumn;
import com.rfxdevelop.stockweb.model.DbTable;
import com.rfxdevelop.stockweb.model.TableShow;
import lombok.extern.java.Log;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Component
@Log
public class InitBasicStocks implements CommandLineRunner{

    @Autowired(required = false)
    private IMapper iMapper;
    @Autowired
    private BaseData baseData;

    @Override
    public void run(String... args) throws Exception {
        List<Map<String, Object>> stock_basics = iMapper.select(null, "stock_basics", null, null);

        baseData.setBasicStocks(stock_basics);
    }
}
