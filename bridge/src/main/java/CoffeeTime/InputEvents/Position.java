// automatically generated by the FlatBuffers compiler, do not modify

package CoffeeTime.InputEvents;

import com.google.flatbuffers.BaseVector;
import com.google.flatbuffers.BooleanVector;
import com.google.flatbuffers.ByteVector;
import com.google.flatbuffers.Constants;
import com.google.flatbuffers.DoubleVector;
import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.FloatVector;
import com.google.flatbuffers.IntVector;
import com.google.flatbuffers.LongVector;
import com.google.flatbuffers.ShortVector;
import com.google.flatbuffers.StringVector;
import com.google.flatbuffers.Struct;
import com.google.flatbuffers.Table;
import com.google.flatbuffers.UnionVector;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

@SuppressWarnings("unused")
public final class Position extends Table {
  public static void ValidateVersion() { Constants.FLATBUFFERS_23_5_26(); }
  public static Position getRootAsPosition(ByteBuffer _bb) { return getRootAsPosition(_bb, new Position()); }
  public static Position getRootAsPosition(ByteBuffer _bb, Position obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public void __init(int _i, ByteBuffer _bb) { __reset(_i, _bb); }
  public Position __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  public float x() { int o = __offset(4); return o != 0 ? bb.getFloat(o + bb_pos) : 0.0f; }
  public float y() { int o = __offset(6); return o != 0 ? bb.getFloat(o + bb_pos) : 0.0f; }

  public static int createPosition(FlatBufferBuilder builder,
      float x,
      float y) {
    builder.startTable(2);
    Position.addY(builder, y);
    Position.addX(builder, x);
    return Position.endPosition(builder);
  }

  public static void startPosition(FlatBufferBuilder builder) { builder.startTable(2); }
  public static void addX(FlatBufferBuilder builder, float x) { builder.addFloat(0, x, 0.0f); }
  public static void addY(FlatBufferBuilder builder, float y) { builder.addFloat(1, y, 0.0f); }
  public static int endPosition(FlatBufferBuilder builder) {
    int o = builder.endTable();
    return o;
  }

  public static final class Vector extends BaseVector {
    public Vector __assign(int _vector, int _element_size, ByteBuffer _bb) { __reset(_vector, _element_size, _bb); return this; }

    public Position get(int j) { return get(new Position(), j); }
    public Position get(Position obj, int j) {  return obj.__assign(__indirect(__element(j), bb), bb); }
  }
}

