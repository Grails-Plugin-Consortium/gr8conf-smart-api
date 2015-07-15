includeTargets << grailsScript("_GrailsInit")
includeTargets << grailsScript("_GrailsBootstrap")
includeTargets << grailsScript("_GrailsRun")
includeTargets << grailsScript("_GrailsSettings")
includeTargets << grailsScript("_GrailsClean")
includeTargets << new File("$cxfPluginDir/scripts/WsdlToJava.groovy")


//run this with `grails regenerate-services`
target(regenerateServices: "The description of the script goes here!") {

    depends(compile, createConfig, parseArguments, classpath)

    config.external.services.each {
        args = it.value
        wsdlToJava()
    }
}

setDefaultTarget(regenerateServices)
