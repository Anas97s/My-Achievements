#include "base.h"
/*
    make rects
    ./rects
    make rects && ./rects
*/

enum FillProperty{
    RANGE,
    SIMPLE,
    PATTERN
} typedef TFillProperty;

struct RangeFill{
    char start;
    char end;
} typedef RangeFill;

struct SimpleFill{
    char c;
} typedef SimpleFill;

struct PatternFill{
    String pattern;
} typedef PatternFill;

struct Fill{
    TFillProperty tag;
    union{
        PatternFill pf;
        SimpleFill sf;
        RangeFill rf;  
    };
}typedef Fill;

struct Rect{
    int x, y;
    int width, height;
    Fill fill;    
} typedef Rect;

Fill make_range_fill(char lower, char upper){
	require("vaild range", lower <= upper);
	require("exact range lower", 1 <= lower && lower <= 127);
	require("exact range upper", 1 <= upper && upper <= 127);
    Fill fill;
	fill.tag = RANGE;
	fill.rf.start = lower; 
	fill.rf.end = upper;
    return fill;
}

Fill make_pattern_fill(String s){
    require("valid input", s_length(s) > 0);
    Fill fill;
    fill.tag = PATTERN;
    fill.pf.pattern = s;
    return fill;
}

Fill make_simple_fill(char c){
	require("vaild intervall", 1 <= c && c <= 127);
	require("representable character", isprint(c)); 
    Fill fill;
	fill.tag = SIMPLE;
	fill.sf.c = c;
	return fill;
}

Rect make_rect(int x, int y, int width, int height, Fill fill){
	require("vaild x", x >= 0);
	require("vaild y", y >= 0);
	require("vaild width", width >= 0);
	require("vaild height", height >= 0);
	Rect rect;
	if (fill.tag == PATTERN || fill.tag == SIMPLE || fill.tag == RANGE) { 
	rect.x = x;  
    rect.y = y;	
	rect.width = width; 
	rect.height = height; 
	rect.fill = fill;
	} 
    return rect;
}   
// up_side und down_side sind Hilfsfunktionen.
void up_side(Rect rect) {
	 int width = rect.width;
     if ( width == 0){
	     printcln('+');
	     return;
     }
     printc('+');
     for (int i = 0; i < (width - 1); i++){
	     printc('-');
     }
     printcln('+');
}

void down_side(Rect rect) {
	 int width = rect.width;
     if ( width == 0){
	     printcln('+');
	     return;
    }
     printc('|');
     for (int i = 0; i < (width - 1); i++){
		 char p;
		 int b;
		switch (rect.fill.tag) {
		case PATTERN:
		if (b > s_length(rect.fill.pf.pattern) - 1){
			b = 0;
		}
		p = rect.fill.pf.pattern[b];
		printc(p);
		break;
		case SIMPLE: 
		    printc(rect.fill.sf.c);
		    break;
		case RANGE:
		p = rect.fill.rf.start;
		p += b;
		if(p > rect.fill.rf.end){
			b = 0;
			p = rect.fill.rf.start;
		}
		printc(p);
		break;
	}
	b++;
     }
     printcln('|');
}

void draw_rect(Rect rect){
	for (int i = 0; i <= rect.height; i++){
		for (int x = 0; x < rect.x; x++) {
	         printc(' ');
		}
		for (int y = 0; y < rect.y; y++) {
			 printc('\n');
		}
		if(i == 0 || i == rect.height){
			up_side(rect);
		} else {
			down_side(rect);
	    }
	}
}	
/*
width = 1, height = 1
+

width = 1, height = 2
+
+

width = 2, height = 1
++

width = 2, height = 2
++
++

width = 3, height = 2
+-+
+-+

width = 3, height = 3, x = 1, y = 0, fill = simple with 'o'

 +-+
 |o|
 +-+
 
width = 15, height = 10, x = 15, y = 0, fill = pattern with ".o"
               +-------------+
               |.o.o.o.o.o.o.|
               |o.o.o.o.o.o.o|
               |.o.o.o.o.o.o.|
               |o.o.o.o.o.o.o|
               |.o.o.o.o.o.o.|
               |o.o.o.o.o.o.o|
               |.o.o.o.o.o.o.|
               |o.o.o.o.o.o.o|
               +-------------+
*/
int main(void){
	draw_rect(make_rect(1, 1, 1, 1, make_simple_fill('o')));
    draw_rect(make_rect(1, 1, 3, 4, make_simple_fill('k'))); 
	draw_rect(make_rect(6, 5, 8, 3, make_simple_fill('5')));
	draw_rect(make_rect(4, 4, 5, 5, make_pattern_fill("aoa")));
	draw_rect(make_rect(9, 7, 4, 10, make_range_fill('0', '9')));
	draw_rect(make_rect(8, 5, 6, 6, make_pattern_fill("#*#")));
    return 0;
}

