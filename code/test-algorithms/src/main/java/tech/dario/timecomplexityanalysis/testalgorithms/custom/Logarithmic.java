package tech.dario.timecomplexityanalysis.testalgorithms.custom;

public class Logarithmic {
  // quick(1000) ~ 1ms ~ 3000000 iterations
  public void quick(long n) {
    double v = Math.log(n) * 434294L;
    for (long i = 0; i < v; i++) {

    }
  }

  // average(1000) ~ 10ms ~ 30000000 iterations
  public void average(long n) {
    double v = Math.log(n) * 4342945L;
    for (long i = 0; i < v; i++) {

    }
  }

  // slow(1000) ~ 100ms ~ 300000000 iterations
  public void slow(long n) {
    double v = Math.log(n) * 43429448;
    for (long i = 0; i < v; i++) {

    }
  }
}
