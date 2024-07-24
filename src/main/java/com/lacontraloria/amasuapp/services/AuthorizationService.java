package com.lacontraloria.amasuapp.services;

import com.lacontraloria.amasuapp.adapters.dto.AuthDTO;
import com.lacontraloria.amasuapp.adapters.exceptions.NotFoundException;
import com.lacontraloria.amasuapp.adapters.repositories.PersonaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    private final PersonaRepository personaRepository;

    public AuthorizationService(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String reqEmailPrincipal) throws UsernameNotFoundException {
       return personaRepository.findByEmailPrincipal(reqEmailPrincipal)
                .orElseThrow(() -> new NotFoundException("No emailPrincipal " + reqEmailPrincipal + " into the data base."));

    }
}
