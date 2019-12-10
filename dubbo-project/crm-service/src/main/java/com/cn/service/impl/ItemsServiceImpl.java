package com.cn.service.impl;

import com.cn.api.ItemService;
import com.cn.dao.ItemsMapper;
import com.cn.entity.Items;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("itemService")
public class ItemsServiceImpl implements ItemService {

    @Autowired
    ItemsMapper itemsMapper;

    @Override
    public Items getItem(Integer itemId) {
        return itemsMapper.selectItemsById(itemId);
    }
}
