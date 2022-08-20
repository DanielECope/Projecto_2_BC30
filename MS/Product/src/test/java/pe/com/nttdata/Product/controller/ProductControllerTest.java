package pe.com.nttdata.Product.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pe.com.nttdata.Product.models.Product;
import pe.com.nttdata.Product.repository.IProductTypeRepository;
import pe.com.nttdata.Product.service.IProductService;

import static org.junit.jupiter.api.Assertions.*;

class ProductControllerTest {

    @Mock
    private IProductTypeRepository repo;

    @InjectMocks
    private IProductService service;

    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findAll() {
    }

    @Test
    void fintById() {
    }

    @Test
    void insert() {
    }
}