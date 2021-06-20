import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.*;

public class Main {
    private static final double M_PI = 3.1415926535;
    private static final int X_SIZE = 600;
    private static final int Y_SIZE = 600;
    private static Point[][] field = new Point[Y_SIZE][X_SIZE];
    private static ArrayList<Line> lines = new ArrayList<>();

    public static double sqrt(double value) {
        if(value < 0)
            return 0;
        else
            return Math.sqrt(value);
    }

    public static void makeZhuliaMandelbrot(String fractalName) { //рисует фрактал мандельброта или жулиа для примера
        for(double x = -3; x < 3; x+=0.01) {
            for(double y = -3; y < 3; y+=0.01) {
                int YE = (int) ((3+y)*100);
                int XE = (int) ((3+x)*100);
                field[YE][XE] = new Point(XE, YE);
                field[YE][XE].setRGB(255, 255, 255);
                field[YE][XE].setActive(false);
                Complex c = null; Complex z = null;
                if(fractalName.toLowerCase().equals("mandelbrot")) {
                    c = new Complex(x, y);
                    z = new Complex(0, 0);
                }
                else
                if(fractalName.toLowerCase().equals("zhulia")) {
                    z = new Complex(x, y);
                    c = new Complex(0.28, 0.0113);
                }
                int iter = 0;
                while(true) {
                    assert z != null;
                    if (!(z.Length() < 4 && iter!=100)) break;
                    z = z.Multi(z).Sum(c);
                    iter++;
                }
                if (iter == 100) {
                    field[YE][XE].setRGB(0, 0, 0);
                    field[YE][XE].setActive(true);
                    field[YE][XE].setzVertical(iter % 17);
                }

//                else if (iter % 17 == 3)
//                    field[YE][XE].setRGB(230, 230, 250);
//
//                else if (iter % 17 == 2)
//                    field[YE][XE].setRGB(255, 99, 71);
//
//                else if (iter % 17 == 1)
//                    field[YE][XE].setRGB(195, 176, 145);
//
//                else if (iter % 17 == 0)
//                    field[YE][XE].setRGB(255, 255, 255);
//
//                else if (iter % 17 == 16)
//                    field[YE][XE].setRGB(100, 149, 237);
//
//                else if (iter % 17 == 15)
//                    field[YE][XE].setRGB(154, 205, 50);
//
//                else if (iter % 17 == 14)
//                    field[YE][XE].setRGB(245, 222, 179);
//
//                else if (iter % 17 == 13)
//                    field[YE][XE].setRGB(211, 211, 211);
//
//                else if (iter % 17 == 12)
//                    field[YE][XE].setRGB(135, 206, 250);
//
//                else if (iter % 17 == 11)
//                    field[YE][XE].setRGB(46, 139, 87);
//
//                else if (iter % 17 == 10)
//                    field[YE][XE].setRGB(255, 255, 255);

//                else if (iter % 17 == 9)
//                    field[YE][XE].setRGB(48, 230, 200);

//                else if (iter % 17 == 8)
//                    field[YE][XE].setRGB(255, 165, 0);
//
//                else if (iter % 17 == 7)
//                    field[YE][XE].setRGB(128, 0, 128);
//
//                else if (iter % 17 == 6)
//                    field[YE][XE].setRGB(231, 254, 255);
//
//                else if (iter % 17 == 5)
//                    field[YE][XE].setRGB(255, 0, 0);
//
//                else if (iter % 17 == 4)
//                    field[YE][XE].setRGB(173, 216, 230);
//                if (iter != 100)
//                    field[YE][XE].setzVertical(iter % 17);
//                else
//                    field[YE][XE].setzVertical(iter);
            }
        }
    }

