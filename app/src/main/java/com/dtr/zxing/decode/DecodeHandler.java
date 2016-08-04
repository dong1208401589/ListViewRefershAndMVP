/*
 * Copyright (C) 2010 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dtr.zxing.decode;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.dong.listviewrefershandmvp.R;
import com.dtr.zxing.activity.CaptureActivity;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

import java.io.ByteArrayOutputStream;
import java.util.Map;

public class DecodeHandler extends Handler {

    private final CaptureActivity activity;
    private final MultiFormatReader multiFormatReader;
    private boolean running = true;
    public DecodeHandler(CaptureActivity activity,Map<DecodeHintType, Object> hints) {
        multiFormatReader = new MultiFormatReader();
        multiFormatReader.setHints(hints);
        this.activity = activity;
    }

    @Override
    public void handleMessage(Message message) {
        if (!running) {
            return;
        }
        switch (message.what) {
            case R.id.decode:
                decode((byte[]) message.obj, message.arg1, message.arg2);
                break;
            case R.id.quit:
                running = false;
                Looper.myLooper().quit();
                break;
        }
    }

    /**
     * Decode the data within the viewfinder rectangle, and time how long it
     * took. For efficiency, reuse the same reader objects from one decode to
     * the next.
     *
     * @param data   The YUV preview frame.
     * @param width  The width of the preview frame.
     * @param height The height of the preview frame.
     */
    private void decode(byte[] data, int width, int height) {
        Camera.Size size = activity.getCameraManager().getPreviewSize();
        Result rawResult = null;

        // 这里需要将获取的data翻转一下，因为相机默认拿的的横屏的数据
        byte[] rotatedData = new byte[data.length];
        for (int y = 0; y < size.height; y++) {
            for (int x = 0; x < size.width; x++)
                rotatedData[x * size.height + size.height - y - 1] = data[x + y * size.width];
        }

        // 宽高也要调整
        int tmp = size.width;
        size.width = size.height;
        size.height = tmp;
       // String result = null;
        //if(result!=null)
        //Log.e("DecodeHandler",result);


        PlanarYUVLuminanceSource source = buildLuminanceSource(rotatedData, size.width, size.height);

        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        try {
            rawResult = multiFormatReader.decodeWithState(bitmap);
        } catch (Exception re) {
        } finally {
            multiFormatReader.reset();
        }

        Handler handler = activity.getHandler();
        if (rawResult != null) {
            // Don't log the barcode contents for security.
            if (handler != null) {
                Message message = Message.obtain(handler, R.id.decode_succeeded);
                Bundle bundle = new Bundle();
               // bundleThumbnail(source, bundle);
                bundle.putString("code", rawResult.getText());
                if(rawResult.getBarcodeFormat().name().equals("QR_CODE")){
                    bundle.putString("type", "3");
                }else
                bundle.putString("type", "2");

                Log.e("handler",rawResult.getBarcodeFormat().name());
                //QR_CODE  CODE_128
                message.setData(bundle);
                message.obj = rawResult.getText();
                message.sendToTarget();
            }
        } else {
            if (handler != null) {
                Message message = Message.obtain(handler, R.id.decode_failed);
                message.sendToTarget();
            }

        }

    }


    /*private void decodezzm(byte[] data, int width, int height) {
       
        Handler handler = activity.getHandler();
        String result = null;
        try {
            Rect rect = activity.getCropRect();
            //Point point = activity.getCameraManager().getCameraResolution().y;
            int top;
            int left;
            int w = rect.height() * width / activity.getCameraManager().getCameraResolution().x;
            int h = rect.width() * height / activity.getCameraManager().getCameraResolution().y;
            top = (height - h) / 2;
            left = (width - w) / 2;
            byte[] buffer = new byte[w * h * 3 >> 1];

            int i, j;
            int base = top * width;
            int bufferBase = 0;
            for (i = 0; i < h; i++) {
                for (j = 0; j < w; j++) {
                    buffer[bufferBase + j] = data[base + j + left];
                }
                bufferBase += w;
                base += width;
            }

            base = width * height + (top / 2 + top % 2) * width;
            bufferBase = w * h;
            for (i = 0; i < h / 2; i++) {
                for (j = 0; j < w; j++) {
                    buffer[bufferBase + j] = data[base + j + left];
                }
                bufferBase += w;
                base += width;
            }
            result = zzmDecoder.decode(buffer, w, h);
        } catch (Exception e) {
            //Log.e(TAG, " " + e.getMessage());
            Message message = Message.obtain(handler,  R.id.decode_failed);
            message.obj = result;
            message.sendToTarget();
        }

        if (result != null && !"".equals(result)) {
            if (handler != null) {
                //解码结果，包括第1位码类型(1-条形码，2-真知码，3-QR�?，第2位起为解码结�?
                String codeType = result.trim().substring(0, 1);
                String resultCode = result.trim().substring(1);
                if(codeType.equals("2")){
                    Message message = Message.obtain(handler, R.id.decode_succeeded);
                    message.obj = result;
                    Bundle bundle = new Bundle();
                    bundle.putString("code", resultCode);
                    bundle.putString("type", codeType);
                    message.setData(bundle);
                    message.sendToTarget();
                }
            }
        } else {
           // decode(data, width, height);
            if (handler != null) {
                Message message = Message.obtain(handler,  R.id.decode_failed);
                message.obj = result;
                message.sendToTarget();
            }
        }
    }*/


    private static void bundleThumbnail(PlanarYUVLuminanceSource source, Bundle bundle) {
        int[] pixels = source.renderThumbnail();
        int width = source.getThumbnailWidth();
        int height = source.getThumbnailHeight();
        Bitmap bitmap = Bitmap.createBitmap(pixels, 0, width, width, height, Bitmap.Config.ARGB_8888);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out);
        bundle.putByteArray(DecodeThread.BARCODE_BITMAP, out.toByteArray());
    }

    /**
     * A factory method to build the appropriate LuminanceSource object based on
     * the format of the preview buffers, as described by Camera.Parameters.
     *
     * @param data   A preview frame.
     * @param width  The width of the image.
     * @param height The height of the image.
     * @return A PlanarYUVLuminanceSource instance.
     */
    public PlanarYUVLuminanceSource buildLuminanceSource(byte[] data, int width, int height) {
        Rect rect = activity.getCropRect();
        if (rect == null) {
            return null;
        }
        // Go ahead and assume it's YUV rather than die.
        return new PlanarYUVLuminanceSource(data, width, height, rect.left, rect.top, rect.width(), rect.height(), false);
    }
}
