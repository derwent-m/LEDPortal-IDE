package heronarts.lx.model;

import java.util.ArrayList;
import java.util.List;

import heronarts.lx.output.SerialMessage;

/**
 * Extends {@link SerialModel} to allow for sending serial messages in addition to datagrams
 */
public class SerialModel extends LXModel {
  // TODO(dev): is SerialModel necessary?

  /**
   * An ordered list of messages that should be sent for this model.
   */
  public final List<SerialMessage> messages = new ArrayList<SerialMessage>();

  /**
   * Constructs a null model with no points
   */
  public SerialModel() {
    this(new ArrayList<LXPoint>());
  }

  /**
   * Constructs a model from a list of points
   *
   * @param points Points in the model
   */
  public SerialModel(List<LXPoint> points) {
    this(points, new SerialModel[0]);
  }

  /**
   * Constructs a model from a list of points
   *
   * @param points Points in the model
   * @param keys Key identifiers for the model type
   */
  public SerialModel(List<LXPoint> points, String ... keys) {
    this(points, new SerialModel[0], keys);
  }

  /**
   * Constructs a model with a given set of points and pre-constructed children. In this case, points
   * from the children are not added to the points array, they are assumed to already be contained by
   * the points list.
   *
   * @param points Points in this model
   * @param children Pre-built direct child model array
   */
  public SerialModel(List<LXPoint> points, LXModel[] children) {
    this(points, children, SerialModel.Key.MODEL);
  }

  /**
   * Constructs a model with a given set of points and pre-constructed submodels. In this case, points
   * from the submodels are not added to the points array, they are assumed to already be contained by
   * the points list.
   *
   * @param points Points in this model
   * @param children Pre-built direct submodel child array
   * @param keys Key identifier for this model
   */
  public SerialModel(List<LXPoint> points, LXModel[] children, String ... keys) {
    super(points, children, keys);
  }
  // public SerialModel(List<LXPoint> points, SerialModel[] children, String ... keys) {
  //   this.keys = validateKeys(keys);
  //   this.pointList = Collections.unmodifiableList(new ArrayList<LXPoint>(points));
  //   addChildren(children);
  //   this.children = children.clone();
  //   this.points = this.pointList.toArray(new LXPoint[0]);
  //   this.size = this.points.length;
  //   this.datagrams = Collections.unmodifiableList(new ArrayList<LXDatagram>());
  //   recomputeGeometry();
  // }

  // /**
  //  * Constructs a model from the given submodels. The point list is generated from
  //  * all points in the submodels, on the assumption that they have not yet been
  //  * added.
  //  *
  //  * @param children Sub-models
  //  */
  // public SerialModel(SerialModel[] children) {
  //   this(children, SerialModel.Key.MODEL);
  // }

  // /**
  //  *
  //  * Constructs a model from the given submodels. The point list is generated from
  //  * all points in the submodels, on the assumption that they have not yet been
  //  * added.
  //  *
  //  * @param children Pre-built sub-models
  //  * @param keys Key identifier for this model
  //  */
  // private SerialModel(SerialModel[] children, String ... keys) {
  //   this.keys = validateKeys(keys);
  //   List<LXPoint> _points = new ArrayList<LXPoint>();
  //   addChildren(children);
  //   for (SerialModel child : children) {
  //     for (LXPoint p : child.points) {
  //       _points.add(p);
  //     }
  //   }
  //   this.children = children.clone();
  //   this.points = _points.toArray(new LXPoint[0]);
  //   this.pointList = Collections.unmodifiableList(_points);
  //   this.size = _points.size();
  //   this.datagrams = Collections.unmodifiableList(new ArrayList<LXDatagram>());
  //   recomputeGeometry();
  // }

  // public SerialModel(SerialModelBuilder builder) {
  //   this(builder, true);
  // }

  // protected SerialModel(SerialModelBuilder builder, boolean isRoot) {
  //   if (builder.model != null) {
  //     throw new IllegalStateException("SerialModelBuilder may only be used once: " + builder);
  //   }
  //   this.keys = validateKeys(builder.keys.toArray(new String[0]));
  //   this.children = new SerialModel[builder.children.size()];
  //   List<LXPoint> _points = new ArrayList<LXPoint>(builder.points);
  //   int ci = 0;
  //   for (SerialModelBuilder child : builder.children) {
  //     this.children[ci++] = new SerialModel(child, false);
  //     for (LXPoint p : child.points) {
  //       _points.add(p);
  //     }
  //   }
  //   addChildren(children);
  //   this.points = _points.toArray(new LXPoint[0]);
  //   this.pointList = Collections.unmodifiableList(_points);
  //   this.size = this.points.length;
  //   this.datagrams = Collections.unmodifiableList(new ArrayList<LXDatagram>(builder.datagrams));
  //   recomputeGeometry();
  //   if (isRoot) {
  //     reindexPoints();
  //     normalizePoints();
  //   }
  //   builder.model = this;
  // }
}