    public static void makeSphericalFractal(String fractalName, double x0, double y0, double z0, double w0, double R0, double x1, double y1, double z1, double w1, double R1) { //строит фрактальное изображение (развернутое)
        for(int x = 0; x < X_SIZE; x++) {
            for(int y = 0; y < X_SIZE; y++) {
                field[y][x] = new Point(x, y);
            }
        }
        int iter = 0;
        double b, csqr, a, Cw, C, w, zz;
        double RforNova = 0, R0forNova = 0, R1forNova = 0;
        double d = sqrt((x1-x0)*(x1-x0) + (y1-y0)*(y1-y0) + (z1-z0)*(z1-z0) + (w1-w0)*(w1-w0)+(R1forNova-R0forNova)*(R1forNova-R0forNova));
        double X = (d*d + R0*R0 - R1*R1)/ (2.f*d);
        double r = sqrt(R0*R0-X*X);
        double yemax = (double)Y_SIZE / 100 - 3;
        double xemax = (double)X_SIZE / 100 - 3;
        for(double ye = -3; ye < yemax; ye += 0.01) {
            for(double xe = -3; xe < xemax; xe += 0.01) {
                double Fi = 2 * M_PI * xe / 5.f;
                double teta = M_PI * ye / 5.f;
                double x = -r * cos(teta) * cos(Fi); // как учесть смещение центров гиперсфер?
                double y = r * cos(teta) * sin(Fi);
                Complex c = new Complex(x, y);
                if(z1!=z0) {
                    double Cxyz = (R1 * R1 - R0 * R0 - (x - x1) * (x - x1) - (y - y1) * (y - y1) - (RforNova - R1forNova) * (RforNova - R1forNova) + (x - x0) * (x - x0) + (y - y0) * (y - y0) + (RforNova - R0forNova) * (RforNova - R0forNova)) / 2.f / (z0 - z1) + (z0 + z1)/2.f;
                    double Czw = (w0 * w0 - w1 * w1) /( 2.f * (z0 - z1));
                    Cw = (w1 - w0) / (z0 - z1);
                    C = Cxyz + Czw;
                    double Ckon = C - z0;
                    b = 2 * Ckon * Cw - 2 * w0;
                    a = (Cw * Cw + 1);
                    csqr = R0 * R0 - (x - x0) * (x - x0) - (y - y0) * (y - y0) - Ckon * Ckon - w0 * w0 - (RforNova - R0forNova) * (RforNova - R0forNova);
                    double a1 = b * b + 4 * a * csqr;
                    if(ye >=0)
                        w = (-b + sqrt(a1)) / 2.f / a; // полушарие со сложением дискриминанта
                    else
                        w = (-b - sqrt(a1)) / 2.f / a; // полушарие с вычитанием дискриминанта
                    zz = C + w * Cw;
                }
                else {
                    double Cxy = R1*R1 - R0*R0 - (x-x1)*(x-x1) - (y-y1)*(y-y1) + (x-x0)*(x-x0) + (y-y0)*(y-y0) - (RforNova - R1forNova) * (RforNova - R1forNova) + (RforNova - R0forNova) * (RforNova - R0forNova);
                    w = (Cxy/(w0-w1)+w0+w1)/2;
                    if(ye >= 0)
                        zz = sqrt(R0*R0-(x-x0)*(x-x0) - (y-y0)*(y-y0) - (w-w0)*(w-w0) - (RforNova - R0forNova) * (RforNova - R0forNova))+z0;
                    else
                        zz = -sqrt(R0*R0-(x-x0)*(x-x0) - (y-y0)*(y-y0) - (w-w0)*(w-w0) - (RforNova - R0forNova) * (RforNova - R0forNova))+z0;
                }
                Complex z = new Complex(zz, w);

                iter = 0;
                if(fractalName.toLowerCase().equals("nova")){
                    //заглушка
                }
                else if (fractalName.toLowerCase().equals("mandelbrot")){
                    while (z.Length() < 4 && iter != 100) {
                        z = z.Multi(z).Sum(c);
                        iter++;
                    }
                }
                int YE = (int) ((3+ye)*100);
                int XE = (int) ((3+xe)*100);
                if (iter == 100)
                    field[YE][XE].setRGB(0, 0, 0);
                else if (iter % 17 == 3)
                    field[YE][XE].setRGB(230, 230, 250);

                else if (iter % 17 == 2)
                    field[YE][XE].setRGB(255, 99, 71);

                else if (iter % 17 == 1)
                    field[YE][XE].setRGB(195, 176, 145);

                else if (iter % 17 == 0)
                    field[YE][XE].setRGB(255, 255, 255);

                else if (iter % 17 == 16)
                    field[YE][XE].setRGB(100, 149, 237);

                else if (iter % 17 == 15)
                    field[YE][XE].setRGB(154, 205, 50);

                else if (iter % 17 == 14)
                    field[YE][XE].setRGB(245, 222, 179);

                else if (iter % 17 == 13)
                    field[YE][XE].setRGB(211, 211, 211);

                else if (iter % 17 == 12)
                    field[YE][XE].setRGB(135, 206, 250);

                else if (iter % 17 == 11)
                    field[YE][XE].setRGB(46, 139, 87);

                else if (iter % 17 == 10)
                    field[YE][XE].setRGB(255, 255, 255);

                else if (iter % 17 == 9)
                    field[YE][XE].setRGB(48, 230, 200);

                else if (iter % 17 == 8)
                    field[YE][XE].setRGB(255, 165, 0);

                else if (iter % 17 == 7)
                    field[YE][XE].setRGB(128, 0, 128);

                else if (iter % 17 == 6)
                    field[YE][XE].setRGB(231, 254, 255);

                else if (iter % 17 == 5)
                    field[YE][XE].setRGB(255, 0, 0);

                else if (iter % 17 == 4)
                    field[YE][XE].setRGB(173, 216, 230);
                if (iter != 100)
                    field[YE][XE].setzVertical(iter % 17);
                else
                    field[YE][XE].setzVertical(iter);
            }
        }
    }

