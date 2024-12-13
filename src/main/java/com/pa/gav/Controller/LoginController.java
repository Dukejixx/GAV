package com.pa.gav.Controller;

import com.pa.gav.Entity.Administrador;
import com.pa.gav.Entity.Cliente;
import com.pa.gav.Entity.Conductor;
import com.pa.gav.Repository.AdministradorRepository;
import com.pa.gav.Repository.ClienteRepository;
import com.pa.gav.Repository.ConductorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private AdministradorRepository administradorRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ConductorRepository conductorRepository;

    @GetMapping("/Login_admin")
    public String showLoginAdmin() {
        return "Login_admin";
    }


    @PostMapping("/Login_admin")
    public String handleAdminLogin(String username, String password, Model model, HttpSession session) {
        Administrador admin = administradorRepository.findByUsername(username);
        if (admin != null && admin.getPassword().equals(password)) {
            session.setAttribute("admin", admin);
            return "redirect:/VAdmin";
        } else {
            model.addAttribute("error", "Credenciales incorrectas");
            return "Login_admin";
        }
    }


    @GetMapping("/Login_cliente")
    public String showLoginCliente() {
        return "Login_cliente";
    }

    @GetMapping("/Register_cliente")
    public String showRegisterCliente(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "Register_cliente";
    }



    @PostMapping("/Login_cliente")
    public String handleClienteLogin(String username, String password, Model model, HttpSession session) {
        Cliente cliente = clienteRepository.findByUsername(username);
        if (cliente != null && cliente.getPassword().equals(password)) {
            session.setAttribute("cliente", cliente);
            System.out.println("Cliente guardado en sesión: " + cliente.getUsername());
            return "redirect:/VCliente";
        } else {
            model.addAttribute("error", "Credenciales incorrectas");
            return "Login_cliente";
        }
    }

    @PostMapping("/Register_cliente")
    public String registerCliente(Cliente cliente, Model model) {
        if (clienteRepository.findByUsername(cliente.getUsername()) != null) {
            model.addAttribute("error", "El nombre de usuario ya está en uso.");
            return "Register_cliente";
        }
        if (clienteRepository.findByNumeroDocumento(cliente.getNumeroDocumento()) != null) {
            model.addAttribute("error", "El número de documento ya está en uso.");
            return "Register_cliente";
        }
        if (clienteRepository.findByTelefono(cliente.getTelefono()) != null) {
            model.addAttribute("error", "El número de telefono ya está en uso.");
            return "Register_cliente";
        }
        if (clienteRepository.findByEmail(cliente.getEmail()) != null) {
            model.addAttribute("error", "El Email ya está en uso.");
            return "Register_cliente";

        }clienteRepository.save(cliente);

        return "redirect:/Login_cliente";
    }


    @GetMapping("/Login_conductor")
    public String showLoginConductor() {
        return "Login_conductor";
    }


    @PostMapping("/Login_conductor")
    public String handleConductorLogin(String username, String password, Model model, HttpSession session) {
        Conductor conductor = conductorRepository.findByUsername(username);
        if (conductor != null && conductor.getPassword().equals(password)) {
            session.setAttribute("conductor", conductor);
            return "redirect:/VConductor";
        } else {
            model.addAttribute("error", "Credenciales incorrectas");
            return "Login_conductor";
        }
    }


    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
