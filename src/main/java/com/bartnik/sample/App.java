package com.bartnik.sample;

import java.time.LocalDate;
import java.util.UUID;

public class App {
  public static void main(final String [] args) {
    final EtlWorkflow etlPipe = new EtlWorkflow(); // TODO this could come from a @Repository
    etlPipe.initiate(UUID.randomUUID(), LocalDate.now());
  } 
}
