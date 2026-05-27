package com.cmrl.metro.utils;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Generates a simple QR-code-like bitmap using the ZXing library.
 *
 * Dependency to add in app/build.gradle:
 *   implementation 'com.google.zxing:core:3.5.3'
 *   implementation 'com.journeyapps:zxing-android-embedded:4.3.0'
 */
public class QRGenerator {

    public static Bitmap generate(String content, int width, int height) {
        try {
            com.google.zxing.MultiFormatWriter writer = new com.google.zxing.MultiFormatWriter();
            com.google.zxing.common.BitMatrix matrix = writer.encode(
                content,
                com.google.zxing.BarcodeFormat.QR_CODE,
                width, height
            );
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    pixels[y * width + x] = matrix.get(x, y) ? Color.BLACK : Color.WHITE;
                }
            }
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            bmp.setPixels(pixels, 0, width, 0, 0, width, height);
            return bmp;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
