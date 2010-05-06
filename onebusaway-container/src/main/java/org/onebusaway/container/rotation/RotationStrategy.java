package org.onebusaway.container.rotation;

import java.io.IOException;
import java.io.Writer;

public interface RotationStrategy {

  public Writer getFirstWriter() throws IOException;

  public Writer getNextWriter(Writer writer, int charactersWritten) throws IOException;

}