    public static void setAmOfNearestPoints() { //находит ближайшие одинаковые по цвету точки и их количество, для всех точек
        for(int i = 0; i < Y_SIZE; i++) {
            for(int j = 0; j < X_SIZE; j++) {
                if(j == 0) { //левый столбец
                    if(i == 0) { //верхняя ячейка
                        if (field[i][j].getR() == field[i][j+1].getR() && field[i][j].getG() == field[i][j+1].getG() && field[i][j].getB() == field[i][j+1].getB()) {
                            field[i][j].increaseNearAmount();
                            field[i][j].addNearestPoint(j+1, i);
                        }
                        if (field[i][j].getR() == field[i+1][j].getR() && field[i][j].getG() == field[i+1][j].getG() && field[i][j].getB() == field[i+1][j].getB()) {
                            field[i][j].increaseNearAmount();
                            field[i][j].addNearestPoint(j, i+1);
                        }
                        if (field[i][j].getR() == field[i+1][j+1].getR() && field[i][j].getG() == field[i+1][j+1].getG() && field[i][j].getB() == field[i+1][j+1].getB()) {
                            field[i][j].increaseNearAmount();
                            field[i][j].addNearestPoint(j+1, i+1);
                        }
                    }
                    else if (i == Y_SIZE-1) { //нижняя ячейка
                        if (field[i][j].getR() == field[i][j+1].getR() && field[i][j].getG() == field[i][j+1].getG() && field[i][j].getB() == field[i][j+1].getB()) {
                            field[i][j].increaseNearAmount();
                            field[i][j].addNearestPoint(j+1, i);
                        }
                        if (field[i][j].getR() == field[i-1][j].getR() && field[i][j].getG() == field[i-1][j].getG() && field[i][j].getB() == field[i-1][j].getB()) {
                            field[i][j].increaseNearAmount();
                            field[i][j].addNearestPoint(j, i-1);
                        }
                        if (field[i][j].getR() == field[i-1][j+1].getR() && field[i][j].getG() == field[i-1][j+1].getG() && field[i][j].getB() == field[i-1][j+1].getB()) {
                            field[i][j].increaseNearAmount();
                            field[i][j].addNearestPoint(j+1, i-1);
                        }
                    }
                    else { //остальные ячейки
                        if (field[i][j].getR() == field[i][j+1].getR() && field[i][j].getG() == field[i][j+1].getG() && field[i][j].getB() == field[i][j+1].getB()) {
                            field[i][j].increaseNearAmount();
                            field[i][j].addNearestPoint(j, i+1);
                        }
                        if (field[i][j].getR() == field[i-1][j].getR() && field[i][j].getG() == field[i-1][j].getG() && field[i][j].getB() == field[i-1][j].getB()) {
                            field[i][j].increaseNearAmount();
                            field[i][j].addNearestPoint(j, i-1);
                        }
                        if (field[i][j].getR() == field[i-1][j+1].getR() && field[i][j].getG() == field[i-1][j+1].getG() && field[i][j].getB() == field[i-1][j+1].getB()) {
                            field[i][j].increaseNearAmount();
                            field[i][j].addNearestPoint(j+1, i-1);
                        }
                        if (field[i][j].getR() == field[i+1][j].getR() && field[i][j].getG() == field[i+1][j].getG() && field[i][j].getB() == field[i+1][j].getB()) {
                            field[i][j].increaseNearAmount();
                            field[i][j].addNearestPoint(j, i+1);
                        }
                        if (field[i][j].getR() == field[i+1][j+1].getR() && field[i][j].getG() == field[i+1][j+1].getG() && field[i][j].getB() == field[i+1][j+1].getB()) {
                            field[i][j].increaseNearAmount();
                            field[i][j].addNearestPoint(j+1, i+1);
                        }
                    }
                }
                else if(j == X_SIZE-1) { //правый столбец
                    if(i == 0) { //верхняя ячейка
                        if (field[i][j].getR() == field[i][j-1].getR() && field[i][j].getG() == field[i][j-1].getG() && field[i][j].getB() == field[i][j-1].getB()) {
                            field[i][j].increaseNearAmount();
                            field[i][j].addNearestPoint(j-1, i);
                        }
                        if (field[i][j].getR() == field[i+1][j].getR() && field[i][j].getG() == field[i+1][j].getG() && field[i][j].getB() == field[i+1][j].getB()) {
                            field[i][j].increaseNearAmount();
                            field[i][j].addNearestPoint(j, i+1);
                        }
                        if (field[i][j].getR() == field[i+1][j-1].getR() && field[i][j].getG() == field[i+1][j-1].getG() && field[i][j].getB() == field[i+1][j-1].getB()) {
                            field[i][j].increaseNearAmount();
                            field[i][j].addNearestPoint(j-1, i+1);
                        }
                    }
                    else if (i == Y_SIZE-1) { //нижняя ячейка
                        if (field[i][j].getR() == field[i][j-1].getR() && field[i][j].getG() == field[i][j-1].getG() && field[i][j].getB() == field[i][j-1].getB()) {
                            field[i][j].increaseNearAmount();
                            field[i][j].addNearestPoint(j-1, i);
                        }
                        if (field[i][j].getR() == field[i-1][j].getR() && field[i][j].getG() == field[i-1][j].getG() && field[i][j].getB() == field[i-1][j].getB()) {
                            field[i][j].increaseNearAmount();
                            field[i][j].addNearestPoint(j, i-1);
                        }
                        if (field[i][j].getR() == field[i-1][j-1].getR() && field[i][j].getG() == field[i-1][j-1].getG() && field[i][j].getB() == field[i-1][j-1].getB()) {
                            field[i][j].increaseNearAmount();
                            field[i][j].addNearestPoint(j-1, i-1);
                        }
                    }
                    else { //остальные ячейки
                        if (field[i][j].getR() == field[i][j-1].getR() && field[i][j].getG() == field[i][j-1].getG() && field[i][j].getB() == field[i][j-1].getB()) {
                            field[i][j].increaseNearAmount();
                            field[i][j].addNearestPoint(j-1, i);
                        }
                        if (field[i][j].getR() == field[i-1][j].getR() && field[i][j].getG() == field[i-1][j].getG() && field[i][j].getB() == field[i-1][j].getB()) {
                            field[i][j].increaseNearAmount();
                            field[i][j].addNearestPoint(j, i-1);
                        }
                        if (field[i][j].getR() == field[i-1][j-1].getR() && field[i][j].getG() == field[i-1][j-1].getG() && field[i][j].getB() == field[i-1][j-1].getB()) {
                            field[i][j].increaseNearAmount();
                            field[i][j].addNearestPoint(j-1, i-1);
                        }
                        if (field[i][j].getR() == field[i+1][j].getR() && field[i][j].getG() == field[i+1][j].getG() && field[i][j].getB() == field[i+1][j].getB()) {
                            field[i][j].increaseNearAmount();
                            field[i][j].addNearestPoint(j, i+1);
                        }
                        if (field[i][j].getR() == field[i+1][j-1].getR() && field[i][j].getG() == field[i+1][j-1].getG() && field[i][j].getB() == field[i+1][j-1].getB()) {
                            field[i][j].increaseNearAmount();
                            field[i][j].addNearestPoint(j-1, i+1);
                        }
                    }
                }
                else { //любой столбец
                    if(i == 0) { //верхняя ячейка
                        if (field[i][j].getR() == field[i][j+1].getR() && field[i][j].getG() == field[i][j+1].getG() && field[i][j].getB() == field[i][j+1].getB()) {
                            field[i][j].increaseNearAmount();
                            field[i][j].addNearestPoint(j+1, i);
                        }
                        if (field[i][j].getR() == field[i+1][j].getR() && field[i][j].getG() == field[i+1][j].getG() && field[i][j].getB() == field[i+1][j].getB()) {
                            field[i][j].increaseNearAmount();
                            field[i][j].addNearestPoint(j, i+1);
                        }
                        if (field[i][j].getR() == field[i+1][j+1].getR() && field[i][j].getG() == field[i+1][j+1].getG() && field[i][j].getB() == field[i+1][j+1].getB()) {
                            field[i][j].increaseNearAmount();
                            field[i][j].addNearestPoint(j+1, i+1);
                        }
                        if (field[i][j].getR() == field[i+1][j-1].getR() && field[i][j].getG() == field[i+1][j-1].getG() && field[i][j].getB() == field[i+1][j-1].getB()) {
                            field[i][j].increaseNearAmount();
                            field[i][j].addNearestPoint(j-1, i+1);
                        }
                        if (field[i][j].getR() == field[i][j-1].getR() && field[i][j].getG() == field[i][j-1].getG() && field[i][j].getB() == field[i][j-1].getB()) {
                            field[i][j].increaseNearAmount();
                            field[i][j].addNearestPoint(j-1, i);
                        }
                    }
                    else if (i == Y_SIZE-1) { //нижняя ячейка
                        if (field[i][j].getR() == field[i][j+1].getR() && field[i][j].getG() == field[i][j+1].getG() && field[i][j].getB() == field[i][j+1].getB()) {
                            field[i][j].increaseNearAmount();
                            field[i][j].addNearestPoint(j+1, i);
                        }
                        if (field[i][j].getR() == field[i-1][j].getR() && field[i][j].getG() == field[i-1][j].getG() && field[i][j].getB() == field[i-1][j].getB()) {
                            field[i][j].increaseNearAmount();
                            field[i][j].addNearestPoint(j, i-1);
                        }
                        if (field[i][j].getR() == field[i-1][j+1].getR() && field[i][j].getG() == field[i-1][j+1].getG() && field[i][j].getB() == field[i-1][j+1].getB()) {
                            field[i][j].increaseNearAmount();
                            field[i][j].addNearestPoint(j+1, i-1);
                        }
                        if (field[i][j].getR() == field[i-1][j-1].getR() && field[i][j].getG() == field[i-1][j-1].getG() && field[i][j].getB() == field[i-1][j-1].getB()) {
                            field[i][j].increaseNearAmount();
                            field[i][j].addNearestPoint(j-1, i-1);
                        }
                        if (field[i][j].getR() == field[i][j-1].getR() && field[i][j].getG() == field[i][j-1].getG() && field[i][j].getB() == field[i][j-1].getB()) {
                            field[i][j].increaseNearAmount();
                            field[i][j].addNearestPoint(j-1, i);
                        }
                    }
                    else { //остальные ячейки
                        if (field[i][j].getR() == field[i][j+1].getR() && field[i][j].getG() == field[i][j+1].getG() && field[i][j].getB() == field[i][j+1].getB()) {
                            field[i][j].increaseNearAmount();
                            field[i][j].addNearestPoint(j+1, i);
                        }
                        if (field[i][j].getR() == field[i-1][j].getR() && field[i][j].getG() == field[i-1][j].getG() && field[i][j].getB() == field[i-1][j].getB()) {
                            field[i][j].increaseNearAmount();
                            field[i][j].addNearestPoint(j, i-1);
                        }
                        if (field[i][j].getR() == field[i-1][j+1].getR() && field[i][j].getG() == field[i-1][j+1].getG() && field[i][j].getB() == field[i-1][j+1].getB()) {
                            field[i][j].increaseNearAmount();
                            field[i][j].addNearestPoint(j+1, i-1);
                        }
                        if (field[i][j].getR() == field[i+1][j].getR() && field[i][j].getG() == field[i+1][j].getG() && field[i][j].getB() == field[i+1][j].getB()) {
                            field[i][j].increaseNearAmount();
                            field[i][j].addNearestPoint(j, i+1);
                        }
                        if (field[i][j].getR() == field[i+1][j+1].getR() && field[i][j].getG() == field[i+1][j+1].getG() && field[i][j].getB() == field[i+1][j+1].getB()) {
                            field[i][j].increaseNearAmount();
                            field[i][j].addNearestPoint(j+1, i+1);
                        }
                        if (field[i][j].getR() == field[i+1][j-1].getR() && field[i][j].getG() == field[i+1][j-1].getG() && field[i][j].getB() == field[i+1][j-1].getB()) {
                            field[i][j].increaseNearAmount();
                            field[i][j].addNearestPoint(j-1, i+1);
                        }
                        if (field[i][j].getR() == field[i-1][j-1].getR() && field[i][j].getG() == field[i-1][j-1].getG() && field[i][j].getB() == field[i-1][j-1].getB()) {
                            field[i][j].increaseNearAmount();
                            field[i][j].addNearestPoint(j-1, i-1);
                        }
                        if (field[i][j].getR() == field[i][j-1].getR() && field[i][j].getG() == field[i][j-1].getG() && field[i][j].getB() == field[i][j-1].getB()) {
                            field[i][j].increaseNearAmount();
                            field[i][j].addNearestPoint(j-1, i);
                        }
                    }
                }
            }
        }
    }

