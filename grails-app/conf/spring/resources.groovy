import com.netflix.hystrix.contrib.javanica.aop.aspectj.HystrixCommandAspect
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider

// Place your Spring DSL code here
beans = {
    hystrixAspect(HystrixCommandAspect)

    authenticationManager(ProviderManager, [ref('activeDirectoryAuthenticationProvider')])
//
    activeDirectoryAuthenticationProvider(ActiveDirectoryLdapAuthenticationProvider, 'demo.grails.org', 'ldaps://prod.corp.com')

}
