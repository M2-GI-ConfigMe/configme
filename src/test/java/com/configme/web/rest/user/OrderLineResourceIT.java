package com.configme.web.rest.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.configme.IntegrationTest;
import com.configme.domain.ClientConfig;
import com.configme.domain.Order;
import com.configme.domain.OrderLine;
import com.configme.repository.OrderLineRepository;
import com.configme.repository.OrderRepository;
import com.configme.repository.UserRepository;
import com.configme.web.rest.OrderLineResource;
import com.configme.web.rest.TestUtil;
import com.configme.web.rest.UserAuthenticator;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OrderLineResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrderLineResourceIT {

    private static final String ENTITY_API_URL = "/api/order-lines";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrderLineRepository orderLineRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrderLineMockMvc;

    private OrderLine orderLine;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAuthenticator userAuthenticator;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderLine createEntity(EntityManager em) {
        OrderLine orderLine = new OrderLine();
        // Add required entity
        ClientConfig clientConfig;
        if (TestUtil.findAll(em, ClientConfig.class).isEmpty()) {
            clientConfig = ClientConfigResourceIT.createEntity();
            em.persist(clientConfig);
            em.flush();
        } else {
            clientConfig = TestUtil.findAll(em, ClientConfig.class).get(0);
        }
        orderLine.setConfig(clientConfig);
        // Add required entity
        Order order;
        if (TestUtil.findAll(em, Order.class).isEmpty()) {
            order = OrderResourceIT.createEntity();
            em.persist(order.getBuyer());
            em.persist(order);
            em.flush();
        } else {
            order = TestUtil.findAll(em, Order.class).get(0);
        }
        orderLine.setOrder(order);
        return orderLine;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderLine createUpdatedEntity(EntityManager em) {
        OrderLine orderLine = new OrderLine();
        // Add required entity
        ClientConfig clientConfig;
        if (TestUtil.findAll(em, ClientConfig.class).isEmpty()) {
            clientConfig = ClientConfigResourceIT.createUpdatedEntity();
            em.persist(clientConfig);
            em.flush();
        } else {
            clientConfig = TestUtil.findAll(em, ClientConfig.class).get(0);
        }
        orderLine.setConfig(clientConfig);
        // Add required entity
        Order order;
        if (TestUtil.findAll(em, Order.class).isEmpty()) {
            order = OrderResourceIT.createUpdatedEntity();
            em.persist(order);
            em.flush();
        } else {
            order = TestUtil.findAll(em, Order.class).get(0);
        }
        orderLine.setOrder(order);
        return orderLine;
    }

    @BeforeEach
    public void initTest() {
        orderLine = createEntity(em);
    }

    @Test
    @Transactional
    void createOrderLine() throws Exception {
        int databaseSizeBeforeCreate = orderLineRepository.findAll().size();
        // Create the OrderLine
        restOrderLineMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderLine)))
            .andExpect(status().is4xxClientError());
    }

    @Test
    @Transactional
    void getAllOrderLines() throws Exception {
        // Initialize the database
        orderLineRepository.saveAndFlush(orderLine);

        // Get all the orderLineList
        restOrderLineMockMvc.perform(get(ENTITY_API_URL + "?sort=id,desc")).andExpect(status().is4xxClientError());
    }

    @Test
    @Transactional
    void getOrderLine() throws Exception {
        // Initialize the database
        orderLineRepository.saveAndFlush(orderLine);

        // Get the orderLine
        restOrderLineMockMvc.perform(get(ENTITY_API_URL_ID, orderLine.getId())).andExpect(status().is4xxClientError());
    }

    @Test
    @Transactional
    void putNewOrderLine() throws Exception {
        // Initialize the database
        orderLineRepository.saveAndFlush(orderLine);

        int databaseSizeBeforeUpdate = orderLineRepository.findAll().size();

        // Update the orderLine
        OrderLine updatedOrderLine = orderLineRepository.findById(orderLine.getId()).get();
        // Disconnect from session so that the updates on updatedOrderLine are not directly saved in db
        em.detach(updatedOrderLine);

        restOrderLineMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOrderLine.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedOrderLine))
            )
            .andExpect(status().is4xxClientError());
    }

    @Test
    @Transactional
    void partialUpdateOrderLineWithPatch() throws Exception {
        // Initialize the database
        orderLineRepository.saveAndFlush(orderLine);

        int databaseSizeBeforeUpdate = orderLineRepository.findAll().size();

        // Update the orderLine using partial update
        OrderLine partialUpdatedOrderLine = new OrderLine();
        partialUpdatedOrderLine.setId(orderLine.getId());

        restOrderLineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrderLine.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrderLine))
            )
            .andExpect(status().is4xxClientError());
    }

    @Test
    @Transactional
    void deleteOrderLine() throws Exception {
        // Initialize the database
        orderLineRepository.saveAndFlush(orderLine);

        int databaseSizeBeforeDelete = orderLineRepository.findAll().size();

        // Delete the orderLine
        restOrderLineMockMvc
            .perform(
                delete(ENTITY_API_URL_ID, orderLine.getId())
                    .header("Authorization", userAuthenticator.getBearer("monuser@mail", "password"))
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().is4xxClientError());
    }
}