    public static void setAmOfWhiteNearestPoints() { //находит ближайшие одинаковые по цвету точки и их количество, для всех точек
        double diagonal_weight = 0.5;
        for(int i = 0; i < Y_SIZE; i++) {
            for(int j = 0; j < X_SIZE; j++) {
                if(j == 0) { //левый столбец
                    if(i == 0) { //верхняя ячейка
                        if (255 == field[i][j+1].getR() && 255 == field[i][j+1].getG() && 255 == field[i][j+1].getB()) {
                            field[i][j].increaseBorderNearAmount();
                        }
                        if (255 == field[i+1][j].getR() && 255 == field[i+1][j].getG() && 255 == field[i+1][j].getB()) {
                            field[i][j].increaseBorderNearAmount();
                        }
                        if (255 == field[i+1][j+1].getR() && 255 == field[i+1][j+1].getG() && 255 == field[i+1][j+1].getB()) {
                            field[i][j].increaseBorderNearAmount(diagonal_weight);
                        }
                    }
                    else if (i == Y_SIZE-1) { //нижняя ячейка
                        if (255 == field[i][j+1].getR() && 255 == field[i][j+1].getG() && 255 == field[i][j+1].getB()) {
                            field[i][j].increaseBorderNearAmount();
                        }
                        if (255 == field[i-1][j].getR() && 255 == field[i-1][j].getG() && 255 == field[i-1][j].getB()) {
                            field[i][j].increaseBorderNearAmount();
                        }
                        if (255 == field[i-1][j+1].getR() && 255 == field[i-1][j+1].getG() && 255 == field[i-1][j+1].getB()) {
                            field[i][j].increaseBorderNearAmount(diagonal_weight);
                        }
                    }
                    else { //остальные ячейки
                        if (255 == field[i][j+1].getR() && 255 == field[i][j+1].getG() && 255 == field[i][j+1].getB()) {
                            field[i][j].increaseBorderNearAmount();
                        }
                        if (255 == field[i-1][j].getR() && 255 == field[i-1][j].getG() && 255 == field[i-1][j].getB()) {
                            field[i][j].increaseBorderNearAmount();
                        }
                        if (255 == field[i-1][j+1].getR() && 255 == field[i-1][j+1].getG() && 255 == field[i-1][j+1].getB()) {
                            field[i][j].increaseBorderNearAmount(diagonal_weight);
                        }
                        if (255 == field[i+1][j].getR() && 255 == field[i+1][j].getG() && 255 == field[i+1][j].getB()) {
                            field[i][j].increaseBorderNearAmount();
                        }
                        if (255 == field[i+1][j+1].getR() && 255 == field[i+1][j+1].getG() && 255 == field[i+1][j+1].getB()) {
                            field[i][j].increaseBorderNearAmount(diagonal_weight);
                        }
                    }
                }
                else if(j == X_SIZE-1) { //правый столбец
                    if(i == 0) { //верхняя ячейка
                        if (255 == field[i][j-1].getR() && 255 == field[i][j-1].getG() && 255 == field[i][j-1].getB()) {
                            field[i][j].increaseBorderNearAmount();
                        }
                        if (255 == field[i+1][j].getR() && 255 == field[i+1][j].getG() && 255 == field[i+1][j].getB()) {
                            field[i][j].increaseBorderNearAmount();
                        }
                        if (255 == field[i+1][j-1].getR() && 255 == field[i+1][j-1].getG() && 255 == field[i+1][j-1].getB()) {
                            field[i][j].increaseBorderNearAmount(diagonal_weight);
                        }
                    }
                    else if (i == Y_SIZE-1) { //нижняя ячейка
                        if (255 == field[i][j-1].getR() && 255 == field[i][j-1].getG() && 255 == field[i][j-1].getB()) {
                            field[i][j].increaseBorderNearAmount();
                        }
                        if (255 == field[i-1][j].getR() && 255 == field[i-1][j].getG() && 255 == field[i-1][j].getB()) {
                            field[i][j].increaseBorderNearAmount();
                        }
                        if (255 == field[i-1][j-1].getR() && 255 == field[i-1][j-1].getG() && 255 == field[i-1][j-1].getB()) {
                            field[i][j].increaseBorderNearAmount(diagonal_weight);
                        }
                    }
                    else { //остальные ячейки
                        if (255 == field[i][j-1].getR() && 255 == field[i][j-1].getG() && 255 == field[i][j-1].getB()) {
                            field[i][j].increaseBorderNearAmount();
                        }
                        if (255 == field[i-1][j].getR() && 255 == field[i-1][j].getG() && 255 == field[i-1][j].getB()) {
                            field[i][j].increaseBorderNearAmount();
                        }
                        if (255 == field[i-1][j-1].getR() && 255 == field[i-1][j-1].getG() && 255 == field[i-1][j-1].getB()) {
                            field[i][j].increaseBorderNearAmount(diagonal_weight);
                        }
                        if (255 == field[i+1][j].getR() && 255 == field[i+1][j].getG() && 255 == field[i+1][j].getB()) {
                            field[i][j].increaseBorderNearAmount();
                        }
                        if (255 == field[i+1][j-1].getR() && 255 == field[i+1][j-1].getG() && 255 == field[i+1][j-1].getB()) {
                            field[i][j].increaseBorderNearAmount(diagonal_weight);
                        }
                    }
                }
                else { //любой столбец
                    if(i == 0) { //верхняя ячейка
                        if (255 == field[i][j+1].getR() && 255 == field[i][j+1].getG() && 255 == field[i][j+1].getB()) {
                            field[i][j].increaseBorderNearAmount();
                        }
                        if (255 == field[i+1][j].getR() && 255 == field[i+1][j].getG() && 255 == field[i+1][j].getB()) {
                            field[i][j].increaseBorderNearAmount();
                        }
                        if (255 == field[i+1][j+1].getR() && 255 == field[i+1][j+1].getG() && 255 == field[i+1][j+1].getB()) {
                            field[i][j].increaseBorderNearAmount(diagonal_weight);
                        }
                        if (255 == field[i+1][j-1].getR() && 255 == field[i+1][j-1].getG() && 255 == field[i+1][j-1].getB()) {
                            field[i][j].increaseBorderNearAmount(diagonal_weight);
                        }
                        if (255 == field[i][j-1].getR() && 255 == field[i][j-1].getG() && 255 == field[i][j-1].getB()) {
                            field[i][j].increaseBorderNearAmount();
                        }
                    }
                    else if (i == Y_SIZE-1) { //нижняя ячейка
                        if (255 == field[i][j+1].getR() && 255 == field[i][j+1].getG() && 255 == field[i][j+1].getB()) {
                            field[i][j].increaseBorderNearAmount();
                        }
                        if (255 == field[i-1][j].getR() && 255 == field[i-1][j].getG() && 255 == field[i-1][j].getB()) {
                            field[i][j].increaseBorderNearAmount();
                        }
                        if (255 == field[i-1][j+1].getR() && 255 == field[i-1][j+1].getG() && 255 == field[i-1][j+1].getB()) {
                            field[i][j].increaseBorderNearAmount(diagonal_weight);
                        }
                        if (255 == field[i-1][j-1].getR() && 255 == field[i-1][j-1].getG() && 255 == field[i-1][j-1].getB()) {
                            field[i][j].increaseBorderNearAmount(diagonal_weight);
                        }
                        if (255 == field[i][j-1].getR() && 255 == field[i][j-1].getG() && 255 == field[i][j-1].getB()) {
                            field[i][j].increaseBorderNearAmount();
                        }
                    }
                    else { //остальные ячейки
                        if (255 == field[i][j+1].getR() && 255 == field[i][j+1].getG() && 255 == field[i][j+1].getB()) {
                            field[i][j].increaseBorderNearAmount();
                        }
                        if (255 == field[i-1][j].getR() && 255 == field[i-1][j].getG() && 255 == field[i-1][j].getB()) {
                            field[i][j].increaseBorderNearAmount();
                        }
                        if (255 == field[i-1][j+1].getR() && 255 == field[i-1][j+1].getG() && 255 == field[i-1][j+1].getB()) {
                            field[i][j].increaseBorderNearAmount(diagonal_weight);
                        }
                        if (255 == field[i+1][j].getR() && 255 == field[i+1][j].getG() && 255 == field[i+1][j].getB()) {
                            field[i][j].increaseBorderNearAmount();
                        }
                        if (255 == field[i+1][j+1].getR() && 255 == field[i+1][j+1].getG() && 255 == field[i+1][j+1].getB()) {
                            field[i][j].increaseBorderNearAmount(diagonal_weight);
                        }
                        if (255 == field[i+1][j-1].getR() && 255 == field[i+1][j-1].getG() && 255 == field[i+1][j-1].getB()) {
                            field[i][j].increaseBorderNearAmount(diagonal_weight);
                        }
                        if (255 == field[i-1][j-1].getR() && 255 == field[i-1][j-1].getG() && 255 == field[i-1][j-1].getB()) {
                            field[i][j].increaseBorderNearAmount(diagonal_weight);
                        }
                        if (255 == field[i][j-1].getR() && 255 == field[i][j-1].getG() && 255 == field[i][j-1].getB()) {
                            field[i][j].increaseBorderNearAmount();
                        }
                    }
                }
            }
        }
    }
    public static void deleteNoisePoints() { //как вариант

    }

