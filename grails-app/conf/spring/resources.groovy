import com.netflix.hystrix.contrib.javanica.aop.aspectj.HystrixCommandAspect

beans = {

    hystrixAspect(HystrixCommandAspect)

//    authenticationManager(ProviderManager, [ref('activeDirectoryAuthenticationProvider')])
//
//    activeDirectoryAuthenticationProvider(ActiveDirectoryLdapAuthenticationProvider, 'demo.grails.org', 'ldaps://prod.corp.com')

}
