//package rs.rbt.internship.admin.security
//
//import org.springframework.security.authentication.AbstractAuthenticationToken
//import org.springframework.security.core.authority.AuthorityUtils
//
//class ApiKeyAuthenticationToken(private var apiKey: String, authenticated: Boolean) : AbstractAuthenticationToken(AuthorityUtils.NO_AUTHORITIES) {
//
//    init {
//        this.apiKey = apiKey
//        isAuthenticated = authenticated
//    }
//
//    constructor(apiKey: String) : this(apiKey, false)
//
//    override fun getCredentials(): Any? {
//        return null
//    }
//
//    override fun getPrincipal(): Any {
//        return apiKey
//    }
//}
