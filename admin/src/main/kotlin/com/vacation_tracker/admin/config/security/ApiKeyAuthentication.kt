package com.vacation_tracker.admin.config.security

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority



class ApiKeyAuthentication(private var apiKey: String?, authorities: Collection<GrantedAuthority?>?) :
    AbstractAuthenticationToken(authorities) {

    init {
        isAuthenticated = true
    }

    override fun getCredentials(): Any? {
        return null
    }

    override fun getPrincipal(): Any? {
        return apiKey
    }

}