    public static boolean linesAreExisted() { //если не все линии проверены
        for (int i = 0; i < Y_SIZE; i++) {
            for (int j = 0; j < X_SIZE; j++) {
                if (field[i][j].isActive())
                    return true;
            }
        }
        return true;
    }

    public static int[] getLessNearestAmPixel(int x, int y, int prev_x_dif, int prev_y_dif) { //получить координаты пикселя с наименьшим количеством ближайших пикселей (проход по границе)
        double minAmount = 10, xmin = 0, ymin = 0, max_scalar = -9999999;
        ArrayList<int[]> nearestPointsCoordinates = field[y][x].getNearestPointsCoordinates();
        for(int[] coords: nearestPointsCoordinates) {
            int xe = coords[0];
            int ye = coords[1];
            int current_scalar = (xe-x)*prev_x_dif + (y-ye)*prev_y_dif;
            if(field[ye][xe].getNearAmount() < minAmount && field[ye][xe].isActive() && field[ye][xe].getNearAmount() <= 7 && field[y][x].getNearAmount() >= 3 && current_scalar >= max_scalar) {
                max_scalar = current_scalar;
                minAmount = field[ye][xe].getNearAmount();
                xmin = xe;
                ymin = ye;
            }
        }
        if(minAmount==10)
            return new int[] {X_SIZE+1, Y_SIZE+1};
        else
            return new int[] {(int)xmin, (int)ymin};
    }




