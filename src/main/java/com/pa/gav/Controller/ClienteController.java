package com.pa.gav.Controller;

import com.pa.gav.Entity.Cliente;
import com.pa.gav.Entity.Viaje;
import com.pa.gav.Repository.ClienteRepository;
import com.pa.gav.Repository.ViajeRepository;
import com.pa.gav.Services.ViajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.ArrayList;
import java.util.List;

@Controller
public class ClienteController {

    @Autowired
    public ViajeRepository viajeRepository;

    @Autowired
    private ViajeService viajeService;

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/VCliente")
    public String showClientePage(@RequestParam(value = "panel", required = false) String panel, HttpSession session, Model model) {
        Cliente cliente = (Cliente) session.getAttribute("cliente");

        if (cliente == null) {
            System.out.println("Cliente no encontrado en la sesión. Redirigiendo a login.");
            return "redirect:/Login_cliente";
        }

        // Manejo de paneles
        List<String> activePanels = new ArrayList<>();

        if ("solicitarViaje".equals(panel)) {
            activePanels.add("solicitarViaje");
        } else if ("misViajes".equals(panel)) {
            activePanels.add("misViajes");
        } else if ("ActDatosPersonales".equals(panel)) {
            activePanels.add("ActDatosPersonales");
        }

        model.addAttribute("activePanels", activePanels);

        // Recargar los viajes del cliente
        List<Viaje> viajes = viajeRepository.findByCliente(cliente);
        model.addAttribute("viajes", viajes);

        model.addAttribute("cliente", cliente);
        return "VCliente";
    }

    // LOGICA DE SOLICITUD DEL VIAJE
    @PostMapping("/solicitarViaje")
    public String solicitarViaje(@RequestParam("clienteId") Long clienteId,
                                 @RequestParam("destino") String destino,
                                 @RequestParam("cantidadPasajeros") int cantidadPasajeros,
                                 @RequestParam("fechaSolicitud") String fechaSolicitud,
                                 RedirectAttributes redirectAttributes, HttpSession session) {

        Cliente cliente = (Cliente) session.getAttribute("cliente");
        if (cliente == null || !cliente.getId().equals(clienteId)) {
            redirectAttributes.addFlashAttribute("errorSolicitud", "No se pudo encontrar el cliente.");
            return "redirect:/VCliente?panel=solicitarViaje";
        }

        try {
            String mensaje = viajeService.solicitarViaje(clienteId, destino, cantidadPasajeros, fechaSolicitud);
            redirectAttributes.addFlashAttribute("successSolicitud", mensaje);
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorSolicitud", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorSolicitud", "Hubo un problema al solicitar el viaje.");
        }

        return "redirect:/VCliente?panel=solicitarViaje";
    }

    @PostMapping("/cancelarViaje")
    public String cancelarViaje(@RequestParam("viajeId") Long viajeId, RedirectAttributes redirectAttributes) {
        try {
            viajeService.cancelarViaje(viajeId);

            redirectAttributes.addFlashAttribute("successCancelacion", "El viaje ha sido cancelado.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorCancelacion", "Hubo un problema al cancelar el viaje.");
        }

        return "redirect:/VCliente?panel=misViajes";
    }






}
