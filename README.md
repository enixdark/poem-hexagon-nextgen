# introduction
[![Build Status](https://travis-ci.org/bertilmuth/poem-hexagon-nextgen.svg?branch=master)](https://travis-ci.org/bertilmuth/poem-hexagon-nextgen)

This is the next generation of the [poem-hexagon](https://github.com/bertilmuth/poem-hexagon) application.
A simple example for a clean hexagonal architecture.

The difference to the poem-hexagon application:

Instead of writing a poem's lines with a driven adapter, the hexagon publishes them as events. An [EventPublisher](https://github.com/bertilmuth/poem-hexagon-nextgen/blob/master/src/main/java/poem/boundary/EventPublisher.java) then broadcasts the events to event receivers. In this case, `ConsoleWriter` is the only event receiver, but there could be more. 

The main class is [poem.simple.Main](https://github.com/bertilmuth/poem-hexagon-nextgen/blob/master/src/main/java/poem/simple/Main.java).

A [blog article](https://dev.to/bertilmuth/implementing-a-hexagonal-architecture-1kgf) describes the details.
