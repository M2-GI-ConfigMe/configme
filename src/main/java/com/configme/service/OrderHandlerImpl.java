package com.configme.service;

import com.configme.domain.*;
import com.configme.domain.enumeration.OrderStatus;
import com.configme.repository.*;
import com.configme.service.dto.CartDTO;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private MbeRepository mbeRepository;

    @Autowired
    private EntityManager em;

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
        if (cartLine.getDeadMemory1Id() != null && hardDriveRepository.findById(cartLine.getDeadMemory1Id()).isPresent()) {
            HardDrive hd1 = hardDriveRepository.findById(cartLine.getDeadMemory1Id()).get();
            config.setDeadMemory1(hd1);
            config.setHd1Price(hd1.getPrice());
        }
        if (cartLine.getDeadMemory2Id() != null && hardDriveRepository.findById(cartLine.getDeadMemory2Id()).isPresent()) {
            HardDrive hd2 = hardDriveRepository.findById(cartLine.getDeadMemory2Id()).get();
            config.setDeadMemory2(hd2);
            config.setHd2Price(hd2.getPrice());
        }
        if (cartLine.getRam1Id() != null && ramRepository.findById(cartLine.getRam1Id()).isPresent()) {
            Ram ram1 = ramRepository.findById(cartLine.getRam1Id()).get();
            config.setRam1(ram1);
            config.setRam1Price(ram1.getPrice());
        }
        if (cartLine.getRam2Id() != null && ramRepository.findById(cartLine.getRam2Id()).isPresent()) {
            Ram ram2 = ramRepository.findById(cartLine.getRam2Id()).get();
            config.setRam2(ram2);
            config.setRam2Price(ram2.getPrice());
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
        if (cartLine.getMbeId() != null && mbeRepository.findById(cartLine.getMbeId()).isPresent()) {
            Mbe mbe = mbeRepository.findById(cartLine.getMbeId()).get();
            config.setMbe(mbe);
            config.setMbePrice(mbe.getPrice());
        }

        orderLine.setConfig(config);

        return orderLine;
    }

    @Transactional
    public void validateOrder(Order order) {
        order.setStatus(OrderStatus.PAYED);
        for (OrderLine line : order.getLines()) {
            ClientConfig config = line.getConfig();
            if (config.getCpu() != null) config.getCpu().setStock(config.getCpu().getStock() - 1);
            if (config.getGpu() != null) config.getGpu().setStock(config.getGpu().getStock() - 1);
            if (config.getComputerCase() != null) config.getComputerCase().setStock(config.getComputerCase().getStock() - 1);
            if (config.getMbe() != null) config.getMbe().setStock(config.getMbe().getStock() - 1);
            if (config.getDeadMemory1() != null) config.getDeadMemory1().setStock(config.getDeadMemory1().getStock() - 1);
            if (config.getDeadMemory2() != null) config.getDeadMemory2().setStock(config.getDeadMemory2().getStock() - 1);
            if (config.getRam1() != null) config.getRam1().setStock(config.getRam1().getStock() - 1);
            if (config.getRam2() != null) config.getRam2().setStock(config.getRam2().getStock() - 1);
            if (config.getVentirad() != null) config.getVentirad().setStock(config.getVentirad().getStock() - 1);
            if (config.getPsu() != null) config.getPsu().setStock(config.getPsu().getStock() - 1);
        }

        this.orderRepository.saveAndFlush(order);
    }
}
