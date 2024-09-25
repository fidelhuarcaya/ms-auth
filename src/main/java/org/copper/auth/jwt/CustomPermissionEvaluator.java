package org.copper.auth.jwt;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public class CustomPermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if (authentication == null || targetDomainObject == null || permission == null) {
            return false;
        }

        if (!(authentication.getPrincipal() instanceof UserDetails userDetails)) {
            return false; // No se puede verificar sin un UserDetails en la autenticación.
        }

        List<CustomGrantedAuthority> grantedAuthority = (List<CustomGrantedAuthority>) userDetails.getAuthorities();

        return userDetails.getAuthorities().stream().anyMatch(authority ->
                grantedAuthority.stream().anyMatch(customGrantedAuthority -> customGrantedAuthority.getRole().equals(targetDomainObject)) &&
                        grantedAuthority.stream().anyMatch(customGrantedAuthority ->
                                customGrantedAuthority.getAuthority().equals(permission.toString())));
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        // Implementa la lógica si necesitas verificar permisos sobre un objeto específico.
        return false;
    }
}
