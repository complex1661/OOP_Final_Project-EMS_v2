package ems;
import java.util.*;
import java.io.*;

public abstract class Load{
  public abstract ArrayList loadFilesFrom(String dirName) throws FileNotFoundException;
}