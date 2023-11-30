plugins {
    kotlin("jvm") version "1.7.10"
    id("com.diffplug.spotless") version "6.23.1"
}

repositories { mavenCentral() }

spotless {
    kotlin { ktfmt().kotlinlangStyle() }
    kotlinGradle { ktfmt().kotlinlangStyle() }
}
