/*
Compile: make volume
Run: ./volume
make volume && ./volume
*/
#define _USE_MATH_DEFINES
#define _GNU_SOURCE
#include "base.h"

enum Tag { TCylinder, TSphere, TCuboid };
typedef enum Tag Tag;

struct Cylinder {
    double r, h; // V = pi * r^2 * h
};

struct Sphere {
    double r; // V = 4 / 3 * pi * r^3
};

struct Cuboid {
    double a, b, c; // V = a * b *c
};

struct GeomObject {
	enum Tag Tag;
	union {
		struct {
			double r;
			double h;
		};
		struct {
			double a;
			double b;
			double c;
		};
		struct Sphere Sphere;
	};
   
};

typedef struct Cylinder Cylinder;
typedef struct Sphere Sphere;
typedef struct Cuboid Cuboid;
typedef struct GeomObject GeomObject;

GeomObject make_cylinder(double r, double h) {
	require("vaild radius", r >= 0);
	require("vaild height", h >= 0);
   struct GeomObject o;
   o.Tag = TCylinder;
   o.r = r;
   o.h = h;
    return o;
}

GeomObject make_sphere(double r) {
	require("vaild radius", r >= 0);
   struct GeomObject o;
	o.Tag = TSphere;
	o.Sphere.r = r;
	ensure("non-negative result", r >= 0);
    return o;
}

GeomObject make_cuboid(double a, double b, double c) {
	require("vaild long a", a >= 0);
	require("vaild long b", b >= 0);
	require("vaild long c", c >= 0);
    GeomObject o;
	o.Tag = TCuboid;
	o.a = a;
	o.b = b;
	o.c = c;
    return o;
}

double volume(GeomObject o);

void volume_test(void) {
    test_within_d(volume(make_sphere(2)), 4 /3.0 * M_PI * 2 * 2 * 2, 1e-6);
    test_within_d(volume(make_cuboid(2, 3, 4)), 2 * 3 * 4, 1e-6);
    test_within_d(volume(make_cylinder(3, 4)), 4 * M_PI * 3 * 3 , 1e-6);
}
    
// GeomObject -> double
// Computes the volume of the given object.
double volume(GeomObject o) {
	switch (o.Tag){
		case TCylinder:
		require("vaild radius", o.r >= 0);
		require("vaild height", o.h >= 0);
		ensure("non-negative result",(M_PI * o.h * pow(o.r,2.0) >= 0));
		 return (M_PI * o.h * pow(o.r,2.0));
		case TSphere:
		require("vaild radius", o.Sphere.r >= 0);
		ensure("non-negative result",(4.0 / 3.0 * M_PI * pow(o.Sphere.r,3.0) >= 0));
		 return (4.0 / 3.0 * M_PI * pow(o.Sphere.r,3.0));
		case TCuboid:
		require("vaild long a", o.a >= 0);
		require("vaild long b", o.b >= 0);
		require("vaild long c", o.c >= 0);
		ensure("non-negative result", (o.a * o.b * o.c) >= 0);
 		 return (o.a * o.b * o.c);
	}
	return 0.0;
}

int main(void) {
    volume_test();
    return 0;
}

/* Eine Aufzählung (enumeration) bietet die Möglichkeit, mehrere Konstanten zu einer Menge zusammenzufassen,
Die enum -Deklaration legt den Namen der Aufzählung und die Namen der einzelnen Konstanten fest. 
Ein enum-Tag kann die Enumeration einfach aufrufen. In der Enumeration sind dann alle integer Werte als Aufzählung vorhanden.
*/



