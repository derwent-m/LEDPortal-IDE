package flavius.pixelblaze.output;

import heronarts.lx.LX;
import heronarts.lx.output.LXOutput;
import java.nio.file.Files;
import java.nio.file.FileSystems;
import java.util.logging.Logger;
import jssc.SerialPort;
import jssc.SerialPortException;
import processing.serial.Serial;

/**
 * An {@link heronarts.lx.output.LXOutput LXOutput} which {@link #write write}s to a
 * <a href=
 * "http://processing.github.io/processing-javadocs/libraries/processing/serial/Serial.html">
 * {@code processing.serial.Serial}</a> object. Attempts to reconnect port if connection is
 * interrupted.
 *
 * @author <a href="https://dev.laserphile.com/">Derwent McElhinney</a>
 * @see heronarts.lx.output.LXOutput
 * @see <a href=
 *      "http://processing.github.io/processing-javadocs/libraries/processing/serial/Serial.html">
 *      {@code processing.serial.Serial}</a>
 */
abstract class SerialOutput extends LXOutput {
  protected Serial serialPort;
  protected String portName;
  protected int baudRate;
  protected int dataBits;
  protected int stopBits;
  protected int parity = SerialPort.PARITY_NONE;
  protected boolean disconnected = false;
  private static final Logger logger = Logger.getLogger(SerialOutput.class.getName());

  /**
   * Constructs a {@code SerialOutput} which writes to {@code serialPort}
   *
   * @param lx         the {@link heronarts.lx.LX} instance hosting this output
   * @param serialPort an open <a href=
   *     "http://processing.github.io/processing-javadocs/libraries/processing/serial/Serial.html">
   *     {@code processing.serial.Serial}</a> object for this output
   * @param baudRate   is used to reconnect {@link #serialPort serialPort}, see: jssc.SerialPort#setParams
   * @param dataBits   is used to reconnect {@link #serialPort serialPort}, see: jssc.SerialPort#setParams
   * @param stopBits   is used to reconnect {@link #serialPort serialPort}, see: jssc.SerialPort#setParams
   * @param parity     is used to reconnect {@link #serialPort serialPort}, see: jssc.SerialPort#setParams
   * @see              <a href="http://javadox.com/org.scream3r/jssc/2.8.0/javadoc/jssc/SerialPort.html#setParams(int,%20int,%20int,%20int)">{@code jssc.SerialPort#setParams}</a>
   */
  public SerialOutput(final LX lx, final Serial serialPort, final int baudRate, int dataBits, int stopBits, int parity) {
    super(lx, serialPort.port.getPortName());
    this.portName = serialPort.port.getPortName();
    this.serialPort = serialPort;
    this.baudRate = baudRate;
    this.dataBits = dataBits;
    this.stopBits = stopBits;
    this.parity = parity;
  }

  /**
   * Constructs a {@code SerialOutput} which writes to {@code serialPort}
   *
   * @param lx         the {@link heronarts.lx.LX} instance hosting this output
   * @param serialPort an open <a href=
   *     "http://processing.github.io/processing-javadocs/libraries/processing/serial/Serial.html">
   *     {@code processing.serial.Serial}</a> object for this output
   * @param baudRate   is used to reconnect {@link #serialPort serialPort}, see: jssc.SerialPort#setParams
   * @see              <a href="http://javadox.com/org.scream3r/jssc/2.8.0/javadoc/jssc/SerialPort.html#setParams(int,%20int,%20int,%20int)">{@code jssc.SerialPort#setParams}</a>
   */
  public SerialOutput(final LX lx, final Serial serialPort, final int baudRate) {
    this(lx, serialPort, baudRate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
  }


  /**
   * Write {@code message} to the serial port. Attempts to reconnect if connection is interrupted.
   *
   * @param message the message to write to the serial port
   */
  public void write(final byte[] message) {
    if(this.disconnected) {
      if (Files.exists(FileSystems.getDefault().getPath(this.portName))) {
        try {
          if(this.serialPort.port.isOpened()) this.serialPort.port.closePort();
          this.serialPort.port = new SerialPort(this.portName);
          if(!this.serialPort.port.isOpened()) this.serialPort.port.openPort();
          this.serialPort.port.setParams(baudRate, dataBits, stopBits, parity);
          this.disconnected = false;
          logger.warning(String.format("reopened serial port: %s", this.portName));
        } catch (SerialPortException e) {
          logger.warning(String.format("can't reopen serial port: %s", e.toString()));
        }
      }
    }
    if (! this.serialPort.active()) {
      if(! this.disconnected) {
        logger.warning(String.format("port %s is not active!", this.portName));
        this.disconnected = true;
      }
      return;
    }
    if (! this.serialPort.port.isOpened()) {
      if(! this.disconnected) {
        logger.warning(String.format("port %s is not opened!", this.portName));
        this.disconnected = true;
      }
      return;
    }
    if (! Files.exists(FileSystems.getDefault().getPath(this.portName))) {
      if(! this.disconnected) {
        logger.warning(String.format("port %s does not exist!", this.portName));
        this.disconnected = true;
      }
      return;
    }
    this.serialPort.write(message);
  }
}
