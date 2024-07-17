package com.vacation_tracker.admin.config.security

import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.AuthorityUtils


class AuthenticationService {

    companion object {
        private const val AUTH_TOKEN_HEADER_NAME: String = "X-API-KEY"
        private const val AUTH_TOKEN: String = "secret-api-key"

        fun getAuthentication(request: HttpServletRequest?): Authentication {
            val apiKey = request?.getHeader(AUTH_TOKEN_HEADER_NAME)
            if (apiKey == null || apiKey != AUTH_TOKEN) {
                throw BadCredentialsException("Invalid API Key")
            }

            return ApiKeyAuthentication(apiKey, AuthorityUtils.NO_AUTHORITIES)
        }
    }
}