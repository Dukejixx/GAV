package com.pa.gav.Controller;

import com.pa.gav.Entity.Conductor;
import com.pa.gav.Entity.Viaje;
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
import java.util.Arrays;
import java.util.List;

@Controller
public class ConductorController {

    @Autowired
    private ViajeRepository viajeRepository;

    @Autowired
    private ViajeService viajeService;

    @GetMapping("/VConductor")
    public String showConductorPage(@RequestParam(value = "panel", required = false) String panel, HttpSession session, Model model) {
        Conductor conductor = (Conductor) session.getAttribute("conductor");

        if (conductor == null) {
            System.out.println("Conductor no encontrado en la sesión. Redirigiendo a login.");  // Log para verificar el conductor en la sesión
            return "redirect:/Login_conductor";  // Redirigir al login si no hay conductor en la sesión
        }

        // Manejo de paneles
        List<String> activePanels = new ArrayList<>();
        if ("misViajes".equals(panel)) {
            activePanels.add("misViajes");
        } else if ("historialViajes".equals(panel)) {
            activePanels.add("historialViajes");
        }

        model.addAttribute("activePanels", activePanels);

        // Definir los estados que necesitamos
        List<Viaje.EstadoViaje> estados = Arrays.asList(Viaje.EstadoViaje.ASIGNADO, Viaje.EstadoViaje.EN_CURSO);

        // Obtener los viajes del conductor con estado ASIGNADO y EN_CURSO
        List<Viaje> viajes = viajeRepository.findByConductorAndEstadoViajeIn(conductor, estados);
        model.addAttribute("viajes", viajes);

        List<Viaje> historialViajes = viajeRepository.findByConductorAndEstadoViaje(conductor, Viaje.EstadoViaje.COMPLETADO);
        model.addAttribute("historialViajes", historialViajes);

        // Calcular la suma de ganancias
        double totalGanado = historialViajes.stream()
                .mapToDouble(Viaje::getPrecio)
                .sum();
        model.addAttribute("totalGanado", totalGanado);

        model.addAttribute("conductor", conductor);
        return "VConductor"; // La vista de conductor
    }

    @PostMapping("/iniciarViaje")
    public String iniciarViaje(@RequestParam Long viajeId, RedirectAttributes redirectAttributes) {
        try {
            String mensaje = viajeService.iniciarViaje(viajeId);
            redirectAttributes.addFlashAttribute("successInicio", mensaje);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorInicio", "Hubo un problema al iniciar el viaje.");
        }

        return "redirect:/VConductor?panel=misViajes";
    }

    @PostMapping("/finalizarViaje")
    public String finalizarViaje(@RequestParam Long viajeId, RedirectAttributes redirectAttributes) {
        try {
            String mensaje = viajeService.finalizarViaje(viajeId);
            redirectAttributes.addFlashAttribute("successFinalizar", mensaje);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorFinalizar", "Hubo un problema al finalizar el viaje.");
        }

        return "redirect:/VConductor?panel=misViajes";
    }


}
