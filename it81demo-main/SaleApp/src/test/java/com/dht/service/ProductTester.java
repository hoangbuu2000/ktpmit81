/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dht.service;
import com.dht.pojo.Product;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLClientInfoException;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.time.Duration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Admin
 */
public class ProductTester {
    private static Connection conn;
    
    @BeforeAll
    public static void setUpClass() {
        try {
            conn = JdbcUtils.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(ProductTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @AfterAll
    public static void tearDownClass() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(ProductTester.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @Test
    public void testWithKeyWord() throws SQLException {
        ProductService s = new ProductService(conn);
        List<Product> products = s.getProducts("iphone");
        
        products.forEach(p -> {
            Assertions.assertTrue(p.getName().contains("iPhone"));
        });
    }
    
    @Test
    public void testUnknownWithKeyWord() throws SQLException {
        ProductService s = new ProductService(conn);
        List<Product> products = s.getProducts("iphone");
        
        Assertions.assertEquals(0, products.size());
    }
    
    @Test
    public void testException() {
        Assertions.assertThrows(SQLDataException.class, () -> {
            new ProductService(conn).getProducts(null);
        });
    }
    
    @Test
    public void testTimeOut() {
        Assertions.assertTimeout(Duration.ZERO, () -> {
            return null; //To change body of generated lambdas, choose Tools | Templates.
        });
    }
    
    @Test
    public void testAddProductWithNameNull() throws SQLException {
        Product p = new Product();
        p.setName(null);
        p.setDescription("Dang Hoang Buu");
        p.setImage("localhost.png");
        p.setPrice(new BigDecimal(100000));
        p.setCategory_id(1);
        
        Assertions.assertFalse(new ProductService(conn).addProduct(p));
    }
    
    @Test
    public void testAddProductWithInvalidCate() throws SQLException {
        Product p = new Product();
        p.setName("Buu");
        p.setDescription("Dang Hoang Buu");
        p.setImage("localhost.png");
        p.setPrice(new BigDecimal(100000));
        p.setCategory_id(99);
        
        Assertions.assertFalse(new ProductService(conn).addProduct(p));
    }
    
    @Test
    public void testAddProduct() throws SQLException {
        Product p = new Product();
        p.setName("Buu");
        p.setDescription("Dang Hoang Buu");
        p.setImage("localhost.png");
        p.setPrice(new BigDecimal(100000));
        p.setCategory_id(1);
        
        Assertions.assertTrue(new ProductService(conn).addProduct(p));
        new ProductService(conn).getProducts("Buu").forEach(p1 -> {
            Assertions.assertEquals(p1.getName(), p.getName());
            Assertions.assertEquals(p1.getDescription(), p.getDescription());
            Assertions.assertEquals(p1.getCategory_id(), p.)
        });
    }
}
