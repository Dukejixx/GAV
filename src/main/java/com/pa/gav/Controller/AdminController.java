    package com.pa.gav.Controller;

    import com.pa.gav.Entity.Administrador;
    import com.pa.gav.Entity.Conductor;
    import com.pa.gav.Entity.Vehiculo;
    import com.pa.gav.Entity.Viaje;
    import com.pa.gav.Exeptions.*;
    import com.pa.gav.Services.ConductorService;
    import com.pa.gav.Services.VehiculoService;
    import com.pa.gav.Services.ViajeService;
    import jakarta.servlet.http.HttpSession;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.PathVariable;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestParam;


    import java.util.ArrayList;
    import java.util.List;

    @Controller
    public class AdminController {

        @Autowired
        private VehiculoService vehiculoService;

        @Autowired
        private ConductorService conductorService;

        @Autowired
        private ViajeService viajeService;

        @GetMapping("/VAdmin")
        public String showAdminPage(@RequestParam(value = "panel", required = false) String panel, HttpSession session, Model model) {
            Administrador administrador = (Administrador) session.getAttribute("admin");
            if (administrador == null) {
                return "redirect:/Login_admin";
            }

            // Manejo de paneles
            List<String> activePanels = new ArrayList<>();
            if ("conductores".equals(panel)) {
                activePanels.add("conductores");
            } else if ("vehiculos".equals(panel)) {
                activePanels.add("vehiculos");
            } else if ("registroVehiculo".equals(panel)) {
                activePanels.add("registroVehiculo");
            } else if ("registroConductor".equals(panel)) {
                activePanels.add("registroConductor");
            } else if ("solicitudes".equals(panel)) {
                activePanels.add("solicitudes");
            } else if ("historialViajes".equals(panel)) {
                activePanels.add("historialViajes");
            }

            model.addAttribute("activePanels", activePanels);

            // Recargar listas de conductores y vehículos
            try {
                Iterable<Conductor> conductores = conductorService.findAll();
                model.addAttribute("conductores", conductores);
            } catch (Exception e) {
                model.addAttribute("errorConductores", "Error al cargar la lista de conductores.");
                model.addAttribute("conductores", new ArrayList<>());
            }

            try {
                Iterable<Vehiculo> vehiculos = vehiculoService.findAll();
                model.addAttribute("vehiculos", vehiculos);
            } catch (Exception e) {
                model.addAttribute("errorVehiculos", "Error al cargar la lista de vehículos.");
                model.addAttribute("vehiculos", new ArrayList<>());
            }

            // Obtener los viajes que están en estado SOLICITADO
            try {
                List<Viaje> viajesPendientes = viajeService.findByEstado(Viaje.EstadoViaje.SOLICITADO);
                model.addAttribute("viajesPendientes", viajesPendientes);
            } catch (Exception e) {
                model.addAttribute("errorViajes", "Error al cargar los viajes solicitados.");
                model.addAttribute("viajesPendientes", new ArrayList<>());
            }

            // Obtener los viajes completados (historial de viajes)
            try {
                List<Viaje> historialViajes = viajeService.findByEstado(Viaje.EstadoViaje.COMPLETADO);
                model.addAttribute("historialViajes", historialViajes);

                // Calcular la suma total de las ganancias
                double totalGanado = historialViajes.stream()
                        .mapToDouble(Viaje::getPrecio)
                        .sum();
                model.addAttribute("totalGanado", totalGanado);
            } catch (Exception e) {
                model.addAttribute("errorHistorial", "Error al cargar el historial de viajes.");
                model.addAttribute("historialViajes", new ArrayList<>());
                model.addAttribute("totalGanado", 0.0);
            }

            return "VAdmin";
        }



        // Métodos CRUD Vehículos
        @PostMapping("/registroVehiculo")
        public String registrarVehiculo(Vehiculo vehiculo, Model model) {
            try {
                vehiculoService.save(vehiculo);
                model.addAttribute("successRegistroVehiculo", "Vehículo registrado exitosamente.");
                model.addAttribute("activePanel", "registroVehiculo");
                model.addAttribute("vehiculo", new Vehiculo());
                model.addAttribute("conductores", conductorService.findAll());
                model.addAttribute("vehiculos", vehiculoService.findAll());
                model.addAttribute("viajesPendientes", viajeService.findByEstado(Viaje.EstadoViaje.SOLICITADO));

                return "/VAdmin";
            } catch (PlacaDuplicadaException e) {
                model.addAttribute("errorRegistroVehiculo", e.getMessage());
                model.addAttribute("activePanel", "registroVehiculo");


                model.addAttribute("vehiculo", vehiculo);
                model.addAttribute("conductores", conductorService.findAll());
                model.addAttribute("vehiculos", vehiculoService.findAll());
                model.addAttribute("viajesPendientes", viajeService.findByEstado(Viaje.EstadoViaje.SOLICITADO));



                return "VAdmin";
            }
        }

        @GetMapping("/ModificarVehiculo/{id}")
        public String cargarFormularioModificacion(@PathVariable("id") Long id, Model model) {
            Vehiculo vehiculo = vehiculoService.findById(id);
            if (vehiculo == null) {
                model.addAttribute("error", "El vehículo no fue encontrado.");
                model.addAttribute("activePanel", "vehiculos");
                return "VAdmin";
            }
            model.addAttribute("vehiculo", vehiculo);
            return "ModificarVehiculo";
        }

        @PostMapping("/guardarModificacionVehiculo")
        public String guardarModificacionVehiculo(Vehiculo vehiculo, Model model) {
            try {
                vehiculoService.update(vehiculo);

                model.addAttribute("vehiculos", vehiculoService.findAll());
                model.addAttribute("conductores", conductorService.findAll());
                model.addAttribute("viajesPendientes", viajeService.findByEstado(Viaje.EstadoViaje.SOLICITADO));


                model.addAttribute("successVehiculos", "Vehículo modificado exitosamente.");
                model.addAttribute("activePanel", "vehiculos");

                model.addAttribute("vehiculo", null);

                return "VAdmin";
            } catch (PlacaDuplicadaException e) {

                model.addAttribute("errorRegistroVehiculo", "Ya existe un vehículo con esa placa.");

                model.addAttribute("vehiculo", vehiculo);

                return "ModificarVehiculo";
            }
        }

        @PostMapping("/eliminarVehiculo/{id}")
        public String eliminarVehiculo(@PathVariable("id") Long id, Model model) {
            try {
                vehiculoService.delete(id);
                model.addAttribute("successVehiculoS", "Vehículo eliminado exitosamente.");
            } catch (Exception e) {
                model.addAttribute("errorVehiculos", "No se pudo eliminar el vehículo.");
            }

            model.addAttribute("vehiculos", vehiculoService.findAll());
            model.addAttribute("conductores", conductorService.findAll());
            model.addAttribute("viajesPendientes", viajeService.findByEstado(Viaje.EstadoViaje.SOLICITADO));


            model.addAttribute("activePanel", "vehiculos");

            return "VAdmin";
        }

        // Métodos CRUD Conductores
        @PostMapping("/registroConductor")
        public String registrarConductor(Conductor conductor, Model model) {
            try {
                conductor.setDisponible(true);
                conductorService.save(conductor);
                model.addAttribute("successRegistroConductor", "Conductor registrado exitosamente.");
                model.addAttribute("activePanel", "registroConductor");
                model.addAttribute("conductor", new Conductor());
                model.addAttribute("vehiculos", vehiculoService.findAll());
                model.addAttribute("conductores", conductorService.findAll());
                model.addAttribute("viajesPendientes", viajeService.findByEstado(Viaje.EstadoViaje.SOLICITADO));

                return "/VAdmin";
            } catch (RuntimeException e) {
                model.addAttribute("errorRegistroConductor", e.getMessage());
                model.addAttribute("activePanel", "registroConductor");

                model.addAttribute("conductor", conductor);
                model.addAttribute("vehiculos", vehiculoService.findAll());
                model.addAttribute("conductores", conductorService.findAll());
                model.addAttribute("viajesPendientes", viajeService.findByEstado(Viaje.EstadoViaje.SOLICITADO));


                return "VAdmin";
            }
        }

        @GetMapping("/ModificarConductor/{id}")
        public String cargarFormularioModificacionConductor(@PathVariable("id") Long id, Model model) {
            Conductor conductor = conductorService.findById(id);
            if (conductor == null) {
                model.addAttribute("error", "El conductor no fue encontrado.");
                model.addAttribute("activePanel", "conductores");
                return "VAdmin";
            }
            model.addAttribute("conductor", conductor);
            model.addAttribute("vehiculos", vehiculoService.findAll());
            model.addAttribute("viajesPendientes", viajeService.findByEstado(Viaje.EstadoViaje.SOLICITADO));

            return "ModificarConductor";
        }

        @PostMapping("/guardarModificacionConductor")
        public String guardarModificacionConductor(Conductor conductor, Model model) {
            try {
                conductor.setDisponible(true);
                conductorService.update(conductor); // Actualiza el conductor

                model.addAttribute("conductores", conductorService.findAll());
                model.addAttribute("vehiculos", vehiculoService.findAll());
                model.addAttribute("viajesPendientes", viajeService.findByEstado(Viaje.EstadoViaje.SOLICITADO));


                model.addAttribute("successConductor", "Conductor modificado exitosamente.");
                model.addAttribute("activePanel", "conductores");

                model.addAttribute("conductor", null);

                return "VAdmin";
            } catch (RuntimeException e) {

                model.addAttribute("errorModificarConductor", e.getMessage());
                model.addAttribute("conductor", conductor);

                return "ModificarConductor";
            }
        }

        @PostMapping("/eliminarConductor/{id}")
        public String eliminarConductor(@PathVariable("id") Long id, Model model) {
            try {
                conductorService.delete(id);
                model.addAttribute("successConductores", "Conductor eliminado exitosamente.");
            } catch (Exception e) {
                model.addAttribute("errorConductores", "No se pudo eliminar el conductor.");
            }

            model.addAttribute("conductores", conductorService.findAll());
            model.addAttribute("vehiculos", vehiculoService.findAll());
            model.addAttribute("viajesPendientes", viajeService.findByEstado(Viaje.EstadoViaje.SOLICITADO));

            model.addAttribute("activePanel", "conductores");

            return "VAdmin";
        }

        // LOGICA DE ASIGNACION
        @GetMapping("/asignarConductor/{id}")
        public String cargarVistaAsignacion(@PathVariable("id") Long id, Model model) {
            try {
                Viaje viaje = viajeService.findById(id);
                if (viaje == null || !viaje.getEstadoViaje().equals(Viaje.EstadoViaje.SOLICITADO)) {
                    model.addAttribute("error", "El viaje no existe o no está pendiente.");
                    return "redirect:/VAdmin";
                }

                List<Conductor> conductoresDisponibles = conductorService.findByDisponible(true);

                model.addAttribute("viaje", viaje);
                model.addAttribute("conductoresDisponibles", conductoresDisponibles);
                return "AsignacionViaje";
            } catch (Exception e) {
                model.addAttribute("error", "Error al cargar el viaje para asignación.");
                return "redirect:/VAdmin";
            }
        }

        @PostMapping("/guardarAsignacion")
        public String guardarAsignacion(
                @RequestParam("viajeId") Long viajeId,
                @RequestParam("conductorId") Long conductorId,
                @RequestParam("precio") Double precio,
                Model model) {
            try {

                Viaje viaje = viajeService.findById(viajeId);
                if (viaje == null || !viaje.getEstadoViaje().equals(Viaje.EstadoViaje.SOLICITADO)) {
                    model.addAttribute("error", "El viaje no existe o ya fue asignado.");
                    return prepararVistaAsignacion(model);
                }

                Conductor conductor = conductorService.findById(conductorId);
                if (conductor == null || !conductor.getDisponible()) {
                    model.addAttribute("error", "El conductor no existe o no está disponible.");
                    return prepararVistaAsignacion(model);
                }

                viaje.setConductor(conductor);
                viaje.setPrecio(precio);
                viaje.setEstadoViaje(Viaje.EstadoViaje.ASIGNADO);
                viajeService.update(viaje);

                conductor.setDisponible(false);
                conductorService.update(conductor);

                model.addAttribute("successAsignado", "Viaje asignado exitosamente.");
                return prepararVistaAsignacion(model);
            } catch (Exception e) {
                model.addAttribute("errorAsignado", "Error al asignar el viaje: " + e.getMessage());
                return prepararVistaAsignacion(model);
            }
        }

        // METODO AUXILIAR
        private String prepararVistaAsignacion(Model model) {
            model.addAttribute("conductores", conductorService.findAll());
            model.addAttribute("vehiculos", vehiculoService.findAll());
            model.addAttribute("viajesPendientes", viajeService.findByEstado(Viaje.EstadoViaje.SOLICITADO));
            model.addAttribute("activePanel", "solicitudes");
            return "VAdmin";
        }







    }
