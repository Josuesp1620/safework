package com.dooservice.safework;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

/**
 * The walls between modules, checked by the build rather than by discipline.
 *
 * This fails when one module reaches into another's internals instead of its
 * published surface. It is here from the first commit on purpose: boundaries
 * added later are boundaries that were already crossed.
 */
class ModularityTests {

    private final ApplicationModules modules = ApplicationModules.of(SafeworkApplication.class);

    @Test
    void modulesDoNotReachIntoEachOther() {
        modules.verify();
    }

    @Test
    void writeTheModuleDocumentation() {
        new Documenter(modules).writeDocumentation();
    }
}
