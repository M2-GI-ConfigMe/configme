package com.configme.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.configme.IntegrationTest;
import com.configme.domain.*;
import com.configme.repository.*;
import com.configme.service.dto.CartDTO;
import com.configme.web.rest.user.*;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@AutoConfigureMockMvc
@WithMockUser
@IntegrationTest
public class PaymentIT {

    private static final int DEFAULT_NB_LINE_CART = 5;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CpuRepository cpuRepository;

    @Autowired
    private GpuRepository gpuRepository;

    @Autowired
    private RamRepository ramRepository;

    @Autowired
    private PsuRepository psuRepository;

    @Autowired
    private ComputerCaseRepository computerCaseRepository;

    @Autowired
    private HardDriveRepository hardDriveRepository;

    @Autowired
    private VentiradRepository ventiradRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAuthenticator userAuthenticator;

    @Autowired
    private EntityManager em;

    private static String ORDER_API_URL = "/api/orders";

    private CartDTO[] cart;

    private CartDTO[] createCart() {
        CartDTO[] cart = new CartDTO[DEFAULT_NB_LINE_CART];
        for (int i = 0; i < DEFAULT_NB_LINE_CART; i++) {
            CartDTO cartLine = new CartDTO();

            Cpu cpu = CpuResourceITUser.createEntity();
            ComputerCase computerCate = ComputerCaseResourceITUser.createEntity();
            Gpu gpu = GpuResourceITUser.createEntity();
            HardDrive hd1 = HardDriveResourceITUser.createEntity();
            HardDrive hd2 = HardDriveResourceITUser.createEntity();
            Ram ram = RamResourceITUser.createEntity();
            Ventirad ventirad = VentiradResourceITUser.createEntity();
            Psu psu = PsuResourceITUser.createEntity();

            cpu.setName("cpu " + i);
            computerCate.setName("computerCate " + i);
            gpu.setName("gpu " + i);
            hd1.setName("hd1 " + (i * 2));
            hd2.setName("hd2 " + (i * 2 + 1));
            ram.setName("ram " + i);
            ventirad.setName("ventirad " + i);
            psu.setName("psu " + i);

            cpuRepository.saveAndFlush(cpu);
            gpuRepository.saveAndFlush(gpu);
            computerCaseRepository.saveAndFlush(computerCate);
            hardDriveRepository.saveAndFlush(hd1);
            hardDriveRepository.saveAndFlush(hd2);
            ventiradRepository.saveAndFlush(ventirad);
            ramRepository.saveAndFlush(ram);
            psuRepository.saveAndFlush(psu);

            cartLine.setComputerCaseId(computerCate.getId());
            cartLine.setCpuId(cpu.getId());
            cartLine.setGpuId(gpu.getId());
            cartLine.setDeadMemory1Id(hd1.getId());
            cartLine.setDeadMemory2Id(hd2.getId());
            cartLine.setRam1Id(ram.getId());
            cartLine.setVentiradId(ventirad.getId());
            cartLine.setPsuId(psu.getId());

            cart[i] = cartLine;
        }

        return cart;
    }

    @BeforeEach
    public void initTest() {
        cart = createCart();
    }

    @Test
    @Transactional
    public void testCreateConfig() throws Exception {
        mockMvc
            .perform(
                post(ORDER_API_URL)
                    .header("Authorization", userAuthenticator.getBearer("user@mail", "password"))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cart))
            )
            .andExpect(status().isCreated());

        User user = userRepository.findOneByEmail("user@mail").get();

        em.refresh(user);

        assertThat(user.getOrders()).hasSize(1);
        assertThat(user.getOrders().iterator().next().getLines()).hasSize(DEFAULT_NB_LINE_CART);
    }
}
