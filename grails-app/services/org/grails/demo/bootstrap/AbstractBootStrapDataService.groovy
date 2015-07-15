package org.grails.demo.bootstrap

abstract class AbstractBootStrapDataService implements BootStrapDataService {

    void loadAllData() {
        loadDefault()
        loadTestData()
    }

    abstract void loadDefault()

    abstract void loadTestData()
}
