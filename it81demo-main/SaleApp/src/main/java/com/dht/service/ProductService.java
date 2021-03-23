/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dht.service;

import com.dht.pojo.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class ProductService {
    private Connection conn;
    
    public ProductService(Connection conn) {
        this.conn = conn;
    }
    
    public List<Product> getProducts(String kw) throws SQLException {
        if (kw == null)
            throw new SQLException("loi ne");
        
        String sql = "select * from product where name like concat('%', ?, '%')";
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setString(1, kw);
        ResultSet rs = stm.executeQuery();
        List<Product> ds = new ArrayList<Product>();
        while(rs.next()) {
            Product p = new Product();
            p.setId(rs.getInt("id"));
            p.setName(rs.getString("name"));
            p.setDescription(rs.getString("description"));
            p.setImage(rs.getString("image"));
            p.setPrice(rs.getBigDecimal("price"));
            ds.add(p);
        }
        
        stm.close();
    
        return ds;
    }
    
    public boolean addProduct(Product p){
        try {
            String q = "insert into product(name, description, image, price, category_id)"
                    + "values(?, ?, ?, ?, ?)";
            PreparedStatement stm = conn.prepareStatement(q);
            stm.setString(1, p.getName());
            stm.setString(2, p.getDescription());
            stm.setString(3, p.getImage());
            stm.setBigDecimal(4, p.getPrice());
            stm.setInt(5, p.getCategory_id());
            int i = stm.executeUpdate();
            
            return i > 0;
        } catch (SQLException ex) {
            Logger.getLogger(ProductService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