    public static void buildLines() { //нахождение ломаных, построение линий
        if(linesAreExisted()) {
            Line line = new Line();
            ArrayList<Vector> vectors = new ArrayList<>();
            for (int y = 0; y < Y_SIZE; y++) {
                for (int x = 0; x < X_SIZE; x++) {
                    if(field[y][x].isActive() && field[y][x].getNearAmount() <= 7 && field[y][x].getNearAmount() >= 3) {
                        int prev_x_dif = 0, prev_y_dif = 0, x_dif=0, y_dif=0;
                        Point current_point = field[y][x];
                        Vector vector = new Vector();
                        vector.setStart_point(current_point);
                        int[] c = getLessNearestAmPixel(x, y, 0, 0);
                        if(c[0] == X_SIZE+1 && c[1] == Y_SIZE+1) {
                            current_point.setActive(false);
                            vector.setEnd_point(current_point);
                            vectors.add(vector);
                            continue;
                        }
                        current_point.setActive(false);
                        current_point = field[c[1]][c[0]];
                        prev_x_dif = current_point.getXE() - x;
                        prev_y_dif = current_point.getYE() - y;
                        Point prev_point = field[y][x];
                        while((current_point.getXE()!=x || current_point.getYE() != y) && ((current_point.getXE()!=0 && current_point.getYE() != 0 && current_point.getXE()!=X_SIZE-1 && current_point.getYE() != Y_SIZE-1))) { // || current_point.getNearAmount() != 0)
                            if(current_point.isActive()) {
                                x_dif = current_point.getXE() - prev_point.getXE();
                                y_dif = current_point.getYE() - prev_point.getYE();
                                if(x_dif!=prev_x_dif || y_dif != prev_y_dif) {
                                    vector.setEnd_point(prev_point);
                                    vectors.add(vector);
                                    vector = new Vector();
                                    vector.setStart_point(prev_point);
                                    prev_x_dif = x_dif;
                                    prev_y_dif = y_dif;
                                }
                                current_point.setActive(false);
                                prev_point = current_point;
                                int[] coords;
//                                if((current_point.getXE() == 0 || current_point.getXE() == X_SIZE-1) && current_point.getNearAmount() == 0) {
//                                    if(!current_point.isActive())
//                                        break;
//                                    coords = new int[]{X_SIZE - 1 - current_point.getXE(), 600 - current_point.getYE()};
//                                }
//                                else
                                coords = getLessNearestAmPixel(current_point.getXE(), current_point.getYE(), x_dif, y_dif); //needtochange на что?
                                int xe = coords[0];
                                int ye = coords[1];
                                if(xe == X_SIZE+1 && ye == Y_SIZE+1) { //если следующая точка - последняя
                                    if(current_point.getYE()+1 == y && current_point.getXE() == x || current_point.getYE()+1 == y && current_point.getXE()+1 == x //если последняя точка в пути является начальной точкой линии
                                    || current_point.getYE()+1 == y && current_point.getXE()-1 == x || current_point.getYE() == y && current_point.getXE()+1 == x
                                    || current_point.getYE() == y && current_point.getXE()-1 == x || current_point.getYE()-1 == y && current_point.getXE() == x
                                    || current_point.getYE()-1 == y && current_point.getXE()+1 == x || current_point.getYE()-1 == y && current_point.getXE()-1 == x) {
                                        vector.setEnd_point(field[y][x]);
                                        current_point.setActive(false);
                                        vectors.add(vector);
                                    }
                                    else {
                                        vector.setEnd_point(current_point);
                                        current_point.setActive(false);
                                        vectors.add(vector);
                                    }
                                    break;
                                }
                                current_point = field[ye][xe];
                            }
                        }
                        if(vectors.size()>=10) {
                            line.setVectors(vectors);
                            vectors = new ArrayList<>();
                            lines.add(line);
                            line = new Line();
                        }
                    }
                }
            }
        }
    }

//    public static int[] getLessPixelByWhite(int x, int y) {
//        double minAmount = 10, maxBorderAmount = -1;
//        int xmin=0, ymin=0;
//        ArrayList<int[]> nearestPointsCoordinates = field[y][x].getNearestPointsCoordinates();
//        for(int[] coords: nearestPointsCoordinates) {
//            int xe = coords[0];
//            int ye = coords[1];
//            if(field[ye][xe].getNearAmount() < minAmount && field[ye][xe].getBorderNearAmount() > maxBorderAmount && field[ye][xe].isActive() && field[ye][xe].getNearAmount() <= 7 && field[y][x].getNearAmount() >= 3 && field[y][x].getBorderNearAmount() <= 7 && field[y][x].getBorderNearAmount() >= 1) {
//                if(field[ye][xe].getNearAmount() < minAmount)
//                    minAmount = field[ye][xe].getNearAmount();
//                if(maxBorderAmount < field[ye][xe].getBorderNearAmount())
//                    maxBorderAmount = field[ye][xe].getBorderNearAmount();
//                xmin = xe;
//                ymin = ye;
//            }
//        }
//        if(minAmount==10 && maxBorderAmount==-1)
//            return new int[] {X_SIZE+1, Y_SIZE+1};
//        else
//            return new int[] {xmin, ymin};
//    }

