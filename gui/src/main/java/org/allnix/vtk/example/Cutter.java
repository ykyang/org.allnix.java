package org.allnix.vtk.example;

import vtk.vtkActor;
import vtk.vtkCubeSource;
import vtk.vtkCutter;
import vtk.vtkNamedColors;
import vtk.vtkNativeLibrary;
import vtk.vtkPlane;
import vtk.vtkPolyDataMapper;
import vtk.vtkRenderer;
import vtk.vtkRenderWindow;
import vtk.vtkRenderWindowInteractor;

public class Cutter {
  private static final long serialVersionUID = 1L;

  // Loading Native Libraries.
  // Now it works in eclipse without any issues.
  static {
    if (!vtkNativeLibrary.LoadAllNativeLibraries()) {
      for (vtkNativeLibrary lib : vtkNativeLibrary.values()) {
        if (!lib.IsLoaded()) {
          System.out.println(lib.GetLibraryName() + " not loaded");
        }
      }
    }
    vtkNativeLibrary.DisableOutputWindow(null);
  }

  public static void main(String[] args) {
    vtkNamedColors color = new vtkNamedColors();
    double planeColor[] = new double[4];
    color.GetColor("Yellow", planeColor);
    double cubeColor[] = new double[4];
    color.GetColor("Aquamarine", cubeColor);
    double bgColor[] = new double[4];
    color.GetColor("Silver", bgColor);


    vtkCubeSource cube = new vtkCubeSource();
    cube.SetXLength(40);
    cube.SetYLength(30);
    cube.SetZLength(20);
    vtkPolyDataMapper cubeMapper = new vtkPolyDataMapper();
    cubeMapper.SetInputConnection(cube.GetOutputPort());

    // create a plane to cut,here it cuts in the XZ direction (xz normal=(1,0,0);XY =(0,0,1),YZ =(0,1,0)
    vtkPlane plane = new vtkPlane();
    plane.SetOrigin(10, 0, 0);
    plane.SetNormal(1, 0, 0);

    //create cutter
    vtkCutter cutter = new vtkCutter();
    cutter.SetCutFunction(plane);
    cutter.SetInputData(cubeMapper.GetInput());
    cutter.Update();

    vtkPolyDataMapper cutterMapper = new vtkPolyDataMapper();
    cutterMapper.SetInputConnection(cutter.GetOutputPort());

    //create plane actor
    vtkActor planeActor = new vtkActor();
    planeActor.GetProperty().SetColor(planeColor);
    planeActor.GetProperty().SetLineWidth(2);
    planeActor.SetMapper(cutterMapper);

    //create cube actor
    vtkActor cubeActor = new vtkActor();
    cubeActor.GetProperty().SetColor(cubeColor);
    cubeActor.GetProperty().SetOpacity(0.3);
    cubeActor.SetMapper(cubeMapper);

    //create renderers and add actors of plane and cube
    vtkRenderer ren = new vtkRenderer();
    ren.AddActor(planeActor);
    ren.AddActor(cubeActor);

    //Add renderer to renderwindow and render
    vtkRenderWindow renWin = new vtkRenderWindow();
    renWin.AddRenderer(ren);
    renWin.SetSize(600, 600);

    vtkRenderWindowInteractor iren = new vtkRenderWindowInteractor();
    iren.SetRenderWindow(renWin);
    ren.SetBackground(bgColor);
    renWin.Render();

    iren.Start();
  }
}
