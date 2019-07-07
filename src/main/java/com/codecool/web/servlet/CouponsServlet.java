package com.codecool.web.servlet;

import com.codecool.web.dao.CouponDao;
import com.codecool.web.dao.ShopDao;
import com.codecool.web.dao.database.DatabaseCouponDao;
import com.codecool.web.dao.database.DatabaseShopDao;
import com.codecool.web.model.Coupon;
import com.codecool.web.model.User;
import com.codecool.web.service.CouponService;
import com.codecool.web.service.exception.ServiceException;
import com.codecool.web.service.simple.SimpleCouponService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/protected/coupons")
public final class CouponsServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            CouponDao couponDao = new DatabaseCouponDao(connection);
            ShopDao shopDao = new DatabaseShopDao(connection);
            CouponService couponService = new SimpleCouponService(couponDao, shopDao);
            HttpSession session = req.getSession();
            User user = (User) session.getAttribute("user");
            List<Coupon> coupons = couponService.getCouponsByUsers(user.getId());

            sendMessage(resp, HttpServletResponse.SC_OK, coupons);
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        } catch (ServiceException ex){
            sendMessage(resp, HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            CouponDao couponDao = new DatabaseCouponDao(connection);
            ShopDao shopDao = new DatabaseShopDao(connection);
            CouponService couponService = new SimpleCouponService(couponDao, shopDao);

            String name = req.getParameter("name");
            String percentage = req.getParameter("percentage");
            HttpSession session = req.getSession();
            User user = (User) session.getAttribute("user");
            Coupon coupon = couponService.addCoupon(name,percentage,user.getId());

            sendMessage(resp, HttpServletResponse.SC_OK, coupon);
        } catch (ServiceException ex) {
            sendMessage(resp, HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }
}