    public static int[] getLessPixelByWhite(int x, int y) {
        double maxBorderAmount = -1;
        int xmin=0, ymin=0;
        ArrayList<int[]> nearestPointsCoordinates = field[y][x].getNearestPointsCoordinates();
        for(int[] coords: nearestPointsCoordinates) {
            int xe = coords[0];
            int ye = coords[1];
            if(field[ye][xe].getBorderNearAmount() > maxBorderAmount && field[ye][xe].isActive() && field[y][x].getBorderNearAmount() <= 4.5 && field[y][x].getBorderNearAmount() >= 0.5) {
                maxBorderAmount = field[ye][xe].getBorderNearAmount();
                xmin = xe;
                ymin = ye;
            }
        }
        if(maxBorderAmount==-1)
            return new int[] {X_SIZE+1, Y_SIZE+1};
        else
            return new int[] {xmin, ymin};
    }
    public static void buildLinesByWhite() { //нахождение ломаных, построение линий
        if(linesAreExisted()) {
            Line line = new Line();
            ArrayList<Vector> vectors = new ArrayList<>();
            for (int y = 0; y < Y_SIZE; y++) {
                for (int x = 0; x < X_SIZE; x++) {
                    if(field[y][x].isActive() && field[y][x].getBorderNearAmount() <= 4.5 && field[y][x].getBorderNearAmount() >= 0.5) {
                        int prev_x_dif = 0, prev_y_dif = 0, x_dif=0, y_dif=0;
                        Point current_point = field[y][x];
                        Vector vector = new Vector();
                        vector.setStart_point(current_point);
                        int[] c = getLessPixelByWhite(x, y);
                        if(c[0] == X_SIZE+1 && c[1] == Y_SIZE+1) {
                            current_point.setActive(false);
                            vector.setEnd_point(current_point);
                            vectors.add(vector);
                            continue;
                        }
                        current_point.setActive(false);
                        current_point = field[c[1]][c[0]];
                        prev_x_dif = current_point.getXE() - x;
                        prev_y_dif = current_point.getYE() - y;
                        Point prev_point = field[y][x];
                        while((current_point.getXE()!=x || current_point.getYE() != y) && ((current_point.getXE()!=0 && current_point.getYE() != 0 && current_point.getXE()!=X_SIZE-1 && current_point.getYE() != Y_SIZE-1))) { // || current_point.getNearAmount() != 0)
                            if(current_point.isActive()) {
                                x_dif = current_point.getXE() - prev_point.getXE();
                                y_dif = current_point.getYE() - prev_point.getYE();
                                if(x_dif!=prev_x_dif || y_dif != prev_y_dif) {
                                    vector.setEnd_point(prev_point);
                                    vectors.add(vector);
                                    vector = new Vector();
                                    vector.setStart_point(prev_point);
                                    prev_x_dif = x_dif;
                                    prev_y_dif = y_dif;
                                }
                                current_point.setActive(false);
                                prev_point = current_point;
                                int[] coords;
//                                if((current_point.getXE() == 0 || current_point.getXE() == X_SIZE-1) && current_point.getNearAmount() == 0) {
//                                    if(!current_point.isActive())
//                                        break;
//                                    coords = new int[]{X_SIZE - 1 - current_point.getXE(), 600 - current_point.getYE()};
//                                }
//                                else
                                coords = getLessPixelByWhite(current_point.getXE(), current_point.getYE()); //needtochange
                                int xe = coords[0];
                                int ye = coords[1];
                                if(xe == X_SIZE+1 && ye == Y_SIZE+1) { //если следующая точка - последняя
                                    if(current_point.getYE()+1 == y && current_point.getXE() == x || current_point.getYE()+1 == y && current_point.getXE()+1 == x //если последняя точка в пути является начальной точкой линии
                                            || current_point.getYE()+1 == y && current_point.getXE()-1 == x || current_point.getYE() == y && current_point.getXE()+1 == x
                                            || current_point.getYE() == y && current_point.getXE()-1 == x || current_point.getYE()-1 == y && current_point.getXE() == x
                                            || current_point.getYE()-1 == y && current_point.getXE()+1 == x || current_point.getYE()-1 == y && current_point.getXE()-1 == x) {
                                        vector.setEnd_point(field[y][x]);
                                        current_point.setActive(false);
                                        vectors.add(vector);

                                    }
                                    else { //если тупик
                                        vector.setEnd_point(current_point);
                                        current_point.setActive(false);
                                        vectors.add(vector);
                                    }
                                    break;
                                }
                                current_point = field[ye][xe];
                            }

                        }
                        if(vectors.size()>=1) {
                            line.setVectors(vectors);
                            vectors = new ArrayList<>();
                            lines.add(line);
                            line = new Line();
                        }
                    }
                }
            }
        }
    }

