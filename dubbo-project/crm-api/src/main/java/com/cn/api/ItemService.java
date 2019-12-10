package com.cn.api;


import com.cn.entity.Items;

public interface ItemService {
    /*
     *
     * @param itemId
     * @return 根据商品id获取商品
*/

    public Items getItem(Integer itemId);


}
