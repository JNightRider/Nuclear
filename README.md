# Nuclear - (Nuklear)

A library that provides JME3 compatibility for integrating the ANSI C Nuklear
graphical user interface library using LWJGL3 bindings.

The name of this library comes from a play on words in two languages, in which 
only the letter K is changed to C, which sound the same in Spanish.

![Nuclear - JME3](./src/test/resources/captura.png)

The graphical interface is rendered using post-processing provided by JME3; its
architecture is inspired by the Nifty module integrated into JME.

**Libraries used**

- JME3
- Nuklear
- LWJGL3

## Building with Nuclear

jMe3GL2 can be added as a normal dependency using the stable jar files or using _**JitPack**_ as follows:

**Step 1. Add the JitPack maven repository**

```gradle
    maven { url "https://jitpack.io"  }
```

**Step 2. Add the necessary dependencies**

```gradle
...
ext {
    lwjglNatives = "natives-linux"
    lwjglVersion = "3.3.6"
}
...

    implementation platform("org.lwjgl:lwjgl-bom:$lwjglVersion")
    implementation "org.lwjgl:lwjgl-nuklear"
    implementation "org.lwjgl:lwjgl-stb"
    
    implementation "org.lwjgl:lwjgl-stb::$lwjglNatives"
    implementation "org.lwjgl:lwjgl-nuklear::$lwjglNatives"
```

**Starting Nuclear**

If this is your first time using Nuclear, you can consult some of these resources 
to guide you in using this great library.

- [Muestra](./src/test/java/org/nrr/nk/test/NkSimpleApplication.java)
- [Con un estilo de JME3](./src/test/java/org/nrr/nk/test/NkJmeApplication.java)

If you still don't know how to use or how Nuklear (C) works, you can check out the website of
**Alexander Fox**, who has done a great job explaining the logic
and more.

[Ir la turorial](https://www.thecodingfox.com/nuklear-usage-guide-lwjgl)
