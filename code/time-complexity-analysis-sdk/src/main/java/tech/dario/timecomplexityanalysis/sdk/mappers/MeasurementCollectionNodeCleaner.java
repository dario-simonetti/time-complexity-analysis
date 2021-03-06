package tech.dario.timecomplexityanalysis.sdk.mappers;

import java.util.Collection;
import org.apache.commons.math3.distribution.TDistribution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.dario.timecomplexityanalysis.timerecorder.tree.AbstractNode;
import tech.dario.timecomplexityanalysis.timerecorder.tree.MergeableNode;
import tech.dario.timecomplexityanalysis.timerecorder.tree.Measurement;
import tech.dario.timecomplexityanalysis.timerecorder.tree.MergeableCollection;

import java.util.function.Function;
import java.util.stream.Collectors;

public class MeasurementCollectionNodeCleaner<T extends AbstractNode<MergeableCollection<Measurement>, T>> implements Function<T, MergeableNode<MergeableCollection<Measurement>>> {

  private static final Logger LOGGER = LoggerFactory.getLogger(MeasurementCollectionNodeCleaner.class);

  private final int numMaxOutliers;

  public MeasurementCollectionNodeCleaner(final int numMaxOutliers) {
    this.numMaxOutliers = numMaxOutliers;
  }

  @Override
  public MergeableNode<MergeableCollection<Measurement>> apply(T node) {
    if (node.getData() == null) {
      return new MergeableNode<>(node.getName(), null);
    }

    final Collection<Measurement> measurementCollection = node.getData().getCollection();
    final Collection<Measurement> measurementCollectionWithoutOutliers = removeOutliers(measurementCollection);
    return new MergeableNode<>(node.getName(), new MergeableCollection<>(measurementCollectionWithoutOutliers));
  }

  private Collection<Measurement> removeOutliers(Collection<Measurement> measurementCollection) {
    // Using generalized extreme Studentized deviate (ESD)
    // See: http://www.itl.nist.gov/div898/handbook/eda/section3/eda35h3.htm
    double[] xs = measurementCollection.stream().map(Measurement::getTotal).mapToDouble(m -> m).toArray();
    if(xs.length <= numMaxOutliers) {
      return measurementCollection;
    }

    double alpha = 0.05d;

    double validRangeMin = min(xs);
    double validRangeMax = max(xs);

    for (int i = 1; i <= numMaxOutliers; i++) {
      double avg = avg(xs);
      double stdev = stdev(avg, xs);
      int mostOutlyingIndex = mostOutlyingIndex(avg, xs);
      double mostOutlying = xs[mostOutlyingIndex];

      double R = Math.abs(mostOutlying - avg) / stdev;

      double p = 1 - (alpha / (2 * xs.length));
      double tpv = tDistributionInverseCumulativeProbability(p, xs.length - 2);
      double lambda = (xs.length - 1) * tpv / Math.sqrt((xs.length - 2 + Math.pow(tpv, 2)) * xs.length);

      xs = removeElement(xs, mostOutlyingIndex);

      if (R > lambda) {
        validRangeMin = min(xs);
        validRangeMax = max(xs);
      }
    }

    final double finalValidRangeMin = validRangeMin;
    final double finalValidRangeMax = validRangeMax;

    final Collection<Measurement> cleanedMeasurementCollection = measurementCollection
        .stream()
        .filter(
            m -> m.getTotal() >= finalValidRangeMin && m.getTotal() <= finalValidRangeMax
        )
        .collect(Collectors.toList());
    LOGGER.debug("Removed " + (measurementCollection.size() - cleanedMeasurementCollection.size()) + " elements");
    return cleanedMeasurementCollection;
  }

  private double avg(double[] xs) {
    double sum = 0.0d;
    for (double x : xs) {
      sum += x;
    }

    return sum / xs.length;
  }

  private double min(double[] xs) {
    double currentMin = Double.POSITIVE_INFINITY;
    for (double x : xs) {
      currentMin = Math.min(currentMin, x);
    }

    return currentMin;
  }

  private double max(double[] xs) {
    double currentMax = Double.NEGATIVE_INFINITY;
    for (double x : xs) {
      currentMax = Math.max(currentMax, x);
    }

    return currentMax;
  }

  private double stdev(double avg, double[] xs) {
    double sum = 0.0d;
    for (double x : xs) {
      sum += Math.pow(x - avg, 2);
    }

    return Math.sqrt(sum / (xs.length - 1));
  }

  private double tDistributionInverseCumulativeProbability(double p, int v) {
    return new TDistribution(v).inverseCumulativeProbability(p);
  }

  private double[] removeElement(double[] original, int i) {
    double[] n = new double[original.length - 1];
    System.arraycopy(original, 0, n, 0, i);
    System.arraycopy(original, i + 1, n, i, original.length - i - 1);
    return n;
  }

  private int mostOutlyingIndex(double avg, double[] xs) {
    double mostOutlying = Double.NEGATIVE_INFINITY;
    int mostOutlyingIndex = -1;
    for (int i = 0; i < xs.length; i++) {
      double diff = Math.abs(xs[i] - avg);
      if (diff > mostOutlying) {
        mostOutlying = diff;
        mostOutlyingIndex = i;
      }
    }

    return mostOutlyingIndex;
  }
}
