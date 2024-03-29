/*
 * Copyright 2019 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.genius.assemble;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity implements GlassGestureDetector.OnGestureListener {

  private View decorView;
  private GlassGestureDetector glassGestureDetector;


  @Override
  public boolean onKeyDown(int keycode, KeyEvent e) {
    Log.e("Base", ":" + KeyEvent.keyCodeToString(keycode));

    switch (keycode) {

      case KeyEvent.KEYCODE_DPAD_CENTER:
        onGesture(GlassGestureDetector.Gesture.TAP);
        return true;
      case KeyEvent.KEYCODE_DPAD_DOWN:
        onGesture(GlassGestureDetector.Gesture.SWIPE_DOWN);
        return true;
      case KeyEvent.KEYCODE_DPAD_UP:
        onGesture(GlassGestureDetector.Gesture.SWIPE_UP);
        return true;
      case KeyEvent.KEYCODE_DPAD_RIGHT:
        onGesture(GlassGestureDetector.Gesture.SWIPE_FORWARD);

        return true;
      case KeyEvent.KEYCODE_DPAD_LEFT:

          onGesture(GlassGestureDetector.Gesture.SWIPE_BACKWARD);

        return true;

      case KeyEvent.KEYCODE_FORWARD_DEL:
        onGesture(GlassGestureDetector.Gesture.TWO_FINGER_SWIPE_FORWARD);
        return true;
      case KeyEvent.KEYCODE_DEL:
        onGesture(GlassGestureDetector.Gesture.TWO_FINGER_SWIPE_BACKWARD);
        return true;
      case KeyEvent.KEYCODE_VOLUME_UP:
        onGesture(GlassGestureDetector.Gesture.TWO_FINGER_SWIPE_UP);
        return true;
      case KeyEvent.KEYCODE_VOLUME_DOWN:
        onGesture(GlassGestureDetector.Gesture.TWO_FINGER_SWIPE_DOWN);
        return true;
      case KeyEvent.KEYCODE_VOLUME_MUTE:
        // Do nothing
        return true;
      default:
        return super.onKeyUp(keycode, e);
    }

  }


  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setTheme(R.style.AppTheme);
    if (getSupportActionBar() != null) {
      getSupportActionBar().hide();
    }
    decorView = getWindow().getDecorView();
    decorView
        .setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
          @Override
          public void onSystemUiVisibilityChange(int visibility) {
            if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
              hideSystemUI();
            }
          }
        });
    glassGestureDetector = new GlassGestureDetector(this, this);
  }

  @Override
  protected void onResume() {
    super.onResume();
    hideSystemUI();
  }

  @Override
  public boolean dispatchTouchEvent(MotionEvent ev) {
    if (glassGestureDetector.onTouchEvent(ev)) {
      return true;
    }
    return super.dispatchTouchEvent(ev);
  }

  @Override
  public boolean onGesture(GlassGestureDetector.Gesture gesture) {
    switch (gesture) {
      case SWIPE_DOWN:
        finish();
        return true;
      default:
        return false;
    }
  }

  private void hideSystemUI() {
    decorView.setSystemUiVisibility(
        View.SYSTEM_UI_FLAG_IMMERSIVE
            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN);
  }
}
