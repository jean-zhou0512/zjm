
package com.imooc.order.service.impl;

import com.imooc.mapper.OrdersMapper;
import com.imooc.order.service.OrdersService;
import com.imooc.pojo.Orders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdersServiceImpl implements OrdersService {
	
	final static Logger log = LoggerFactory.getLogger(OrdersServiceImpl.class);
	
	@Autowired
	private OrdersMapper ordersMapper;
	
	@Override
	public Orders getOrder(int orderId) {
		return ordersMapper.selectByPrimaryKey(orderId);
	}

    /*@Override
	public boolean createOrder(int itemId) {
		
		// 创建订单
		int oid = UUID.randomUUID().toString().replaceAll("-", "");
		Orders o = new Orders();
		o.setId(oid);
		o.setOrderNum(oid);
		o.setItemId(itemId);
		ordersMapper.insert(o);
		
		log.info("订单创建成功");
		
		return true;
	}
*/

}


