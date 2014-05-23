/*
 * Kong Huang
 * CS1213
 * 2048 in swing
 * This file contains the constants (numbers and their respective colors) and their value.
 */

package Final;

import java.awt.*;

// enum for storing mundane number and color values
public enum NumbersAndColors {
	
	// hex color codes taken from quaker66's implementation of 2048 in the 
	// OctaForge UI (lua)
	// too lazy to make own colors :/
    _0   (0,    0xcdc0b4, 0x776e65),
    _2   (2,    0x776E65, 0xEEE4DA),
    _4   (4,    0x776E65, 0xEDE0C8),
    _8   (8,    0xF9F6F2, 0xF2B179),
    _16  (16,   0xF9F6F2, 0xF59563),
    _32  (32,   0xF9F6F2, 0xF67C5F),
    _64  (64,   0xF9F6F2, 0xF65E3B),
    _128 (128,  0xF9F6F2, 0xEDCF72),
    _256 (256,  0xF9F6F2, 0xEDCC61),
    _512 (512,  0xF9F6F2, 0xEDC850),
    _1024(1024, 0xF9F6F2, 0xEDC53F),
    _2048(2048, 0xF9F6F2, 0xEDC22E);
    
    private final int value;
    private final Color color;
    private final Color fontColor;

    // constructor
    NumbersAndColors(int n, int f, int c) {
        value = n;
        color = new Color(c);
        fontColor = new Color(f);
    }
    
    static NumbersAndColors of(int num) {
        return NumbersAndColors.valueOf("_" + num);
    }

    public Color fontColor() {
        return fontColor;
    }

    public Color color() {
        return color;
    }
    
    public int value() {
        return value;
    }
    

}
