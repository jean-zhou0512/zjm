package com.imooc.web.controller;

import com.imooc.order.service.OrdersService;
import com.imooc.pojo.Orders;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class OrderController {

    @Autowired
    private OrdersService ordersService;

    @RequestMapping(value = "/getOrder")
    public String getOrder(HttpServletRequest request, Model model){
            String ordersId=request.getParameter("ordersIds");
            if(ordersId==null|| StringUtils.isBlank(ordersId)){
                return "/Error";
            }else{
                Orders orders=ordersService.getOrder(Integer.parseInt(ordersId));
                model.addAttribute("orders",orders);
                return "/OrderList";
            }
    }

}
