package com.configme.service;

import com.configme.domain.*;
import com.configme.domain.enumeration.OrderStatus;
import com.configme.repository.*;
import com.configme.service.dto.CartDTO;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderHandlerImpl implements OrderHandler {

    @Autowired
    private OrderRepository orderRepository;

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

    public Order createOrderFromCart(CartDTO[] cart, User user) {
        Order order = new Order();

        order.setStatus(OrderStatus.CART);
        order.setCreatedAt(LocalDate.now());
        order.setUpdatedAt(LocalDate.now());

        Set<OrderLine> lines = new HashSet<>();

        for (CartDTO cartLine : cart) {
            OrderLine orderLine = createOrderLineFromCartLine(cartLine);
            orderLine.setOrder(order);
            lines.add(orderLine);
        }

        order.setLines(lines);

        order.setDeliveryAddress((user.getAddress()).clone());

        order.setBuyer(user);

        orderRepository.saveAndFlush(order);

        return order;
    }

    private OrderLine createOrderLineFromCartLine(CartDTO cartLine) {
        OrderLine orderLine = new OrderLine();
        ClientConfig config = new ClientConfig();
        if (cartLine.getCpuId() != null && cpuRepository.findById(cartLine.getCpuId()).isPresent()) {
            Cpu cpu = cpuRepository.findById(cartLine.getCpuId()).get();
            config.setCpu(cpu);
            config.setCpuPrice(cpu.getPrice());
        }
        if (cartLine.getGpuId() != null && gpuRepository.findById(cartLine.getGpuId()).isPresent()) {
            Gpu gpu = gpuRepository.findById(cartLine.getGpuId()).get();
            config.setGpu(gpu);
            config.setGpuPrice(gpu.getPrice());
        }
        if (cartLine.getComputerCaseId() != null && computerCaseRepository.findById(cartLine.getComputerCaseId()).isPresent()) {
            ComputerCase computerCase = computerCaseRepository.findById(cartLine.getComputerCaseId()).get();
            config.setComputerCase(computerCase);
            config.setComputerCasePrice(computerCase.getPrice());
        }
        if (cartLine.getHd1Id() != null && hardDriveRepository.findById(cartLine.getHd1Id()).isPresent()) {
            HardDrive hd1 = hardDriveRepository.findById(cartLine.getHd1Id()).get();
            config.setDeadMemory1(hd1);
            config.setHd1Price(hd1.getPrice());
        }
        if (cartLine.getHd2Id() != null && hardDriveRepository.findById(cartLine.getHd2Id()).isPresent()) {
            HardDrive hd2 = hardDriveRepository.findById(cartLine.getHd2Id()).get();
            config.setDeadMemory2(hd2);
            config.setHd1Price(hd2.getPrice());
        }
        if (cartLine.getVentiradId() != null && ventiradRepository.findById(cartLine.getVentiradId()).isPresent()) {
            Ventirad ventirad = ventiradRepository.findById(cartLine.getVentiradId()).get();
            config.setVentirad(ventirad);
            config.setVentiradPrice(ventirad.getPrice());
        }
        if (cartLine.getPsuId() != null && psuRepository.findById(cartLine.getPsuId()).isPresent()) {
            Psu psu = psuRepository.findById(cartLine.getPsuId()).get();
            config.setPsu(psu);
            config.setPsuPrice(psu.getPrice());
        }

        orderLine.setConfig(config);

        return orderLine;
    }
}
