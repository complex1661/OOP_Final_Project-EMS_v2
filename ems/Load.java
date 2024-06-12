package ems;
import java.util.*;
import java.io.*;

public abstract class Load{
  public abstract Object loadFileByName(String fileName) throws FileNotFoundException;
}