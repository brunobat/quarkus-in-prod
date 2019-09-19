package org.acme.hero.camel;

import io.quarkus.test.junit.QuarkusTest;
import org.acme.hero.model.Hero;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
class SuperHeroCreatorTest {

    @Inject
    SuperHeroCreator creator;

    @Test
    void add() {
        final Hero hero = creator.add("broccoli");
        assertNotNull(hero);
        assertNotNull(hero.getId());
        assertEquals("SUPER-broccoli", hero.getName());
    }
}