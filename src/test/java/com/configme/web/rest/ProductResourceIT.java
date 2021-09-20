package com.configme.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.configme.domain.Product;
import com.configme.repository.ProductRepository;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

public interface ProductResourceIT {
    String DEFAULT_NAME = "AAAAAAAAAA";
    String UPDATED_NAME = "BBBBBBBBBB";

    Float DEFAULT_PRICE = 0.5F;
    Float UPDATED_PRICE = 1F;

    Float DEFAULT_DISCOUNT = 0F;
    Float UPDATED_DISCOUNT = 1F;

    Integer DEFAULT_STOCK = 0;
    Integer UPDATED_STOCK = 1;

    String DEFAULT_IMG = "AAAAAAAAAA";
    String UPDATED_IMG = "BBBBBBBBBB";

    String DEFAULT_DESC = "AAAAAAAAAA";
    String UPDATED_DESC = "BBBBBBBBBB";

    String DEFAULT_BRAND = "AAAAAAAAAA";
    String UPDATED_BRAND = "BBBBBBBBBB";

    Boolean DEFAULT_IS_ACTIVE = false;
    Boolean UPDATED_IS_ACTIVE = true;

    static Product createProductField(Product product) {
        product
            .name(DEFAULT_NAME)
            .price(DEFAULT_PRICE)
            .discount(DEFAULT_DISCOUNT)
            .stock(DEFAULT_STOCK)
            .img(DEFAULT_IMG)
            .desc(DEFAULT_DESC)
            .brand(DEFAULT_BRAND)
            .isActive(DEFAULT_IS_ACTIVE);

        return product;
    }

    static Product updateProductField(Product product) {
        product
            .name(UPDATED_NAME)
            .price(UPDATED_PRICE)
            .discount(UPDATED_DISCOUNT)
            .stock(UPDATED_STOCK)
            .img(UPDATED_IMG)
            .desc(UPDATED_DESC)
            .brand(UPDATED_BRAND)
            .isActive(UPDATED_IS_ACTIVE);

        return product;
    }