    public static void makeTestFieldSquare() {
        for (int x = 0; x < Y_SIZE; x++) { //вывод в консоль RGB каждой точки
            for (int y = 0; y < X_SIZE; y++) {
                field[y][x] = new Point(x,y);
                field[y][x].setActive(false);
                field[y][x].setRGB(255, 255, 255);
            }
        }

        for(int y = 50; y < 150; y++) {
            for(int x = 50; x < 150; x++) {
                field[y][x].setActive(true);
                field[y][x].setRGB(0, 0, 0);
            }
        }
    }

    public static void makeTestFieldTriangle() {
        for (int x = 0; x < Y_SIZE; x++) { //вывод в консоль RGB каждой точки
            for (int y = 0; y < X_SIZE; y++) {
                field[y][x] = new Point(x,y);
                field[y][x].setActive(false);
                field[y][x].setRGB(255, 255, 255);
            }
        }

        for(int i = 100; i < 150; i++) {
            field[i][i].setActive(true); //149 149
            field[i][i].setRGB(0, 0, 0);
        }
        for(int i = 149; i >= 100; i--) {
            field[i][149+150-i].setActive(true); //100 199
            field[i][149+150-i].setRGB(0, 0, 0);
        }
        for(int i = 199; i >= 100; i--) {
            field[100][i].setActive(true); // 100 100
            field[100][i].setRGB(0, 0, 0);
        }
    }

    public static void main(String[] args) throws IOException {
        //makeTestFieldSquare();
//        makeTestFieldTriangle();
//        makeSphericalFractal("mandelbrot", 0, 0, 0, -0.9, 1.2, 0, 0, 0, -0.5, 1); //для сферического фрактала Мандельброта
        Random rand = new Random();
        makeZhuliaMandelbrot("mandelbrot");
        File file = null;
        FileWriter writer = null;
//        for (int x = 0; x < Y_SIZE; x++) { //вывод в консоль RGB каждой точки
//            for (int y = 0; y < X_SIZE; y++) {
//                System.out.println(field[y][x].getR() + " " + field[y][x].getG() + " " + field[y][x].getB());
//            }
//        }
        file = new File(".\\src\\file.svg");
        writer = new FileWriter(file);
        setAmOfNearestPoints();
        setAmOfWhiteNearestPoints();
        GUI app = new GUI();
        app.setVisible(true);
        buildLinesByWhite();
        writer.write("""
                            <?xml version="1.0" encoding="UTF-8" standalone="no"?>
                            <svg version = "1.1"
                                 baseProfile="full"
                                 xmlns = "http://www.w3.org/2000/svg"
                                 xmlns:xlink = "http://www.w3.org/1999/xlink"
                                 xmlns:ev = "http://www.w3.org/2001/xml-events"
                                 height = "600px"  width = "600px">
                                <rect x="0" y="0" width="600" height="600"
                                      fill="none" stroke="none" stroke-width="5px" stroke-opacity="0.5"/>""");
        for(Line line: lines) {
            ArrayList<Vector> vectors = line.getVectors();
            System.out.println(line.toString());
            for (Vector vector : vectors) {
                if (vector != null) {
                    int x0 = vector.getStart_point().getXE();
                    int y0 = vector.getStart_point().getYE();
                    int x1 = vector.getEnd_point().getXE();
                    int y1 = vector.getEnd_point().getYE();
                    String r = Integer.toHexString(vector.getEnd_point().getR());
                    String g = Integer.toHexString(vector.getEnd_point().getG());
                    String b = Integer.toHexString(vector.getEnd_point().getB());
                    System.out.println(vector.getStart_point() + " " + vector.getEnd_point());
                    writer.write("<line x1=\"" + x0 + "\" y1=\"" + y0 + "\" x2=\"" + x1 + "\" y2=\"" + y1 + "\" style=\"stroke: #" + r + g + b + "; stroke-width: 0.5px;\" />\n");
                }
            }
        }
        writer.write("</svg>");
        writer.close();

        for(Line line: lines) {
            ArrayList<Vector> vectors = line.getVectors();
            ArrayList<Integer> numbers = new ArrayList<>();
            file = new File(".\\src\\lines\\h_" + vectors.get(0).getStart_point().getzVertical() + ".txt");
            while (file.exists())
                file = new File(".\\src\\lines\\h_" + vectors.get(0).getStart_point().getzVertical() + "_" + rand.nextInt(200000) + ".txt");
            writer = new FileWriter(file);
            for (Vector vector : vectors) {
                if (vector != null) {
                    System.out.println(vector.getStart_point() + " " + vector.getEnd_point());
                    writer.write(vector.getStart_point().getXE() + " " + vector.getStart_point().getYE() + " " + vector.getStart_point().getzVertical() + "\n");
                }
            }
            writer.close();
        }
    }

    static class GraphicsPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for(int y = 0; y < Y_SIZE; y++) { // блок рисования фрактала через точки
                for(int x = 0; x < X_SIZE; x++) {
                    g.setColor(new Color(field[y][x].getR(), field[y][x].getG(), field[y][x].getB()));
                    g.drawOval(field[y][x].getXE(), field[y][x].getYE(), 1, 1);
                }
            }
//            for(Line line: lines) { //блок рисования фрактала через найденные линии
//                for(Vector vector: line.getVectors()) {
//                    if(vector != null) {
//                        g.setColor(new Color(vector.getEnd_point().getR(), vector.getEnd_point().getG(), vector.getEnd_point().getB()));
//                        g.drawLine(vector.getStart_point().getXE(), vector.getStart_point().getYE(), vector.getEnd_point().getXE(), vector.getEnd_point().getYE());
//                    }
//                }
//            }
        }
    }

    static class GUI extends JFrame {
        GraphicsPanel panel  = new GraphicsPanel();

        public GUI () {
            super("fractal");
            this.setVisible(true);
            this.setBounds(100, 100, 600, 600);
            this.add(panel);
            this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        }
    }
}