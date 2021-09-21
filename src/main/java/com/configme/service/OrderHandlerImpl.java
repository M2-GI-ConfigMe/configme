package com.configme.service;

import com.configme.domain.*;
import com.configme.domain.enumeration.OrderStatus;
import com.configme.repository.*;
import com.configme.service.dto.CartDTO;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
    private UserRepository userRepository;

    public Order createOrderFromCart(CartDTO[] cart, User user) {
        if (userRepository.haveOrderProcessing(user)) throw new ResponseStatusException(
            HttpStatus.BAD_REQUEST,
            "Un panier est déjà en cours de traintement"
        );

        if (userRepository.haveCart(user)) orderRepository.deleteById(userRepository.getCartId(user));

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
        if (cartLine.getCpuId() != null) {
            Long cpuId = cartLine.getCpuId();
            Optional<Cpu> optionalCpu = cpuRepository.findById(cpuId);
            if (optionalCpu.isPresent()) {
                Cpu cpu = optionalCpu.get();
                config.setCpu(cpu);
                config.setCpuPrice(cpu.getPrice());
            }
        }
        if (cartLine.getGpuId() != null) {
            Long gpuId = cartLine.getGpuId();
            Optional<Gpu> optionalGpu = gpuRepository.findById(gpuId);
            if (optionalGpu.isPresent()) {
                Gpu gpu = optionalGpu.get();
                config.setGpu(gpu);
                config.setGpuPrice(gpu.getPrice());
            }
        }
        if (cartLine.getComputerCaseId() != null) {
            Long computerCaseId = cartLine.getComputerCaseId();
            Optional<ComputerCase> optionalComputerCase = computerCaseRepository.findById(computerCaseId);
            if (optionalComputerCase.isPresent()) {
                ComputerCase computerCase = optionalComputerCase.get();
                config.setComputerCase(computerCase);
                config.setComputerCasePrice(computerCase.getPrice());
            }
        }
        if (cartLine.getHd1Id() != null) {
            Long hd1Id = cartLine.getHd1Id();
            Optional<HardDrive> optionalHd1 = hardDriveRepository.findById(hd1Id);
            if (optionalHd1.isPresent()) {
                HardDrive hd1 = optionalHd1.get();
                config.setHd1(hd1);
                config.setHd1Price(hd1.getPrice());
            }
        }
        if (cartLine.getHd2Id() != null) {
            Long hd2Id = cartLine.getHd2Id();
            Optional<HardDrive> optionalHd2 = hardDriveRepository.findById(hd2Id);
            if (optionalHd2.isPresent()) {
                HardDrive hd2 = optionalHd2.get();
                config.setHd2(hd2);
                config.setHd2Price(hd2.getPrice());
            }
        }
        if (cartLine.getRam1Id() != null) {
            Long ram1Id = cartLine.getRam1Id();
            Optional<Ram> optionalRam1 = ramRepository.findById(ram1Id);
            if (optionalRam1.isPresent()) {
                Ram ram1 = optionalRam1.get();
                config.setRam1(ram1);
                config.setRam1Price(ram1.getPrice());
            }
        }
        if (cartLine.getRam2Id() != null) {
            Long ram2Id = cartLine.getRam2Id();
            Optional<Ram> optionalRam2 = ramRepository.findById(ram2Id);
            if (optionalRam2.isPresent()) {
                Ram ram2 = optionalRam2.get();
                config.setRam2(ram2);
                config.setRam2Price(ram2.getPrice());
            }
        }
        if (cartLine.getVentiradId() != null) {
            Long ventiradId = cartLine.getVentiradId();
            Optional<Ventirad> optionalVentirad = ventiradRepository.findById(ventiradId);
            if (optionalVentirad.isPresent()) {
                Ventirad ventirad = optionalVentirad.get();
                config.setVentirad(ventirad);
                config.setVentiradPrice(ventirad.getPrice());
            }
        }
        if (cartLine.getPsuId() != null) {
            Long psuId = cartLine.getPsuId();
            Optional<Psu> optionalPsu = psuRepository.findById(psuId);
            if (optionalPsu.isPresent()) {
                Psu psu = optionalPsu.get();
                config.setPsu(psu);
                config.setPsuPrice(psu.getPrice());
            }
        }
        if (cartLine.getMbeId() != null) {
            Long mbeId = cartLine.getMbeId();
            Optional<Mbe> optionalMbe = mbeRepository.findById(mbeId);
            if (optionalMbe.isPresent()) {
                Mbe mbe = optionalMbe.get();
                config.setMbe(mbe);
                config.setMbePrice(mbe.getPrice());
            }
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
            if (config.getHd1() != null) config.getHd1().setStock(config.getHd1().getStock() - 1);
            if (config.getHd2() != null) config.getHd2().setStock(config.getHd2().getStock() - 1);
            if (config.getRam1() != null) config.getRam1().setStock(config.getRam1().getStock() - 1);
            if (config.getRam2() != null) config.getRam2().setStock(config.getRam2().getStock() - 1);
            if (config.getVentirad() != null) config.getVentirad().setStock(config.getVentirad().getStock() - 1);
            if (config.getPsu() != null) config.getPsu().setStock(config.getPsu().getStock() - 1);
        }

        this.orderRepository.saveAndFlush(order);
    }
}