    default void checkNameIsRequired(Product product, String url, ProductRepository productRepository, MockMvc mockMvc) throws Exception {
        int databaseSizeBeforeTest = productRepository.findAll().size();

        // set the field null
        product.setName(null);

        // Create the Product, which fails.

        mockMvc
            .perform(post(url).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(product)))
            .andExpect(status().isBadRequest());

        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeTest);
    }

    default void checkPriceIsRequired(Product product, String url, ProductRepository productRepository, MockMvc mockMvc) throws Exception {
        int databaseSizeBeforeTest = productRepository.findAll().size();

        // set the field null
        product.setPrice(null);

        // Create the Product, which fails.

        mockMvc
            .perform(post(url).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(product)))
            .andExpect(status().isBadRequest());

        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeTest);
    }

    default void checkStockIsRequired(Product product, String url, ProductRepository productRepository, MockMvc mockMvc) throws Exception {
        int databaseSizeBeforeTest = productRepository.findAll().size();

        // set the field null
        product.setStock(null);

        // Create the Product, which fails.

        mockMvc
            .perform(post(url).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(product)))
            .andExpect(status().isBadRequest());

        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeTest);
    }

    default void checkImgIsRequired(Product product, String url, ProductRepository productRepository, MockMvc mockMvc) throws Exception {
        int databaseSizeBeforeTest = productRepository.findAll().size();

        // set the field null
        product.setImg(null);

        // Create the Product, which fails.

        mockMvc
            .perform(post(url).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(product)))
            .andExpect(status().isBadRequest());

        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeTest);
    }

    default void checkBrandIsRequired(Product product, String url, ProductRepository productRepository, MockMvc mockMvc) throws Exception {
        int databaseSizeBeforeTest = productRepository.findAll().size();

        // set the field null
        product.setBrand(null);

        // Create the Product, which fails.

        mockMvc
            .perform(post(url).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(product)))
            .andExpect(status().isBadRequest());

        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeTest);
    }

    default void checkIsActiveIsRequired(Product product, String url, ProductRepository productRepository, MockMvc mockMvc)
        throws Exception {
        int databaseSizeBeforeTest = productRepository.findAll().size();

        // set the field null
        product.setIsActive(null);

        // Create the Product, which fails.

        mockMvc
            .perform(post(url).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(product)))
            .andExpect(status().isBadRequest());

        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeTest);
    }

    default void getAllProductAssertProductField(ResultActions mockMvc) throws Exception {
        mockMvc
            .andExpect(jsonPath("$.content.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.content.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.content.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.content.[*].stock").value(hasItem(DEFAULT_STOCK)))
            .andExpect(jsonPath("$.content.[*].img").value(hasItem(DEFAULT_IMG)))
            .andExpect(jsonPath("$.content.[*].desc").value(hasItem(DEFAULT_DESC)))
            .andExpect(jsonPath("$.content.[*].brand").value(hasItem(DEFAULT_BRAND)))
            .andExpect(jsonPath("$.content.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }

    default void getProductAssertProductField(ResultActions mockMvc) throws Exception {
        mockMvc
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT.doubleValue()))
            .andExpect(jsonPath("$.stock").value(DEFAULT_STOCK))
            .andExpect(jsonPath("$.img").value(DEFAULT_IMG))
            .andExpect(jsonPath("$.desc").value(DEFAULT_DESC))
            .andExpect(jsonPath("$.brand").value(DEFAULT_BRAND))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()));
    }

    default void assertProductCreation(Product testProduct) {
        assertThat(testProduct.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProduct.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testProduct.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
        assertThat(testProduct.getStock()).isEqualTo(DEFAULT_STOCK);
        assertThat(testProduct.getImg()).isEqualTo(DEFAULT_IMG);
        assertThat(testProduct.getDesc()).isEqualTo(DEFAULT_DESC);
        assertThat(testProduct.getBrand()).isEqualTo(DEFAULT_BRAND);
        assertThat(testProduct.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    default void assertProductUpdate(Product testProduct) {
        assertThat(testProduct.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProduct.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testProduct.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testProduct.getStock()).isEqualTo(UPDATED_STOCK);
        assertThat(testProduct.getImg()).isEqualTo(UPDATED_IMG);
        assertThat(testProduct.getDesc()).isEqualTo(UPDATED_DESC);
        assertThat(testProduct.getBrand()).isEqualTo(UPDATED_BRAND);
        assertThat(testProduct.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    default Product partialUpdateField(Product product) {
        product.name(UPDATED_NAME).discount(UPDATED_DISCOUNT).stock(UPDATED_STOCK).desc(UPDATED_DESC).isActive(UPDATED_IS_ACTIVE);

        return product;
    }

    default void assertPartialUpdateField(Product testProduct) {
        assertThat(testProduct.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProduct.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testProduct.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testProduct.getStock()).isEqualTo(UPDATED_STOCK);
        assertThat(testProduct.getImg()).isEqualTo(DEFAULT_IMG);
        assertThat(testProduct.getDesc()).isEqualTo(UPDATED_DESC);
        assertThat(testProduct.getBrand()).isEqualTo(DEFAULT_BRAND);
        assertThat(testProduct.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    default void testProductField(ProductRepository productRepository, MockMvc mockMvc, Product product, String url) throws Exception {
        checkNameIsRequired(product, url, productRepository, mockMvc);

        checkPriceIsRequired(product, url, productRepository, mockMvc);

        checkStockIsRequired(product, url, productRepository, mockMvc);

        checkImgIsRequired(product, url, productRepository, mockMvc);

        checkBrandIsRequired(product, url, productRepository, mockMvc);

        checkIsActiveIsRequired(product, url, productRepository, mockMvc);
    }
}
